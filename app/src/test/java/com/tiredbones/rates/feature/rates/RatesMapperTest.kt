package com.tiredbones.rates.feature.rates

import com.tiredbones.rates.CurrencyRates
import com.tiredbones.rates.CurrencyRates.Currency
import com.tiredbones.rates.R
import com.tiredbones.rates.feature.rates.list.RateItem
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.math.BigDecimal

class RatesMapperTest {

  private val sut = RatesMapper()

  @Test
  fun `mapRatesToUiItems should map items properly`() {
    val baseCurrency = Currency.USD
    val baseCurrencyAmount = BigDecimal(100L)

    val firstRateItem = Currency.GBP to 2f
    val secondRateItem = Currency.RUB to 10f
    val rates = mapOf(firstRateItem, secondRateItem)
    val input = CurrencyRates(
        base = baseCurrency,
        rates = rates
    )

    val expectedResult = listOf(
        RateItem(
            id = baseCurrency,
            rate = 1f,
            amount = baseCurrencyAmount,
            name = R.string.currency_name_usd,
            imageRes = R.drawable.ic_usd
        ),
        RateItem(
            id = firstRateItem.first,
            rate = firstRateItem.second,
            amount = firstRateItem.second.toBigDecimal() * baseCurrencyAmount,
            name = R.string.currency_name_gbp,
            imageRes = R.drawable.ic_gbp
        ),
        RateItem(
            id = secondRateItem.first,
            rate = secondRateItem.second,
            amount = secondRateItem.second.toBigDecimal() * baseCurrencyAmount,
            name = R.string.currency_name_rub,
            imageRes = R.drawable.ic_rub
        )
    )
    val actualResult = sut.mapRatesToUiItems(input, baseCurrencyAmount)

    assertThat(actualResult, equalTo(expectedResult))
  }

  @Test
  fun `recalculateAmounts should change items' amounts`() {
    val baseCurrencyAmount = BigDecimal(100L)
    val inputList = listOf(
        RateItem(
            id = Currency.USD,
            rate = 1f,
            amount = baseCurrencyAmount,
            name = R.string.currency_name_usd,
            imageRes = R.drawable.ic_usd
        ),
        RateItem(
            id = Currency.RUB,
            rate = 2f,
            amount =   baseCurrencyAmount,
            name = R.string.currency_name_rub,
            imageRes = R.drawable.ic_rub
        ),
        RateItem(
            id = Currency.EUR,
            rate = 3f,
            amount =   baseCurrencyAmount,
            name = R.string.currency_name_eur,
            imageRes = R.drawable.ic_eur
        )
    )

    val expectedResult = inputList.map { it.copy(amount = it.rate.toBigDecimal() * baseCurrencyAmount) }
    val actualResult = sut.recalculateAmounts(inputList, baseCurrencyAmount)
    assertThat(actualResult, equalTo(expectedResult))
  }
}
