package com.orbitmvi.feature.list.presentation

import com.orbitmvi.core.mvi.MviAction
import com.orbitmvi.core.mvi.MviEffect
import com.orbitmvi.core.mvi.MviState
import com.orbitmvi.feature.list.domain.model.Country

internal sealed class CountryListAction : MviAction {
    object LoadAllCountryAction : CountryListAction()
}

internal data class CountryListState(
    val isLoading: Boolean = false,
    val countries: List<Country> = listOf()
) : MviState

internal sealed class CountryListEffect : MviEffect {
    data class ListErrorEffect(val message: String) : CountryListEffect()
}
