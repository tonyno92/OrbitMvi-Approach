package com.orbitmvi.feature.details.presentation

import com.orbitmvi.core.mvi.MviAction
import com.orbitmvi.core.mvi.MviEffect
import com.orbitmvi.core.mvi.MviState
import com.orbitmvi.feature.list.domain.model.Country

internal sealed class CountryDetailAction : MviAction {
    data class OpenCountryDetailAction(val countryId: Int) : CountryDetailAction()
}

internal data class CountryDetailState(
    val isLoading: Boolean = false,
    val country: Country? = null
) : MviState

internal sealed class CountryDetailEffect : MviEffect {
    data class DetailsErrorEffect(val message: String) : CountryDetailEffect()
}
