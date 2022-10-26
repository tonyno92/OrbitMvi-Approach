package com.orbitmvi.feature.details.domain.repository

import com.orbitmvi.feature.list.domain.model.Country

internal interface CountryDetailRepository {
    suspend fun getCountryDetailsResult(countryId: Int): Result<Country>
}
