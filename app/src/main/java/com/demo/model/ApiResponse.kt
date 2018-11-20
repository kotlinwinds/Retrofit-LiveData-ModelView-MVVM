package com.demo.model

import com.google.gson.annotations.SerializedName


data class ApiResponse(
    val message: String,
    @SerializedName("data")
    val data: Data?=null
)

data class Data(

    val mobile: String,
    val email: String,
    val lang: String

 )