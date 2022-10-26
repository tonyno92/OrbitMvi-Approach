package com.orbitmvi.feature.details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.orbitmvi.core.utils.dispatcher.TestDispatcherProvider
import com.orbitmvi.feature.details.CountryTestData.countryDomain
import com.orbitmvi.feature.details.ResultTestData.resultError
import com.orbitmvi.feature.details.domain.usecase.GetCountryByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.orbitmvi.orbit.test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CountryDetailViewModelTest {
    private val useCaseMock = mockk<GetCountryByIdUseCase>()
    private val viewModel = CountryDetailViewModel(useCaseMock, TestDispatcherProvider())
    private val errorMessage = "HTTP 400 Response.error()"
    private val countryId = "Italian Republic".hashCode()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `verify DetailViewModel's getCountryDetails success scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke(countryId) } returns Result.success(countryDomain)

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(CountryDetailAction.OpenCountryDetailAction(countryId))
        }

        // then
        testSubject.assert(CountryDetailState()) {
            states(
                { copy(isLoading = true) },
                { copy(country = countryDomain, isLoading = false) }
            )
        }
    }

    @Test
    fun `verify DetailViewModel's getCountryDetails failure scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke(countryId) } returns resultError

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(CountryDetailAction.OpenCountryDetailAction(countryId))
        }

        // then
        testSubject.assert(CountryDetailState()) {
            states(
                { copy(isLoading = true) },
                { copy(country = null, isLoading = false) }
            )

            postedSideEffects(
                CountryDetailEffect.DetailsErrorEffect("Failed to get country details: $errorMessage")
            )
        }
    }

    @Test
    fun `verify DetailViewModel's getCountryDetails error scenario`() = runTest {
        // given
        coEvery { useCaseMock.invoke(countryId) } throws IOException()

        val testSubject = viewModel.test()

        // when
        testSubject.testIntent {
            dispatch(CountryDetailAction.OpenCountryDetailAction(countryId))
        }

        // then
        testSubject.assert(CountryDetailState()) {
            states(
                { copy(isLoading = true) },
                { copy(country = null, isLoading = false) }
            )

            postedSideEffects(
                CountryDetailEffect.DetailsErrorEffect("Failed to get country details: null")
            )
        }
    }
}
