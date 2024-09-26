package com.nezuko.data.repository

import com.nezuko.data.source.local.LocalSource
import com.nezuko.domain.repository.LocalStoreRepository
import javax.inject.Inject

class LocalStoreRepositoryImpl @Inject constructor(
    private val localSource: LocalSource
) : LocalStoreRepository {
    override fun addCity(city: String) {
        localSource.addStringItem(string = city)
    }

    override fun getCities(): List<String> = localSource.getStringList()

    override fun setCities(cities: List<String>) {
        localSource.saveStringList(list = cities)
    }

    override fun clearCity(city: String) {
        localSource.removeString(city)
    }

    override fun clearCities() {
        localSource.clearStorage()
    }
}