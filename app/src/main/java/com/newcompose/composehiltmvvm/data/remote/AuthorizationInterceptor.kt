package com.newcompose.composehiltmvvm.data.remote

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", "260b67644b4a6e8ac70ec1d792c79c40") // Todo: Add your api key here
            .build()
        val request: Request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}