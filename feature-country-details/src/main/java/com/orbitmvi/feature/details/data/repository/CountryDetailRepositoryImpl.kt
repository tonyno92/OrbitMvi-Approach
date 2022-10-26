package com.orbitmvi.feature.details.data.repository

import com.orbitmvi.feature.details.domain.repository.CountryDetailRepository
import com.orbitmvi.feature.list.data.datasource.database.CountryDao
import com.orbitmvi.feature.list.domain.converter.toDomainModel
import com.orbitmvi.feature.list.domain.model.Country

internal class CountryDetailRepositoryImpl(private val countryDao: CountryDao) : CountryDetailRepository {

    override suspend fun getCountryDetailsResult(countryId: Int): Result<Country> = Result.success(
        countryDao.getCountry(countryId).toDomainModel()
    )
}
