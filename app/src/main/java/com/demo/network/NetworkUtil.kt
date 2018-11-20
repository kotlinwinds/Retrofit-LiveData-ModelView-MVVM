package com.demo.network

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import okhttp3.ResponseBody
import org.json.JSONObject

object NetworkUtil {
    fun isHttpStatusCode(e: Throwable): String{

        var errorMess = ""

        if (e is HttpException) {
            val responseBody = e.response().errorBody()
            errorMess = (getErrorMessage(responseBody!!)!!)

            when {
            //    e.code() == 400 -> errorMess = "onError: BAD REQUEST 400"
                e.code() == 401 -> {
                   // Utils.clearAllDataAndLogOut()
                }
                e.code() == 403 -> errorMess = "onError: FORBIDDEN 403"
                e.code() == 404 -> errorMess = "onError: NOT FOUND 404"
                e.code() == 500 -> errorMess = "onError: INTERNAL SERVER ERROR 500"
                e.code() == 502 -> errorMess = "onError: BAD GATEWAY"
            }
        }
        return errorMess
    }


}

   private fun getErrorMessage(responseBody: ResponseBody): String? {
    return try {
        val jsonObject = JSONObject(responseBody.string())
        jsonObject.getString("message")
    } catch (e: Exception) {
        e.message
    }
  }