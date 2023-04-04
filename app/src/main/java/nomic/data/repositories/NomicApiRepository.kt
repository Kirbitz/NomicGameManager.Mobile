package nomic.data.repositories

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.suspendCancellableCoroutine
import nomic.data.models.ResponseFormatDTO
import nomic.data.models.RulesAmendmentsDTO
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

    /**
     * Cancels all requests that are identified by tag
     *
     * @param tag the identifier of requests in the queue
     */
    fun cancelRequests(tag: String) {
        queue.cancelAll(tag)
    }
}