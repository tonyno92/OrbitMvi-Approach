package com.orbitmvi.feature.list.presentation

import com.orbitmvi.core.mvi.MviViewModel
import com.orbitmvi.core.utils.dispatcher.DispatcherProvider
import com.orbitmvi.core.utils.mockSafeFold
import com.orbitmvi.feature.list.domain.usecase.DiscoverAllCountryUseCase
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import java.io.IOException

internal class CountryListViewModel(
    private val discoverUseCase: DiscoverAllCountryUseCase,
    dispatcherProvider: DispatcherProvider
) : MviViewModel<CountryListAction, CountryListState, CountryListEffect>(CountryListState(), dispatcherProvider) {

    override fun dispatch(action: CountryListAction) = intent {
        when (action) {
            is CountryListAction.LoadAllCountryAction -> {
                reduce {
                    state.copy(isLoading = true)
                }

                val newState: CountryListState = try {
                    discoverUseCase()
                        .mockSafeFold(
                            onSuccess = {
                                state.copy(countries = it, isLoading = false)
                            },
                            onFailure = {
                                postSideEffect(CountryListEffect.ListErrorEffect("Failed to load countries: ${it.message}"))
                                state.copy(isLoading = false)
                            }
                        )
                } catch (e: IOException) {
                    postSideEffect(CountryListEffect.ListErrorEffect("Failed to load countries: ${e.localizedMessage}"))
                    state.copy(isLoading = false)
                }

                reduce {
                    state.copy(countries = newState.countries, isLoading = newState.isLoading)
                }
            }
        }
    }
}
