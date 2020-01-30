package com.tiredbones.rates.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tiredbones.api_client.RatesApi
import com.tiredbones.rates.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @Singleton
  internal fun provideHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
      builder.addInterceptor(HttpLoggingInterceptor())
          .addNetworkInterceptor(StethoInterceptor())
    }
    return builder.build()
  }

  @Provides
  @Singleton
  fun provideMainRetrofit(okHttpClient: OkHttpClient): Retrofit =
      Retrofit.Builder()
          .baseUrl(BuildConfig.BASE_URL)
          .client(okHttpClient)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build()

  @Provides
  @Singleton
  internal fun provideRatesApi(retrofit: Retrofit): RatesApi = retrofit.create(RatesApi::class.java)

}
