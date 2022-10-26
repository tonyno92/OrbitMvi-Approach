package com.orbitmvi.feature.list.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orbitmvi.feature.list.data.datasource.database.model.CountryEntityModel

@Database(entities = [CountryEntityModel::class], version = 1, exportSchema = false)
internal abstract class CountryDatabase : RoomDatabase() {

    abstract fun countries(): CountryDao
}
