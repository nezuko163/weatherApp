package com.nezuko.data

import com.nezuko.data.source.remote.ApiRemoteSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @ParameterizedTest
    @CsvSource("Москва", "Санкт-Петербург", "Жешарт")
    suspend fun getCityName(name: String) {
        val api = ApiRemoteSource()
        withContext(Dispatchers.IO) {
            assertNotNull(api.getCoordinatesCityByName(name))
        }
    }

}