package com.demo.network


import com.demo.model.ApiResponse
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {


    @FormUrlEncoded
    @POST("/api/v1/login")
    fun login(@FieldMap param : Map<String,String>): Observable<ApiResponse>

}