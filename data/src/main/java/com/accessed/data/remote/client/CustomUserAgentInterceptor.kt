package com.accessed.data.remote.client

import okhttp3.Interceptor
import okhttp3.Response

class CustomUserAgentInterceptor(private val userAgent: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("User-Agent", userAgent)
            .addHeader("Accessed-Platform", "ACCESSED_USER")
            .build()
        return chain.proceed(request)
    }
}