package com.orbitmvi.feature.details.domain

import com.orbitmvi.feature.details.domain.usecase.GetCountryByIdUseCase
import org.koin.dsl.module

internal object DomainModule {

    val module = module {
        single { GetCountryByIdUseCase(get()) }
    }
}
