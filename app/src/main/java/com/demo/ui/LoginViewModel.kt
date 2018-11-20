package com.demo.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.demo.model.ApiResponse
import com.demo.network.ApiClient
import com.demo.network.ApiService
import com.demo.network.NetworkUtil.isHttpStatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel : ViewModel(){
    private val apiService by lazy { ApiClient.getClient().create(ApiService::class.java) }
    val requestLoginData= MutableLiveData<ApiResponse>()
    val errorMess= MutableLiveData<String>()





    @SuppressLint("CheckResult")
    fun setLoginParams(apiParams: HashMap<String, String>) {
        apiService!!.login(apiParams)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                requestLoginData.value = result
            },
                { error ->
                    errorMess.value=isHttpStatusCode(error)
                }
            )
    }


}