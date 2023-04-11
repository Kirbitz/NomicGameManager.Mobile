package nomic.data.repositories

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.suspendCancellableCoroutine
import nomic.data.models.AmendmentDTO
import nomic.data.models.GameDTO
import nomic.data.models.ResponseFormatDTO
import nomic.data.models.RuleDTO
import nomic.data.models.RulesAmendmentsDTO
import org.json.JSONObject
import kotlin.coroutines.resumeWithException

/**
 * Implementation of the [INomicApiRepository][nomic.data.repositories.INomicApiRepository]
 *
 * @see [nomic.data.repositories.INomicApiRepository]
 * @param context the application context of the mobile app page
 */
class NomicApiRepository(context: Context) : INomicApiRepository {
    private val baseUrl: String = "http://10.0.2.2:8080/api"
    private val mapper = ObjectMapper().registerKotlinModule()
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    override suspend fun getRulesAmendmentsList(gameId: Int, tag: String): List<RulesAmendmentsDTO> {
        return suspendCancellableCoroutine { continuation ->
            val endpointUrl = "$baseUrl/rules_amendments/collect/$gameId"
            val stringRequest = StringRequest(Request.Method.GET, endpointUrl,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<List<RulesAmendmentsDTO>>>(response)
                    if (responseObject.success) {
                        continuation.resumeWith(Result.success(responseObject.data))
                    }
                },
                { error ->
                    error.printStackTrace()
                    continuation.resumeWithException(error)
                }
            )
            stringRequest.tag = tag
            queue.add(stringRequest)
        }
    }

    override suspend fun enactRule(newRule: RuleDTO, tag: String): String {
        return suspendCancellableCoroutine { continuation ->
            val endpointUrl = "$baseUrl/rules_amendments/enactRule"
            val jsonData = JSONObject(mapper.writeValueAsString(newRule))
            val jsonRequest = JsonObjectRequest(Request.Method.POST, endpointUrl, jsonData,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<String>>(response.toString())
                    if (responseObject.success) {
                        continuation.resumeWith(Result.success(responseObject.data))
                    }
                },
                { error ->
                    error.printStackTrace()
                    continuation.resumeWithException(error)
                }
            )
            jsonRequest.tag = tag
            queue.add(jsonRequest)
        }
    }

    override suspend fun repealRule(ruleId: Int, tag: String): String {
        return suspendCancellableCoroutine { continuation ->
            val endpointUrl = "$baseUrl/rules_amendments/repeal_rule/$ruleId"
            val stringRequest = StringRequest(Request.Method.GET, endpointUrl,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<String>>(response)
                    if (responseObject.success) {
                        continuation.resumeWith(Result.success(responseObject.data))
                    }
                },
                { error ->
                    error.printStackTrace()
                    continuation.resumeWithException(error)
                }
            )
            stringRequest.tag = tag
            queue.add(stringRequest)
        }
    }

    // TODO there is an issue with this endpoint that may result in a rewrite
    // of the actual endpoint
    override suspend fun transmuteRule(ruleId: Int, mutable: Boolean, tag: String): String {
        return suspendCancellableCoroutine { continuation ->
            val endpointUrl = "$baseUrl/rules_amendments/transmute_rule/$ruleId"
            val jsonData = JSONObject("{ mutableInput: $mutable}")
            val jsonRequest = JsonObjectRequest(Request.Method.POST, endpointUrl, jsonData,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<String>>(response.toString())
                    if (responseObject.success) {
                        continuation.resumeWith(Result.success(responseObject.data))
                    }
                },
                { error ->
                    error.printStackTrace()
                    continuation.resumeWithException(error)
                }
            )
            jsonRequest.tag = tag
            queue.add(jsonRequest)
        }
    }

    override suspend fun amendRule(newAmendment: AmendmentDTO, tag: String): String {
        return suspendCancellableCoroutine { continuation ->
            val endpointUrl = "$baseUrl/rules_amendments/enactAmendment"
            val jsonData = JSONObject(mapper.writeValueAsString(newAmendment))
            val jsonRequest = JsonObjectRequest(Request.Method.POST, endpointUrl, jsonData,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<String>>(response.toString())
                    if (responseObject.success) {
                        continuation.resumeWith(Result.success(responseObject.data))
                    }
                },
                { error ->
                    error.printStackTrace()
                    continuation.resumeWithException(error)
                }
            )
            jsonRequest.tag = tag
            queue.add(jsonRequest)
        }
    }

    override suspend fun repealAmendment(amendId: Int, tag: String): String {
        return suspendCancellableCoroutine { continuation ->
            val endpointUrl = "$baseUrl/rules_amendments/repeal_amendment/$amendId"
            val stringRequest = StringRequest(Request.Method.GET, endpointUrl,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<String>>(response)
                    if (responseObject.success) {
                        continuation.resumeWith(Result.success(responseObject.data))
                    }
                },
                { error ->
                    error.printStackTrace()
                    continuation.resumeWithException(error)
                }
            )
            stringRequest.tag = tag
            queue.add(stringRequest)
        }
    }

    override suspend fun createGame(newGame: GameDTO, tag: String): String {
        return suspendCancellableCoroutine { continuation ->
            val endpointUrl = "$baseUrl/game/create"
            val jsonData = JSONObject(mapper.writeValueAsString(newGame))
            val jsonRequest = JsonObjectRequest(Request.Method.POST, endpointUrl, jsonData,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<String>>(response.toString())
                    if (responseObject.success) {
                        continuation.resumeWith(Result.success(responseObject.data))
                    }
                },
                { error ->
                    error.printStackTrace()
                    continuation.resumeWithException(error)
                }
            )
            jsonRequest.tag = tag
            queue.add(jsonRequest)
        }
    }

    /**
     * Cancels all requests that are identified by tag
     *
     * @param tag the identifier of requests in the queue
     */
    fun cancelRequests(tag: String) {
        queue.cancelAll(tag)
    }
}