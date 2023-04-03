package nomic.data.models

/**
 * Response data received from the Nomic API
 *
 * @property success a bool flag indicating if the request worked
 * @property status the http status code for the request
 * @property data information related to the request
 */
data class ResponseFormatDTO<T>(
    val success: Boolean,
    val status: String,
    val data: T
)
