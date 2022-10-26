package com.orbitmvi.feature.list.domain

import com.orbitmvi.feature.list.CountryTestData.countryDomain
import com.orbitmvi.feature.list.ResultTestData.resultError
import com.orbitmvi.feature.list.ResultTestData.resultSuccess
import com.orbitmvi.feature.list.domain.repository.CountryRepository
import com.orbitmvi.feature.list.domain.usecase.DiscoverAllCountryUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DiscoverAllCountryUseCaseTest {
    private val repositoryMock = mockk<CountryRepository>()
    private val useCase = DiscoverAllCountryUseCase(repositoryMock)

    @Test
    fun `verify success case for DiscoverAllCountryUseCase`() = runTest {
        // given
        coEvery { repositoryMock.getAllCountry() } returns resultSuccess

        // when
        val result = useCase()

        // then
        assertEquals(true, result.isSuccess)
        assertEquals(listOf(countryDomain), result.getOrNull())
    }

    @Test
    fun `verify error case for DiscoverAllCountryUseCase`() = runTest {
        // given
        coEvery { repositoryMock.getAllCountry() } returns resultError

        // when
        val result = useCase()

        // then
        assertEquals(false, result.isSuccess)
        assertEquals("HTTP 400 Response.error()", result.exceptionOrNull()?.message)
    }
}
