package com.kirabium.relayance.di

import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.service.CustomerApi
import com.kirabium.relayance.data.service.CustomerFakeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCustomerApi(): CustomerApi {
        return CustomerFakeApi()
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(api: CustomerApi): CustomerRepository {
        return CustomerRepository(api)
    }

}