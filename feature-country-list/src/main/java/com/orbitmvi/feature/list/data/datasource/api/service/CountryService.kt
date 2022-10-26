package com.orbitmvi.feature.list.data.datasource.api.service

import com.orbitmvi.feature.list.data.datasource.api.response.CountryResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface CountryService {

    @GET("all")
    suspend fun getAllCountry(): Response<CountryResponse>
}
