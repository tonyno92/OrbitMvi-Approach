// ktlint-disable filename
package com.orbitmvi.feature.list.domain.converter

import com.orbitmvi.feature.list.domain.model.Country

internal fun com.orbitmvi.feature.list.data.datasource.api.model.CountryApiModel.toDomainModel() =
    Country(
        id = name.official.hashCode(),
        name = name.official,
        flag = flags.png,
        coatOfArms = coatOfArms.png,
        continents = continents,
        languages = languages?.values?.toList()
    )

fun com.orbitmvi.feature.list.data.datasource.database.model.CountryEntityModel.toDomainModel() =
    Country(
        id = id,
        name = name,
        flag = flag,
        coatOfArms = coatOfArms,
        continents = continents.split(';'),
        languages = languages?.split(';')
    )
