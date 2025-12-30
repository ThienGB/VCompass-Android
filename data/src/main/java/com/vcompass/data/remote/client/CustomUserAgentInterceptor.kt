package com.vcompass.data.remote.client

import okhttp3.Interceptor
import okhttp3.Response

class CustomUserAgentInterceptor(private val userAgent: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("User-Agent", userAgent)
            .build()
        return chain.proceed(request)
    }
}