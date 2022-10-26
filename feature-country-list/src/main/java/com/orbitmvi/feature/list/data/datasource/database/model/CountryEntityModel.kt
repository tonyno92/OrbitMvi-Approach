package com.orbitmvi.feature.list.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntityModel(
    val name: String,
    val flag: String,
    val continents: String,
    val languages: String?,
    val coatOfArms: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = name.hashCode()
}
