package com.tiredbones.rates.di.module

import androidx.lifecycle.ViewModelProvider
import com.tiredbones.rates.base.BaseViewModel
import com.tiredbones.rates.di.ViewModelFactory
import com.tiredbones.rates.di.ViewModelKey
import com.tiredbones.rates.feature.rates.RatesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

  @Binds
  @Singleton
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(RatesViewModel::class)
  internal abstract fun bindRatesViewModel(viewModel: RatesViewModel): BaseViewModel

}
