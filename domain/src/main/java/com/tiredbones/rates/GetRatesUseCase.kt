package com.tiredbones.rates

import javax.inject.Inject
import com.tiredbones.rates.CurrencyRates.Currency.EUR
import com.tiredbones.rates.CurrencyRates.Currency
import io.reactivex.Single

class GetRatesUseCase @Inject constructor(
    private val repository: RatesRepository
) {

  operator fun invoke(baseCurrency: Currency): Single<CurrencyRates> = repository.getRates(baseCurrency.code)
}
