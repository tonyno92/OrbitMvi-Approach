package com.orbitmvi.feature.list.data

import androidx.room.Room
import com.orbitmvi.feature.list.data.datasource.api.service.CountryService
import com.orbitmvi.feature.list.data.datasource.database.CountryDatabase
import com.orbitmvi.feature.list.data.repository.CountryRepositoryImpl
import com.orbitmvi.feature.list.domain.repository.CountryRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal object DataModule {

    val module = module {

        /**
         * Country Instance Retrofit and Services
         */
        single {
            get<Retrofit>().create(CountryService::class.java)
        }

        /**
         * Database countries uses for cache
         */
        single {
            Room.databaseBuilder(
                get(),
                CountryDatabase::class.java,
                "Countries.db"
            ).build()
        }
        single { get<CountryDatabase>().countries() }

        /**
         * Repository
         */
        single<CountryRepository> { CountryRepositoryImpl(get(), get()) }
    }
}
