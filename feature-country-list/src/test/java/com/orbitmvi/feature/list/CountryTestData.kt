package com.orbitmvi.feature.list

import com.orbitmvi.feature.list.data.datasource.api.model.CoatOfArmsApiModel
import com.orbitmvi.feature.list.data.datasource.api.model.CountryApiModel
import com.orbitmvi.feature.list.data.datasource.api.model.FlagsApiModel
import com.orbitmvi.feature.list.data.datasource.api.model.NameApiModel
import com.orbitmvi.feature.list.data.datasource.database.model.CountryEntityModel
import com.orbitmvi.feature.list.domain.model.Country

internal object CountryTestData {

    /**
     * DATA
     */
    val countryApiModel = CountryApiModel(
        name = NameApiModel(
            "Italy",
            "Italian Republic"
        ),
        coatOfArms = CoatOfArmsApiModel(
            "https://mainfacts.com/media/images/coats_of_arms/it.png",
            "https://mainfacts.com/media/images/coats_of_arms/it.svg"
        ),
        flags = FlagsApiModel(
            "https://flagcdn.com/w320/it.png",
            "https://flagcdn.com/it.svg"
        ),
        area = 301336.0,
        continents = listOf("Europe"),
        languages = mapOf("ita" to "Italian"),
        independent = true,
        region = "Europe"
    )

    val countryEntityModel = CountryEntityModel(
        name = "Italian Republic",
        flag = "https://flagcdn.com/w320/it.png",
        continents = "Europe",
        languages = "Italian",
        coatOfArms = "https://mainfacts.com/media/images/coats_of_arms/it.png"
    )

    /**
     * DOMAIN
     */
    val countryDomain = Country(
        id = "Italian Republic".hashCode(),
        name = "Italian Republic",
        flag = "https://flagcdn.com/w320/it.png",
        continents = listOf("Europe"),
        languages = listOf("Italian")
    )
}
