package com.tiredbones.rates.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

  protected val disposables = CompositeDisposable()
  val loading = MutableLiveData<Boolean>()
  val showKeyboard = MutableLiveData<Boolean>()

  override fun onCleared() {
    disposables.dispose()
  }
}
