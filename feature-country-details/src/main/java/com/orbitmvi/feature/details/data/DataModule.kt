package com.orbitmvi.feature.details.data

import com.orbitmvi.feature.details.data.repository.CountryDetailRepositoryImpl
import com.orbitmvi.feature.details.domain.repository.CountryDetailRepository
import org.koin.dsl.module

internal object DataModule {

    val module = module {
        single<CountryDetailRepository> { CountryDetailRepositoryImpl(get()) }
    }
}
