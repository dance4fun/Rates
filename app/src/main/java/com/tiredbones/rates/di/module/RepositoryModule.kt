package com.tiredbones.rates.di.module

import com.tiredbones.rates.RatesRepository
import com.tiredbones.repository.RatesRemoteRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

  @Binds
  internal abstract fun provideRatesRemoteRepository(repository: RatesRemoteRepository): RatesRepository

}
