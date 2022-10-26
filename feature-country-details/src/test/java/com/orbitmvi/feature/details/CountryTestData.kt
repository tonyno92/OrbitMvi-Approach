package com.orbitmvi.feature.details

import com.orbitmvi.feature.list.domain.model.Country

internal object CountryTestData {

    val countryDomain = Country(
        id = "Italian Republic".hashCode(),
        name = "Italian Republic",
        flag = "https://flagcdn.com/w320/it.png",
        continents = listOf("Europe"),
        languages = listOf("Italian")
    )
}
