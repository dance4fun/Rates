package com.tiredbones.rates

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.tiredbones.rates.CurrencyRates.Currency
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetRatesUseCaseTest {

  @Mock lateinit var ratesRepository: RatesRepository

  private lateinit var sut: GetRatesUseCase

  @Before
  fun setUp() {
    sut = GetRatesUseCase(ratesRepository)
  }

  @Test
  fun `invoke should returns currency rates list from repository`() {
    val currency = Currency.EUR
    val result = mock<CurrencyRates>()
    given(ratesRepository.getRates(currency.code)).willReturn(
        Single.just(result)
    )

    sut.invoke(currency).test().assertValue(result)
  }

  @Test
  fun `invoke should throw error if repository returns error`() {
    val currency = Currency.EUR
    val error = mock<Exception>()
    given(ratesRepository.getRates(currency.code)).willReturn(
        Single.error(error)
    )

    sut.invoke(currency).test().assertError(error)
  }
}
