package com.orbitmvi.feature.list.data.datasource.api.model

import com.orbitmvi.feature.list.data.datasource.database.model.CountryEntityModel

internal data class CountryApiModel(
    val name: NameApiModel,
    val coatOfArms: CoatOfArmsApiModel,
    val flags: FlagsApiModel,
    val area: Double,
    val continents: List<String>,
    val languages: Map<String, String>? = emptyMap(),
    val independent: Boolean,
    val region: String
)

internal data class NameApiModel(
    val common: String,
    val official: String
)

internal data class CoatOfArmsApiModel(
    val png: String,
    val svg: String
)

internal data class FlagsApiModel(
    val png: String,
    val svg: String
)

internal fun CountryApiModel.toEntityModel(): CountryEntityModel =
    CountryEntityModel(
        name = name.official,
        flag = flags.png,
        coatOfArms = coatOfArms.png,
        continents = continents.joinToString(";"),
        languages = languages?.values?.joinToString(";")
    )
