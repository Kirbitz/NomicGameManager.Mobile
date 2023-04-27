package nomic.data.repositories

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.suspendCancellableCoroutine
import nomic.data.models.ResponseFormatDTO
import nomic.mobile.BuildConfig
import org.json.JSONObject
import kotlin.coroutines.resumeWithException

/**
 * Implementation of the [IVolleyRequester][nomic.data.repositories.IVolleyRequester]
 *
 * @see [nomic.data.repositories.IVolleyRequester]
 */
class VolleyRequester(context: Context) : IVolleyRequester {
    private val mapper = ObjectMapper().registerKotlinModule()
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    override suspend fun <T> stringRequest(url: String, tag: String): T {
        return suspendCancellableCoroutine { continuation ->
            // Lambda functions response and error are built in callback functions for StringRequest
            // response is based on Response.Listener which triggers when a successful 2XX call is returned from the API
            // error is based on Response.ErrorListener which triggers when volley receives a bad status code
            //
            // Also the reason this was turned into an object is that was the only method of being able to include headers in the request call
            val stringRequest = object : StringRequest(
                Method.GET,
                url,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<T>>(response)
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

    override suspend fun <I, O> jsonObjectRequest(url: String, data: I, tag: String): O {
        return suspendCancellableCoroutine { continuation ->
            val jsonData = JSONObject(mapper.writeValueAsString(data))
            // Lambda functions response and error are built in callback functions for JsonObjectRequest
            // response is based on Response.Listener which triggers when a successful 2XX call is returned from the API
            // error is based on Response.ErrorListener which triggers when volley receives a bad status code
            //
            // Also the reason this was turned into an object is that was the only method of being able to include headers in the request call
            val jsonRequest = object : JsonObjectRequest(
                Method.POST,
                url,
                jsonData,
                { response ->
                    val responseObject = mapper.readValue<ResponseFormatDTO<O>>(response.toString())
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
