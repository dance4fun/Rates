package com.tiredbones.rates.feature.rates

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.tiredbones.logger.Logger
import com.tiredbones.rates.CurrencyRates.Currency
import com.tiredbones.rates.GetRatesUseCase
import com.tiredbones.rates.base.BaseViewModel
import com.tiredbones.rates.extensions.TAG
import com.tiredbones.rates.extensions.addTo
import com.tiredbones.rates.extensions.doOnFirst
import com.tiredbones.rates.feature.rates.list.RateItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase,
    private val ratesMapper: RatesMapper
) : BaseViewModel(), LifecycleObserver {

  val rates = MutableLiveData<List<RateItem>>()
  val showError = MutableLiveData<Boolean>()
  val showContent = MutableLiveData<Boolean>()

  private var baseCurrency = Currency.EUR
  private var baseCurrencyAmount = BigDecimal(1L)

  private var updateRatesDisposable: Disposable? = null
  private val currencyAmountChangedObservable = PublishSubject.create<Pair<Currency, String>>()

  init {
    currencyAmountChangedObservable
        .subscribeOn(Schedulers.computation())
        .skip(1)
        .distinctUntilChanged()
        .doOnNext { stopUpdating() }
        .map { (_, amount) ->
          amount.takeIf { it.isNotEmpty() }?.toBigDecimalOrNull() ?: EMPTY_AMOUNT
        }
        .onErrorReturn { EMPTY_AMOUNT }
        .doOnNext {
          baseCurrencyAmount = it
          rates.postValue(ratesMapper.recalculateAmounts(rates.value!!, baseCurrencyAmount))
        }
        .debounce(UPDATE_RATE_SEC, TimeUnit.SECONDS)
        .subscribe { updateRates() }
        .addTo(disposables)
  }

  fun bind(lifecycleOwner: LifecycleOwner) {
    loading.postValue(true)
    lifecycleOwner.lifecycle.addObserver(this)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun updateRates() {
    getRatesUseCase(baseCurrency).toObservable()
        .repeatWhen { it.delay(UPDATE_RATE_SEC, TimeUnit.SECONDS) }
        .doOnFirst { showContent() }
        .map { ratesMapper.mapRatesToUiItems(it, baseCurrencyAmount) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ result ->
          rates.value = result
        }, {
          showError()
          Logger.e(TAG, it, "Error retrieving the data")
        })
        .addTo(disposables)
        .also {
          updateRatesDisposable?.dispose()
          updateRatesDisposable = it
        }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun stopUpdating() {
    updateRatesDisposable?.dispose()
  }

  fun onRetryClicked() {
    showError.value = false
    loading.value = true
    updateRates()
  }

  fun onItemSelected(selectedItem: RateItem) {
    stopUpdating()
    baseCurrency = selectedItem.id
    baseCurrencyAmount = rates.value?.find { it.id == selectedItem.id }?.amount ?: EMPTY_AMOUNT
    updateRates()
  }

  fun onCurrencyAmountChanged(value: String) {
    currencyAmountChangedObservable.onNext(baseCurrency to value)
  }

  private fun showContent() {
    showError.postValue(false)
    showContent.postValue(true)
    loading.postValue(false)
  }

  private fun showError() {
    showKeyboard.postValue(false)
    showError.postValue(true)
    showContent.postValue(false)
    loading.postValue(false)
  }

  private companion object {
    const val UPDATE_RATE_SEC = 1L
    val EMPTY_AMOUNT = BigDecimal(0L)
  }
}
