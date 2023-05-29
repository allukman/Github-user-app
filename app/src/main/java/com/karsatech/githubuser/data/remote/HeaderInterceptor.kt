package com.karsatech.githubuser.data.remote

import com.karsatech.githubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {

        val tokenAuth = BuildConfig.API_KEY

        proceed(
            request().newBuilder()
                .addHeader("Authorization", "token $tokenAuth")
                .header("Connection", "close")
                .removeHeader("Content-Length")
                .build()
        )
    }
}