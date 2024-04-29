package com.example.testapppayments.model.api

import com.example.testapppayments.model.responsemodel.ModelLoginData
import com.example.testapppayments.model.responsemodel.ModelPaymentsData
import com.example.testapppayments.model.responsemodel.ModelTokenData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    // функция получения платежей
    @Headers("app-key:12345","v:1")
    @GET("payments")
    suspend fun getPayments(@Header("token") token : String) : Response <ModelPaymentsData>

    // функция получения токена
    @Headers("Content-type:application/json","app-key:12345","v:1")
    @POST("login")
    suspend fun getTokenForInput(@Body loginData : ModelLoginData) : Response <ModelTokenData>

}