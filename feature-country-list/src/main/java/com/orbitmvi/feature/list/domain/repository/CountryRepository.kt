package com.orbitmvi.feature.list.domain.repository

import com.orbitmvi.feature.list.domain.model.Country

internal interface CountryRepository {
    suspend fun getAllCountry(): Result<List<Country>>
}
