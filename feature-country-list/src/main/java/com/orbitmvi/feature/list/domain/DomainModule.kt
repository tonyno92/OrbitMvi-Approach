package com.orbitmvi.feature.list.domain

import com.orbitmvi.feature.list.domain.usecase.DiscoverAllCountryUseCase
import org.koin.dsl.module

internal object DomainModule {

    val module = module {
        single { DiscoverAllCountryUseCase(get()) }
    }
}
