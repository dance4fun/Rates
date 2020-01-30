package com.tiredbones.rates

import io.reactivex.Single

interface RatesRepository {

  fun getRates(baseCurrency: String): Single<CurrencyRates>
}
