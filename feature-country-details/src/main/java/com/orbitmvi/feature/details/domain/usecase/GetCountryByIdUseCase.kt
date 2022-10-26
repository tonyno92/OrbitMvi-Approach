package com.orbitmvi.feature.details.domain.usecase

import com.orbitmvi.feature.details.domain.repository.CountryDetailRepository

internal class GetCountryByIdUseCase(private val repository: CountryDetailRepository) {

    suspend operator fun invoke(countryId: Int) =
        repository.getCountryDetailsResult(countryId)
}
