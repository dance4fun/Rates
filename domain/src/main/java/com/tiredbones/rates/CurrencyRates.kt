package com.tiredbones.rates

data class CurrencyRates(
    val base: Currency,
    val rates: Map<Currency, Float>
) {

  enum class Currency(val code: String) {
    AUD("AUD"),
    BGN("BGN"),
    BRL("BRL"),
    CAD("CAD"),
    CHF("CHF"),
    CNY("CNY"),
    CZK("CZK"),
    DKK("DKK"),
    EUR("EUR"),
    GBP("GBP"),
    HKD("HKD"),
    HRK("HRK"),
    HUF("HUF"),
    IDR("IDR"),
    ILS("ILS"),
    INR("INR"),
    ISK("ISK"),
    JPY("JPY"),
    KRW("KRW"),
    MXN("MXN"),
    MYR("MYR"),
    NOK("NOK"),
    NZD("NZD"),
    PHP("PHP"),
    PLN("PLN"),
    RON("RON"),
    RUB("RUB"),
    SEK("SEK"),
    SGD("SGD"),
    THB("THB"),
    TRY("TRY"),
    USD("USD"),
    ZAR("ZAR"),
    UNKNOWN("");

    companion object {
      fun from(value: String): Currency = values().firstOrNull { it.code.equals(value, ignoreCase = true) } ?: UNKNOWN
    }
  }
}
