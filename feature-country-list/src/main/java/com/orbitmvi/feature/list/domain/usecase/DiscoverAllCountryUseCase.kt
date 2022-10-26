package com.orbitmvi.feature.list.domain.usecase

import com.orbitmvi.feature.list.domain.model.Country
import com.orbitmvi.feature.list.domain.repository.CountryRepository

internal class DiscoverAllCountryUseCase(private val repository: CountryRepository) {

    suspend operator fun invoke(): Result<List<Country>> = repository.getAllCountry()
}
