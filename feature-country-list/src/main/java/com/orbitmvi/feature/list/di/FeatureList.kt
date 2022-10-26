package com.orbitmvi.feature.list.di

import com.orbitmvi.feature.list.data.DataModule
import com.orbitmvi.feature.list.domain.DomainModule
import com.orbitmvi.feature.list.presentation.CountryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FeatureList {

    val module = module {
        includes(
            DataModule.module,
            DomainModule.module
        )

        viewModel { CountryListViewModel(get(), get()) }
    }
}
