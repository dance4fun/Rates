package com.tiredbones.rates.feature.rates

import com.tiredbones.rates.CurrencyRates
import com.tiredbones.rates.CurrencyRates.Currency
import com.tiredbones.rates.R
import com.tiredbones.rates.feature.rates.list.RateItem
import java.math.BigDecimal
import javax.inject.Inject

class RatesMapper @Inject constructor() {

  fun recalculateAmounts(items: List<RateItem>, baseCurrencyAmount: BigDecimal): List<RateItem> =
      items.map { it.copy(amount = it.rate.toBigDecimal() * baseCurrencyAmount) }

  fun mapRatesToUiItems(currencyRates: CurrencyRates, baseCurrencyAmount: BigDecimal): List<RateItem> {
    val ratesList = mutableListOf<RateItem>()
    ratesList.add(getItem(currencyRates.base, 1f, baseCurrencyAmount))

    currencyRates.rates.forEach { (currency, rate) ->
      val amount = rate.toBigDecimal() * baseCurrencyAmount
      ratesList.add(getItem(currency, rate, amount))
    }

    return ratesList
  }

  private fun getItem(currency: Currency, rate: Float, amount: BigDecimal): RateItem =
      RateItem(
          id = currency,
          rate = rate,
          amount = amount,
          name = getCurrencyName(currency),
          imageRes = getCurrencyIcon(currency)
      )

  private fun getCurrencyName(currency: Currency): Int =
      when (currency) {
        Currency.AUD -> R.string.currency_name_aud
        Currency.BGN -> R.string.currency_name_bgn
        Currency.BRL -> R.string.currency_name_brl
        Currency.CAD -> R.string.currency_name_cad
        Currency.CHF -> R.string.currency_name_chf
        Currency.CNY -> R.string.currency_name_cny
        Currency.CZK -> R.string.currency_name_czk
        Currency.DKK -> R.string.currency_name_dkk
        Currency.EUR -> R.string.currency_name_eur
        Currency.GBP -> R.string.currency_name_gbp
        Currency.HKD -> R.string.currency_name_hkd
        Currency.HRK -> R.string.currency_name_hrk
        Currency.HUF -> R.string.currency_name_huf
        Currency.IDR -> R.string.currency_name_idr
        Currency.ILS -> R.string.currency_name_ils
        Currency.INR -> R.string.currency_name_inr
        Currency.ISK -> R.string.currency_name_isk
        Currency.JPY -> R.string.currency_name_jpy
        Currency.KRW -> R.string.currency_name_krw
        Currency.MXN -> R.string.currency_name_mxn
        Currency.MYR -> R.string.currency_name_myr
        Currency.NOK -> R.string.currency_name_nok
        Currency.NZD -> R.string.currency_name_nzd
        Currency.PHP -> R.string.currency_name_php
        Currency.PLN -> R.string.currency_name_pln
        Currency.RON -> R.string.currency_name_ron
        Currency.RUB -> R.string.currency_name_rub
        Currency.SEK -> R.string.currency_name_sek
        Currency.SGD -> R.string.currency_name_sgd
        Currency.THB -> R.string.currency_name_thb
        Currency.TRY -> R.string.currency_name_try
        Currency.USD -> R.string.currency_name_usd
        Currency.ZAR -> R.string.currency_name_zar
        else -> throw IllegalStateException("No such currency")
      }

  private fun getCurrencyIcon(currency: Currency): Int =
      when (currency) {
        Currency.AUD -> R.drawable.ic_aud
        Currency.BGN -> R.drawable.ic_bgn
        Currency.BRL -> R.drawable.ic_brl
        Currency.CAD -> R.drawable.ic_cad
        Currency.CHF -> R.drawable.ic_chf
        Currency.CNY -> R.drawable.ic_cny
        Currency.CZK -> R.drawable.ic_czk
        Currency.DKK -> R.drawable.ic_dkk
        Currency.EUR -> R.drawable.ic_eur
        Currency.GBP -> R.drawable.ic_gbp
        Currency.HKD -> R.drawable.ic_hkd
        Currency.HRK -> R.drawable.ic_hrk
        Currency.HUF -> R.drawable.ic_huf
        Currency.IDR -> R.drawable.ic_idr
        Currency.ILS -> R.drawable.ic_ils
        Currency.INR -> R.drawable.ic_inr
        Currency.ISK -> R.drawable.ic_isk
        Currency.JPY -> R.drawable.ic_jpy
        Currency.KRW -> R.drawable.ic_krw
        Currency.MXN -> R.drawable.ic_mxn
        Currency.MYR -> R.drawable.ic_myr
        Currency.NOK -> R.drawable.ic_nok
        Currency.NZD -> R.drawable.ic_nzd
        Currency.PHP -> R.drawable.ic_php
        Currency.PLN -> R.drawable.ic_pln
        Currency.RON -> R.drawable.ic_ron
        Currency.RUB -> R.drawable.ic_rub
        Currency.SEK -> R.drawable.ic_sek
        Currency.SGD -> R.drawable.ic_sgd
        Currency.THB -> R.drawable.ic_thb
        Currency.TRY -> R.drawable.ic_try
        Currency.USD -> R.drawable.ic_usd
        Currency.ZAR -> R.drawable.ic_zar
        else -> throw IllegalStateException("No such currency")
      }
}
