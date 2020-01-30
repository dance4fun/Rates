package com.tiredbones.repository.mapper

import com.tiredbones.api_client.response.RatesResponse
import com.tiredbones.rates.CurrencyRates

fun RatesResponse.map() = CurrencyRates(
    base = CurrencyRates.Currency.from(base),
    rates = rates.mapKeys { CurrencyRates.Currency.from(it.key) }
)
