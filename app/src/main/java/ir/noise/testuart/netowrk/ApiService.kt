package ir.noise.testuart.netowrk

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("assessment")
    suspend fun sendPhoneNumber(@Body body: Map<String, String>): Response<ResponseBody>
}
