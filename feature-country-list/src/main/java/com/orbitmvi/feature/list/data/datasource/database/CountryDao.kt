package com.orbitmvi.feature.list.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orbitmvi.feature.list.data.datasource.database.model.CountryEntityModel

@Dao
interface CountryDao {

    @Query("SELECT NOT EXISTS(SELECT * FROM countries LIMIT 1)")
    suspend fun isEmpty(): Boolean

    @Query("SELECT * FROM countries ORDER BY name")
    suspend fun getAll(): List<CountryEntityModel>

    @Query("SELECT * FROM countries where id = :id")
    suspend fun getCountry(id: Int): CountryEntityModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(albums: List<CountryEntityModel>)
}
