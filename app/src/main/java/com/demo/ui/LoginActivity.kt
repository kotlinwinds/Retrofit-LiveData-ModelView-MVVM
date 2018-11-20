package com.demo.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.demo.R
import com.demo.model.ApiResponse
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


     private val instance by lazy { LoginViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initObservers()

    }


    fun btnLogin(v: View){
        val apiParams = HashMap<String, String>()
        apiParams["mobile"] = edtMob.text.toString()
        apiParams["password"] = edtPassword.text.toString()
        apiParams["lang"] = "en"
        instance.setLoginParams(apiParams)
        pb.visibility=View.VISIBLE
    }


    private fun initObservers() {
        instance.requestLoginData.observe(this, Observer {
            pb.visibility=View.GONE
            setData(it)
        })

        instance.errorMess.observe(this, Observer {
            pb.visibility=View.GONE
            Toast.makeText(applicationContext,""+it,Toast.LENGTH_SHORT).show()
        })
    }

    private fun setData(it: ApiResponse?) {
            Toast.makeText(applicationContext,""+it!!.message,Toast.LENGTH_SHORT).show()
            Log.d("TAHS",""+ it.message)
            Log.d("TAHS",""+ it.data)
    }


}