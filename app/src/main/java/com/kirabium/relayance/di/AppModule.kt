package com.kirabium.relayance.di

import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.service.CustomerApi
import com.kirabium.relayance.data.service.CustomerFakeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Hilt module that provides application-wide dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Provides a singleton instance of [CustomerApi] using a fake implementation.
     *
     * @return An instance of [CustomerFakeApi] for testing and development purposes.
     */
    @Provides
    @Singleton
    fun provideCustomerApi(): CustomerApi {
        return CustomerFakeApi()
    }

    /**
     * Provides a singleton instance of [CustomerRepository].
     *
     * @param api The [CustomerApi] to be used by the repository.
     * @return A [CustomerRepository] instance.
     */
    @Provides
    @Singleton
    fun provideCustomerRepository(api: CustomerApi): CustomerRepository {
        return CustomerRepository(api)
    }

    /**
     * Provides a coroutine dispatcher for IO-bound tasks.
     *
     * @return The [Dispatchers.IO] dispatcher.
     */
    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}