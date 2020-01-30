package com.tiredbones.rates.feature.rates.list

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tiredbones.rates.CurrencyRates.Currency
import java.math.BigDecimal

data class RateItem(
    val id: Currency,
    val rate: Float,
    val amount: BigDecimal,
    @StringRes val name: Int,
    @DrawableRes val imageRes: Int
)
