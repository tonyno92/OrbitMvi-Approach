package com.orbitmvi.feature.list.domain.model

data class Country(
    val id: Int,
    val name: String,
    val flag: String,
    val continents: List<String>,
    val languages: List<String>? = null,
    val coatOfArms: String? = null
)
