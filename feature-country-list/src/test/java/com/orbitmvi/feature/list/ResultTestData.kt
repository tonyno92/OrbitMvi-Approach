package com.orbitmvi.feature.list

import com.orbitmvi.feature.list.CountryTestData.countryDomain
import com.orbitmvi.feature.list.domain.model.Country
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import retrofit2.HttpException
import retrofit2.Response

internal object ResultTestData {

    val resultSuccess: Result<List<Country>> = Result.success(listOf(countryDomain))
    val resultError: Result<List<Country>> =
        Result.failure(
            HttpException(
                Response.error<List<Country>>(
                    400,
                    RealResponseBody("type", 0, Buffer())
                )
            )
        )
}
