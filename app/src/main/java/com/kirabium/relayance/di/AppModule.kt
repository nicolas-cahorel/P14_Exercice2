package com.kirabium.relayance.di

import com.kirabium.relayance.data.repository.CustomerRepository
import dagger.hilt.InstallIn
import com.kirabium.relayance.data.service.CustomerApi
import com.kirabium.relayance.data.service.CustomerFakeApi
import com.kirabium.relayance.domain.utils.GenerateDateUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDateUtils() : GenerateDateUtils {
        return GenerateDateUtils()
    }

    @Provides
    @Singleton
    fun provideCustomerApi(dateUtils: GenerateDateUtils): CustomerApi {
        return CustomerFakeApi(dateUtils)
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(api: CustomerApi): CustomerRepository {
        return CustomerRepository(api)
    }

}