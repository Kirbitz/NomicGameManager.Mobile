package nomic.data.models

import com.google.gson.annotations.SerializedName

/**
 * Raw Representation of Data
 *
 * @property success a bool flag indicating if the request worked
 * @property status the http status code for the request
 * @property data information related to the request
 */
data class ResponseFormat<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: T
)
