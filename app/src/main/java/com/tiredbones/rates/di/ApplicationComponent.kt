package com.tiredbones.rates.di

import com.tiredbones.rates.RatesApplication
import com.tiredbones.rates.base.BaseActivity
import com.tiredbones.rates.di.module.NetworkModule
import com.tiredbones.rates.di.module.RepositoryModule
import com.tiredbones.rates.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      ViewModelModule::class,
      RepositoryModule::class,
      NetworkModule::class
    ]
)
interface ApplicationComponent {

  fun inject(app: BaseActivity)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: RatesApplication): Builder

    fun build(): ApplicationComponent
  }
}
