package nomic.data.repositories

import com.android.volley.RequestQueue

/**
 * This sets up the request types to be made to the Nomic API
 *
 * The stringRequest will setup any request without a body
 * The jsonObjectRequest will setup any request with a body
 */
interface IVolleyRequester {
    /**
     * Sets up a get style request to be sent to the Nomic API
     *
     * @param url the url endpoint to target in the Nomic API
     * @param tag an attribute for [RequestQueue] that allows it to be canceled later
     * @return Generic type based on the data in the return body
     */
    suspend fun <T> stringRequest(url: String, tag: String): T

    /**
     * Sets up a post style request to be sent to the Nomic API
     *
     * @param url the url endpoint to target in the Nomic API
     * @param data the object to be converted to JSON and sent in the body
     * @param tag an attribute for [RequestQueue] that allows it to be canceled later
     * @return Generic type based on the data in the return body
     */
    suspend fun <I, O> jsonObjectRequest(url: String, data: I, tag: String): O
}
