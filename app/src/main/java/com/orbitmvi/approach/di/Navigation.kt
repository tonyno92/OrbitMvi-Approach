package com.orbitmvi.approach.di

import com.orbitmvi.feature.details.navigation.CountryDetailNavigation
import com.orbitmvi.feature.list.navigation.CountryListNavigation
import com.orbitmvi.approach.navigation.Navigator
import org.koin.dsl.binds
import org.koin.dsl.module

object Navigation {

    val module = module {
        single { Navigator() } binds arrayOf(
            CountryListNavigation::class,
            CountryDetailNavigation::class
        )
    }
}
