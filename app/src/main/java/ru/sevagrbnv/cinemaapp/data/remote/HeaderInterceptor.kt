package ru.sevagrbnv.cinemaapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-API-KEY", Secrets.KINOPOISK_API_KEY)
            .addHeader("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}