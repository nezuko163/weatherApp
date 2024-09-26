package com.nezuko.data.di

import android.content.Context
import com.nezuko.data.repository.CityRepositoryImpl
import com.nezuko.data.repository.LocalStoreRepositoryImpl
import com.nezuko.data.source.local.LocalSource
import com.nezuko.data.source.remote.ApiRemoteSource
import com.nezuko.domain.repository.CityRepository
import com.nezuko.domain.repository.LocalStoreRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindCityRepository(impl: CityRepositoryImpl): CityRepository

    @Binds
    fun bindLocalStoreRepository(impl: LocalStoreRepositoryImpl): LocalStoreRepository

    companion object {
        @Singleton
        @Provides
        fun providesApiRemoteSource(): ApiRemoteSource = ApiRemoteSource()

        @Singleton
        @Provides
        fun provideLocalSource(@ApplicationContext context: Context) = LocalSource(context)
    }
}