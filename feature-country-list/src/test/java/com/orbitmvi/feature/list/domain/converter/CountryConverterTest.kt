package com.orbitmvi.feature.list.domain.converter

import com.orbitmvi.feature.list.CountryTestData.countryApiModel
import com.orbitmvi.feature.list.CountryTestData.countryEntityModel
import com.orbitmvi.feature.list.data.datasource.api.model.toEntityModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryConverterTest {

    @Test
    fun `verify country mapping apiModel to entityModel`() {
        val countryEntity = countryApiModel.toEntityModel()
        assertEquals(countryEntity.id, countryApiModel.name.official.hashCode())
        assertEquals(countryEntity.name, countryApiModel.name.official)
        assertEquals(countryEntity.flag, countryApiModel.flags.png)
        // Add here more assert if you want more check
    }

    @Test
    fun `verify country mapping apiModel to domain`() {
        val countryDomain = countryApiModel.toDomainModel()
        assertEquals(countryApiModel.name.official, countryDomain.name)
        assertEquals(countryApiModel.flags.png, countryDomain.flag)
        // Add here more assert if you want more check
    }

    @Test
    fun `verify country mapping entityModel to domain`() {
        val countryDomain = countryEntityModel.toDomainModel()
        assertEquals(countryEntityModel.id, countryDomain.id)
        assertEquals(countryEntityModel.name, countryDomain.name)
        assertEquals(countryEntityModel.flag, countryDomain.flag)
        // Add here more assert if you want more check
    }
}
