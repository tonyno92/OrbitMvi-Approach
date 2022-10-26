package com.orbitmvi.feature.list

import com.orbitmvi.feature.list.data.datasource.api.model.CountryApiModel
import com.orbitmvi.feature.list.data.datasource.api.model.FlagsApiModel
import com.orbitmvi.feature.list.data.datasource.api.model.NameApiModel
import com.orbitmvi.feature.list.domain.converter.toDomainModel
import io.mockk.mockk

internal object CountryUiTestData {

    /**
     * DATA
     */
    private val countryListData = (1..30).map { id ->
        CountryApiModel(
            name = NameApiModel("", "$id"),
            coatOfArms = mockk(),
            flags = FlagsApiModel("", ""),
            area = 0.0,
            continents = emptyList(),
            languages = emptyMap(),
            independent = false,
            region = ""
        )
    }.toList()

    /**
     * DOMAIN
     */

    val countryListDomain = countryListData.map { it.toDomainModel() }
}
