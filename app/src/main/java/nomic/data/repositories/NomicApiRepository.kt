package nomic.data.repositories

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.suspendCancellableCoroutine
import nomic.data.models.*
import nomic.mobile.BuildConfig
import kotlin.coroutines.resumeWithException

/**
 * Implementation of the [INomicApiRepository][nomic.data.repositories.INomicApiRepository]
 *
 * @see [nomic.data.repositories.INomicApiRepository]
 */
class NomicApiRepository(private val volleyRequester: VolleyRequester, context: Context) : INomicApiRepository {
    private val baseUrl: String = BuildConfig.SERVER_ROOT_URI
    private val mapper = ObjectMapper().registerKotlinModule()
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    override suspend fun getRulesAmendmentsList(gameId: Int, tag: String): List<RulesAmendmentsDTO> {
        val endpointUrl = "$baseUrl/rules_amendments/collect/$gameId"
        return suspendCancellableCoroutine { continuation ->
            // Lambda functions response and error are built in callback functions for StringRequest
            // response is based on Response.Listener which triggers when a successful 2XX call is returned from the API
            // error is based on Response.ErrorListener which triggers when volley receives a bad status code
            //
            // Also the reason this was turned into an object is that was the only method of being able to include headers in the request call
            val stringRequest = object : StringRequest(
                Method.GET,
                endpointUrl,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<List<RulesAmendmentsDTO>>>(response)
                    if (responseObject.success) {
                        continuation.resumeWith(Result.success(responseObject.data))
                    }
                },
                { error ->
                    error.printStackTrace()
                    val statusCode = error.networkResponse?.statusCode
                    var exception = Exception(error.message)
                    when (statusCode) {
                        400 -> exception = IllegalArgumentException(error.message)
                        404 -> exception = EntityNotFoundException()
                        else -> {}
                    }
                    continuation.resumeWithException(exception)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = BuildConfig.USER_TOKEN
                    return headers
                }
            }
            stringRequest.tag = tag
            queue.add(stringRequest)
        }
    }

    override suspend fun enactRule(newRule: RuleDTO, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/enactRule"
        return volleyRequester.jsonObjectRequest(endpointUrl, newRule, tag)
    }

    override suspend fun repealRule(ruleId: Int, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/repeal_rule/$ruleId"
        return volleyRequester.stringRequest(endpointUrl, tag)
    }

    override suspend fun transmuteRule(ruleId: Int, mutable: ModifyRuleMutabilityDTO, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/transmute_rule/$ruleId"
        return volleyRequester.jsonObjectRequest(endpointUrl, mutable, tag)
    }

    override suspend fun amendRule(newAmendment: AmendmentDTO, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/enactAmendment"
        return volleyRequester.jsonObjectRequest(endpointUrl, newAmendment, tag)
    }

    override suspend fun repealAmendment(amendId: Int, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/repeal_amendment/$amendId"
        return volleyRequester.stringRequest(endpointUrl, tag)
    }

    override suspend fun createGame(newGame: GameDTO, tag: String): String {
        val endpointUrl = "$baseUrl/game/create"
        return volleyRequester.jsonObjectRequest(endpointUrl, newGame, tag)
    }

    override suspend fun getGamesList(size: Int, offset: Int, tag: String): List<GameDTO> {
        val endpointUrl = "$baseUrl/game/list?size=$size&offset=$offset"
        return suspendCancellableCoroutine { continuation ->
            // Lambda functions response and error are built in callback functions for StringRequest
            // response is based on Response.Listener which triggers when a successful 2XX call is returned from the API
            // error is based on Response.ErrorListener which triggers when volley receives a bad status code
            //
            // Also the reason this was turned into an object is that was the only method of being able to include headers in the request call
            val stringRequest = object : StringRequest(
                Method.GET,
                endpointUrl,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<List<GameDTO>>>(response)
                    if (responseObject.success) {
                        continuation.resumeWith(Result.success(responseObject.data))
                    }
                },
                { error ->
                    error.printStackTrace()
                    val statusCode = error.networkResponse?.statusCode
                    var exception = Exception(error.message)
                    when (statusCode) {
                        400 -> exception = IllegalArgumentException(error.message)
                        404 -> exception = EntityNotFoundException()
                        else -> {}
                    }
                    continuation.resumeWithException(exception)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = BuildConfig.USER_TOKEN
                    return headers
                }
            }
            stringRequest.tag = tag
            queue.add(stringRequest)
        }
    }

    /**
     * Cancels all requests that are identified by tag
     *
     * @param tag the identifier of requests in the queue
     */
    fun cancelRequests(tag: String) {
        volleyRequester.cancelRequests(tag)
    }
}
