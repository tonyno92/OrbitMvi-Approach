package com.orbitmvi.feature.details.presentation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.Coil
import com.agoda.kakao.screen.Screen.Companion.idle
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.orbitmvi.core.utils.dispatcher.TestDispatcherProvider
import com.orbitmvi.core.utils.image.TestImageLoader
import com.orbitmvi.feature.details.R
import com.orbitmvi.feature.details.CountryUiTestData.countryDomain
import com.orbitmvi.feature.details.domain.repository.CountryDetailRepository
import com.orbitmvi.feature.details.domain.usecase.GetCountryByIdUseCase
import com.orbitmvi.feature.details.kakao.hasAlpha
import com.orbitmvi.feature.details.navigation.CountryDetailNavigation
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class CountryDetailsFragmentTest : KoinTest {
    private val countryDetailNavigation = mockk<CountryDetailNavigation>()
    private val repository = mockk<CountryDetailRepository>()
    private val countryId = "Italian Republic".hashCode()

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    factory { countryDetailNavigation }
                    factory { repository }
                    factory { GetCountryByIdUseCase(get()) }
                    viewModel { CountryDetailViewModel(get(), TestDispatcherProvider()) }
                }
            )
        }

        Coil.setImageLoader(TestImageLoader())
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun verifyCountryDetailsIsPopulated_givenValidData() {
        coEvery { repository.getCountryDetailsResult(countryId) } returns Result.success(countryDomain)

        launchFragmentInContainer<CountryDetailFragment>(
            Bundle().apply {
                putInt("country_id", countryId)
            },
            R.style.Theme_AppCompat_Light_NoActionBar
        )

        onScreen<CountryDetailsScreen> {
            flag {
                isVisible()
            }
            toolbar {
                isVisible()
            }
            overview {
                isVisible()
                hasText(countryDomain.name)
            }
            continent {
                isVisible()
                hasText(countryDomain.continents.joinToString())
            }
            language {
                isVisible()
                hasText(countryDomain.languages?.joinToString() ?: "")
            }
            coatOfArms {
                isVisible()
                hasAlpha(1f)
                idle(1000)
            }

            // Scroll to top, so that coatOfArms becomes "invisible"
            appBar {
                collapse()
                idle(1000)
            }
            coatOfArms {
                isVisible()
                hasAlpha(0f)
            }

            // Scroll to bottom, and coatOfArms is visible again
            appBar {
                expand()
                idle(1000)
            }
            coatOfArms {
                isVisible()
                hasAlpha(1f)
            }
        }
    }
}
