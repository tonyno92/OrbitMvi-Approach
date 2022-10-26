package com.orbitmvi.approach

import android.app.Application
import com.orbitmvi.core.network.di.CoreNetwork
import com.orbitmvi.core.prefs.di.CorePrefs
import com.orbitmvi.core.utils.di.CoreUtils
import com.orbitmvi.feature.details.di.FeatureDetail
import com.orbitmvi.feature.list.di.FeatureList
import com.orbitmvi.approach.di.Navigation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.fileProperties

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            properties(
                mapOf(
                    "is_debug" to BuildConfig.DEBUG.toString()
                )
            )
            fileProperties()
            modules(
                listOf(
                    CoreNetwork.module,
                    CorePrefs.module,
                    CoreUtils.module,
                    FeatureList.module,
                    FeatureDetail.module,
                    Navigation.module
                )
            )
        }
    }
}
