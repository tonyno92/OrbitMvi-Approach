package com.orbitmvi.feature.list.presentation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.Coil
import com.agoda.kakao.screen.Screen.Companion.idle
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.orbitmvi.core.utils.dispatcher.TestDispatcherProvider
import com.orbitmvi.core.utils.image.TestImageLoader
import com.orbitmvi.feature.list.CountryUiTestData.countryListDomain
import com.orbitmvi.feature.list.R
import com.orbitmvi.feature.list.domain.repository.CountryRepository
import com.orbitmvi.feature.list.domain.usecase.DiscoverAllCountryUseCase
import com.orbitmvi.feature.list.navigation.CountryListNavigation
import io.mockk.coEvery
import io.mockk.mockk
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import retrofit2.HttpException
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class CountryListFragmentTest : KoinTest {
    private val countryListNavigation = mockk<CountryListNavigation>()
    private val repository = mockk<CountryRepository>()

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    factory { countryListNavigation }
                    factory { DiscoverAllCountryUseCase(repository) }
                    viewModel { CountryListViewModel(get(), TestDispatcherProvider()) }
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
    fun verifyCountryListIsPopulated_givenValidData() {
        coEvery { repository.getAllCountry() } returns Result.success(countryListDomain)

        launchFragmentInContainer<CountryListFragment>(Bundle(), R.style.Theme_AppCompat_Light_NoActionBar)

        onScreen<CountryListScreen> {
            recycler {
                isVisible()
                hasSize(countryListDomain.size)

                firstChild<CountryListScreen.CountryItem> {
                    isDisplayed()
                    image {
                        isVisible()
                        hasTag(countryListDomain.first().name)
                    }
                }

                scrollToEnd()

                lastChild<CountryListScreen.CountryItem> {
                    isDisplayed()
                    image {
                        isVisible()
                        hasTag(countryListDomain.last().name)
                    }
                }
            }
        }
    }

    @Test
    fun verifyCountryListIsEmpty_givenNoData_thenRetry() {
        coEvery { repository.getAllCountry() } returns Result.failure(
            HttpException(
                Response.error<String>(
                    400,
                    RealResponseBody("type", 0, Buffer())
                )
            )
        )

        launchFragmentInContainer<CountryListFragment>(Bundle(), R.style.Theme_AppCompat_Light_NoActionBar)

        // verify that we have no data in recycler as useCase returns unsuccessful NetworkResponse
        onScreen<CountryListScreen> {
            recycler {
                hasSize(0)
                idle(500)
            }
        }

        // next call to useCase should return successful NetworkResponse
        coEvery { repository.getAllCountry() } returns Result.success(countryListDomain)

        onScreen<CountryListScreen> {
            // show snackbar when we have problems retrieving data from network
            snackBar {
                isDisplayed()

                action {
                    hasText("Retry")
                    // click retry
                    click()
                    idle(500)
                }

                doesNotExist()
            }

            // verify we got proper results
            recycler {
                hasSize(countryListDomain.size)
            }
        }
    }
}
