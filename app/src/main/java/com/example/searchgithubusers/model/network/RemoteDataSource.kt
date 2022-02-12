package com.example.searchgithubusers.model.network

import android.util.Log
import com.example.searchgithubusers.BuildConfig.API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource {
    private val TAG = javaClass.name

    private val isShowRetrofitLogs = true
    private val defaultTimeout = 30L

    private val logging = HttpLoggingInterceptor { message -> Log.d(TAG, message) }

    private val okHttpClient: OkHttpClient by lazy {
        if(isShowRetrofitLogs) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        OkHttpClient.Builder()
            .connectTimeout(defaultTimeout, TimeUnit.SECONDS)
            .readTimeout(defaultTimeout, TimeUnit.SECONDS)
            .writeTimeout(defaultTimeout, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    fun <Api> buildApi(
        api: Class<Api>
    ): Api {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())      // 透過Multipart傳檔案Server，Server那的Json格式都會夾帶雙引號
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}