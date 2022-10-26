package com.orbitmvi.core.utils.di

import com.orbitmvi.core.utils.dispatcher.AppDispatcherProvider
import com.orbitmvi.core.utils.dispatcher.DispatcherProvider
import org.koin.dsl.module

object CoreUtils {
    val module = module {
        single<DispatcherProvider> { AppDispatcherProvider() }
    }
}
