package com.orbitmvi.feature.list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.orbitmvi.core.utils.dispatcher.TestDispatcherProvider
import com.orbitmvi.feature.list.CountryTestData.countryDomain
import com.orbitmvi.feature.list.ResultTestData.resultError
import com.orbitmvi.feature.list.ResultTestData.resultSuccess
import com.orbitmvi.feature.list.domain.usecase.DiscoverAllCountryUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.orbitmvi.orbit.test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CountryListViewModelTest {
    private val useCaseMock = mockk<DiscoverAllCountryUseCase>()
    private val viewModel = CountryListViewModel(useCaseMock, TestDispatcherProvider())
    private val errorMessage = "HTTP 400 Response.error()"

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `verify ListViewModel's request all country success scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke() } returns resultSuccess

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(CountryListAction.LoadAllCountryAction)
        }

        // then
        testSubject.assert(CountryListState()) {
            states(
                { copy(isLoading = true) },
                { copy(countries = listOf(countryDomain), isLoading = false) }
            )
        }
    }

    @Test
    fun `verify ListViewModel's request all country failure scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke() } returns resultError

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(CountryListAction.LoadAllCountryAction)
        }

        // then
        testSubject.assert(CountryListState()) {
            states(
                { copy(isLoading = true) },
                { copy(countries = listOf(), isLoading = false) }
            )

            postedSideEffects(
                CountryListEffect.ListErrorEffect("Failed to load countries: $errorMessage")
            )
        }
    }

    @Test
    fun `verify ListViewModel's request all country error scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke() } throws IOException()

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(CountryListAction.LoadAllCountryAction)
        }

        // then
        testSubject.assert(CountryListState()) {
            states(
                { copy(isLoading = true) },
                { copy(countries = listOf(), isLoading = false) }
            )

            postedSideEffects(
                CountryListEffect.ListErrorEffect("Failed to load countries: null")
            )
        }
    }
}
