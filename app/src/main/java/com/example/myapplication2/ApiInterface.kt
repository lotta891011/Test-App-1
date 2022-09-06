package com.example.myapplication2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//POST and GET methods for Reqres request
interface ApiInterface {
    @POST("api/login")
    fun sendReq(@Body requestModel: RequestModel): Call<ResponseModel>

    @GET("api/users/2")
    fun sendReq2(): Call<ResponseModel2>
}