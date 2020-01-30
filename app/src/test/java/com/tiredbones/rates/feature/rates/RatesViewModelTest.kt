package com.tiredbones.rates.feature.rates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.tiredbones.rates.CurrencyRates
import com.tiredbones.rates.GetRatesUseCase
import com.tiredbones.rates.feature.rates.list.RateItem
import com.tiredbones.rates.CurrencyRates.Currency
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import util.RxTestRule

@RunWith(MockitoJUnitRunner::class)
class RatesViewModelTest {

  private val scheduler = TestScheduler()
  @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
  @get:Rule val rxTestRule = RxTestRule(scheduler)

  private val getRatesUseCase: GetRatesUseCase = mock()
  private val ratesMapper: RatesMapper = mock()
  private lateinit var sut: RatesViewModel

  @Before
  fun init() {
    sut = RatesViewModel(getRatesUseCase, ratesMapper)
  }

  @Test
  fun `updateRates should set rates list from getRatesUseCase`() {
    val currencyRates = mock<CurrencyRates>()
    given { getRatesUseCase(any()) }.willReturn(Single.just(currencyRates))

    val resultList = mock<List<RateItem>>()
    given { ratesMapper.mapRatesToUiItems(eq(currencyRates), any()) }.willReturn(resultList)

    sut.updateRates()
    scheduler.triggerActions()
    assertThat(sut.rates.value, equalTo(resultList))
  }

  @Test
  fun `updateRates should show content upon subscription`() {
    val currencyRates = mock<CurrencyRates>()
    given { getRatesUseCase(any()) }.willReturn(Single.just(currencyRates))

    val resultList = mock<List<RateItem>>()
    given { ratesMapper.mapRatesToUiItems(eq(currencyRates), any()) }.willReturn(resultList)

    sut.updateRates()
    scheduler.triggerActions()
    assertThat(sut.showContent.value, equalTo(true))
  }

  @Test
  fun `updateRates should hide content and show error if getRatesUseCase returns error`() {
    val error = mock<Exception>()
    given { getRatesUseCase(any()) }.willReturn(Single.error(error))

    sut.updateRates()
    scheduler.triggerActions()
    assertThat(sut.showContent.value, equalTo(false))
    assertThat(sut.showError.value, equalTo(true))
  }

  @Test
  fun `updateRates should hide keyboard if getRatesUseCase returns error`() {
    val error = mock<Exception>()
    given { getRatesUseCase(any()) }.willReturn(Single.error(error))

    sut.updateRates()
    scheduler.triggerActions()
    assertThat(sut.showKeyboard.value, equalTo(false))
  }

  @Test
  fun `onRetryClicked should hide error and show loading`() {
    val currencyRates = mock<CurrencyRates>()
    given { getRatesUseCase(any()) }.willReturn(Single.just(currencyRates))

    sut.onRetryClicked()
    assertThat(sut.showError.value, equalTo(false))
    assertThat(sut.loading.value, equalTo(true))
  }

  @Test
  fun `onRetryClicked should call getRatesUseCase`() {
    val currencyRates = mock<CurrencyRates>()
    given { getRatesUseCase(any()) }.willReturn(Single.just(currencyRates))

    sut.onRetryClicked()
    verify(getRatesUseCase).invoke(any())
  }

  @Test
  fun `onItemSelected should call getRatesUseCase with selected currency`() {
    val currency = Currency.GBP
    val selectedItem = mock<RateItem> {
      on { id } doReturn currency
    }
    val currencyRates = mock<CurrencyRates>()
    given { getRatesUseCase(currency) }.willReturn(Single.just(currencyRates))

    sut.onItemSelected(selectedItem)
    verify(getRatesUseCase).invoke(currency)
  }

  @Test
  fun `onCurrencyAmountChanged should call recalculateAmounts with selected amount`() {
    val rates = mock<List<RateItem>>()
    val inputAmount = "10"
    sut.rates.value = rates

    val currencyAmount = inputAmount.toBigDecimal()
    given { ratesMapper.recalculateAmounts(rates, currencyAmount) }.willReturn(rates)

    scheduler.triggerActions()
    sut.onCurrencyAmountChanged("1") // skip init value from textwatcher
    sut.onCurrencyAmountChanged(inputAmount)
    verify(ratesMapper).recalculateAmounts(rates, currencyAmount)
  }

  @Test
  fun `onCurrencyAmountChanged should update rates list`() {
    val ratesBefore = mock<List<RateItem>>()
    val ratesAfter = mock<List<RateItem>>()
    val inputAmount = "10"
    sut.rates.value = ratesBefore

    val currencyAmount = inputAmount.toBigDecimal()
    given { ratesMapper.recalculateAmounts(ratesBefore, currencyAmount) }.willReturn(ratesAfter)

    scheduler.triggerActions()
    sut.onCurrencyAmountChanged("1") // skip init value from textwatcher
    sut.onCurrencyAmountChanged(inputAmount)
    assertThat(sut.rates.value, equalTo(ratesAfter))
  }
}
