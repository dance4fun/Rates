package com.tiredbones.rates.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tiredbones.rates.base.BaseViewModel

inline fun <reified T : BaseViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T =
    ViewModelProviders.of(this, viewModelFactory).get(T::class.java)

fun FragmentActivity.showKeyboard() {
  getInputMethodManager().toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun FragmentActivity.hideKeyboard() {
  getInputMethodManager().hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

private fun FragmentActivity.getInputMethodManager() =
    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
