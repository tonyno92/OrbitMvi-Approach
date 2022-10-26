package com.orbitmvi.core.network.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoreNetwork {
    private const val LOGGING_INTERCEPTOR = "logging_interceptor"
    private const val IS_DEBUG = "is_debug"
    private const val BASE_URL = "country_base_url"
    private const val API_VERSION = "country_api_version"

    val module = module {
        factory<Converter.Factory> { GsonConverterFactory.create() }
        factory<Interceptor>(named(LOGGING_INTERCEPTOR)) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        factory {
            OkHttpClient.Builder().apply {
                if (getProperty<String>(IS_DEBUG) == "true") {
                    addInterceptor(get<Interceptor>(named(LOGGING_INTERCEPTOR)))
                }
            }.build()
        }
        single {
            Retrofit.Builder()
                .baseUrl(
                    getProperty<String>(BASE_URL) + getProperty<String>(API_VERSION)
                )
                .client(get())
                .addConverterFactory(get())
                .build()
        }
    }
}
