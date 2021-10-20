package com.example.getandpostlocation

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {
    @GET("/test/")
    fun doGetListResources(): Call<List<UserDetails.Datum>>
    @POST("/test/")
    fun addUser(@Body userData: UserDetails.Datum): Call<UserDetails.Datum>
}