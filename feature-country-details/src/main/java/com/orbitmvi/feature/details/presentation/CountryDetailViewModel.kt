package com.orbitmvi.feature.details.presentation

import com.orbitmvi.core.mvi.MviViewModel
import com.orbitmvi.core.utils.dispatcher.DispatcherProvider
import com.orbitmvi.core.utils.mockSafeFold
import com.orbitmvi.feature.details.domain.usecase.GetCountryByIdUseCase
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.io.IOException

internal class CountryDetailViewModel(
    private val useCase: GetCountryByIdUseCase,
    private val dispatcherProvider: DispatcherProvider
) : MviViewModel<CountryDetailAction, CountryDetailState, CountryDetailEffect>(CountryDetailState(), dispatcherProvider) {

    override fun dispatch(action: CountryDetailAction) = intent {
        when (action) {
            is CountryDetailAction.OpenCountryDetailAction -> {
                reduce {
                    state.copy(isLoading = true)
                }
                val newState: CountryDetailState = try {
                    val result = withContext(dispatcherProvider.io) {
                        useCase(action.countryId)
                            .mockSafeFold(
                                onSuccess = { state.copy(isLoading = false, country = it) },
                                onFailure = {
                                    postSideEffect(CountryDetailEffect.DetailsErrorEffect("Failed to get country details: ${it.message}"))
                                    state.copy(isLoading = false)
                                }
                            )
                    }
                    result
                } catch (e: IOException) {
                    postSideEffect(CountryDetailEffect.DetailsErrorEffect("Failed to get country details: ${e.localizedMessage}"))
                    state.copy(isLoading = false)
                }

                reduce {
                    state.copy(isLoading = newState.isLoading, country = newState.country)
                }
            }
        }
    }
}
