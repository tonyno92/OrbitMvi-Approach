package com.orbitmvi.feature.details.di

import com.orbitmvi.feature.details.data.DataModule
import com.orbitmvi.feature.details.domain.DomainModule
import com.orbitmvi.feature.details.presentation.CountryDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FeatureDetail {

    val module = module {
        includes(
            DataModule.module,
            DomainModule.module
        )

        viewModel { CountryDetailViewModel(get(), get()) }
    }
}
