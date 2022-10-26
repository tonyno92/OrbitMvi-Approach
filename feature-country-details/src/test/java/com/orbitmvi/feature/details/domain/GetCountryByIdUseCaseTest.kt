package com.orbitmvi.feature.details.domain

import com.orbitmvi.feature.details.CountryTestData.countryDomain
import com.orbitmvi.feature.details.ResultTestData.resultError
import com.orbitmvi.feature.details.ResultTestData.resultSuccess
import com.orbitmvi.feature.details.domain.repository.CountryDetailRepository
import com.orbitmvi.feature.details.domain.usecase.GetCountryByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCountryByIdUseCaseTest {
    private val repositoryMock = mockk<CountryDetailRepository>()
    private val useCase = GetCountryByIdUseCase(repositoryMock)
    private val countryId = "Italian Republic".hashCode()

    @Test
    fun `verify success case for GetCountryByIdUseCase`() = runTest {
        // given
        coEvery { repositoryMock.getCountryDetailsResult(countryId) } returns resultSuccess

        // when
        val result = useCase.invoke(countryId)

        // then
        Assert.assertEquals(true, result.isSuccess)
        Assert.assertEquals(countryDomain, result.getOrNull())
    }

    @Test
    fun `verify error case for GetCountryByIdUseCase`() = runTest {
        // given
        coEvery { repositoryMock.getCountryDetailsResult(countryId) } returns resultError

        // when
        val result = useCase.invoke(countryId)

        // then
        Assert.assertEquals(true, result.isFailure)
        Assert.assertEquals("HTTP 400 Response.error()", result.exceptionOrNull()?.message)
    }
}
