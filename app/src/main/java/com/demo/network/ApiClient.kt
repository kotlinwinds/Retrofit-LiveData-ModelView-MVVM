package com.demo.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    private var retrofit: Retrofit? = null
    private val REQUEST_TIMEOUT = 60
    private var okHttpClient: OkHttpClient? = null

    fun getClient(): Retrofit {

        if (okHttpClient == null)
            initOkHttp()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl("https://test-karyakarta.winds.co.in")
                    .client(okHttpClient!!)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                    .addHeader("userType", "karyakarta")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")

            // Adding Authorization token (API Key)
            // Requests will be denied without API key
          /*  if (!TextUtils.isEmpty(SharedPrefUtils.instance.authToken)) {
                requestBuilder.addHeader("Authorization","Bearer"+SharedPrefUtils.instance.authToken!!)
            }*/

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        okHttpClient = httpClient.build()
    }
}