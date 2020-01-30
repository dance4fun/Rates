package com.tiredbones.repository

import com.tiredbones.repository.mapper.map
import com.tiredbones.api_client.RatesApi
import com.tiredbones.api_client.response.RatesResponse
import com.tiredbones.rates.CurrencyRates
import com.tiredbones.rates.RatesRepository
import io.reactivex.Single
import javax.inject.Inject

class RatesRemoteRepository @Inject constructor(
    private val ratesApi: RatesApi
) : RatesRepository {

  override fun getRates(baseCurrency: String): Single<CurrencyRates> =
      ratesApi.getRates(baseCurrency).map(RatesResponse::map)
}
