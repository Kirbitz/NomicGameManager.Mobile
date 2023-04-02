package nomic.data.services

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import nomic.data.models.ResponseFormat

class NomicApiService {
    private val url: String = "http://10.0.2.2:8080/api"
    private val gson = Gson()

    fun getRulesAmendmentsList(): StringRequest {
        val endpoint = "$url/rules_amendments/collect/2"
        val stringRequest = StringRequest(Request.Method.GET, endpoint,
            { response ->
                val test = gson.fromJson<ResponseFormat<String>>(response, ResponseFormat::class.java)
                val test2 = test.data
            },
            {
                it.printStackTrace()
            })
        return stringRequest
    }
}