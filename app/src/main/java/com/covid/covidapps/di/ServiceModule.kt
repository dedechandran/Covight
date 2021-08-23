package com.covid.covidapps.di

import com.covid.covidapps.datasource.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideService(retrofit: Retrofit): Service{
        return retrofit.create(Service::class.java)
    }
}