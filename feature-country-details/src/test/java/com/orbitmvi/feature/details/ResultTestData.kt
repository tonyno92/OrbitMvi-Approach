package com.orbitmvi.feature.details

import com.orbitmvi.feature.details.CountryTestData.countryDomain
import com.orbitmvi.feature.list.domain.model.Country
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import retrofit2.HttpException
import retrofit2.Response

internal object ResultTestData {

    val resultSuccess: Result<Country> = Result.success(countryDomain)
    val resultError: Result<Country> =
        Result.failure(
            HttpException(
                Response.error<List<Country>>(
                    400,
                    RealResponseBody("type", 0, Buffer())
                )
            )
        )
}
