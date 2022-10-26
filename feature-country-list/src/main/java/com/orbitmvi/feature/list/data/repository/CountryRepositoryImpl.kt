package com.orbitmvi.feature.list.data.repository

import com.orbitmvi.feature.list.data.datasource.api.model.CountryApiModel
import com.orbitmvi.feature.list.data.datasource.api.model.toEntityModel
import com.orbitmvi.feature.list.data.datasource.api.service.CountryService
import com.orbitmvi.feature.list.data.datasource.database.CountryDao
import com.orbitmvi.feature.list.domain.converter.toDomainModel
import com.orbitmvi.feature.list.domain.model.Country
import com.orbitmvi.feature.list.domain.repository.CountryRepository
import retrofit2.HttpException
import java.io.IOException

internal class CountryRepositoryImpl(
    private val countryService: CountryService,
    private val countryDao: CountryDao
) : CountryRepository {

    override suspend fun getAllCountry(): Result<List<Country>> = try {
        if (countryDao.isEmpty()) {
            countryService.getAllCountry().let { response ->
                Result.success(
                    response
                        .takeIf { it.isSuccessful }
                        ?.body()
                        ?.also { storeInCache(it) }
                        ?.map { it.toDomainModel() }
                        ?.sortedBy { it.name }
                        ?: throw HttpException(response)
                )
            }
        } else {
            Result.success(
                countryDao.getAll()
                    .map { it.toDomainModel() }
            )
        }
    } catch (e: IOException) {
        Result.failure(e)
    }

    private suspend fun storeInCache(data: List<CountryApiModel>) {
        countryDao.insertCountry(data.map { it.toEntityModel() })
    }
}
