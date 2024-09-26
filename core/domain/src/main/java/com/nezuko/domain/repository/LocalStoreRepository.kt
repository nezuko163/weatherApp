package com.nezuko.domain.repository

import javax.inject.Singleton

@Singleton
interface LocalStoreRepository {
    fun addCity(city: String)

    fun getCities(): List<String>

    fun setCities(cities: List<String>)

    fun clearCity(city: String)

    fun clearCities()
}