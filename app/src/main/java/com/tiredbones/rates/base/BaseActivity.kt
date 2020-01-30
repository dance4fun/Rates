package com.tiredbones.rates.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tiredbones.rates.R
import com.tiredbones.rates.RatesApplication
import com.tiredbones.rates.extensions.hideKeyboard
import com.tiredbones.rates.extensions.observe
import com.tiredbones.rates.extensions.showKeyboard
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

  protected open fun hasInjections() = true

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private val loadingDialog: AlertDialog by lazy {
    AlertDialog.Builder(this, R.style.DialogTransparent)
        .setView(R.layout.dialog_progress)
        .setCancelable(false)
        .create()
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    if (hasInjections()) {
      RatesApplication.applicationComponent.inject(this)
    }
    super.onCreate(savedInstanceState)
  }

  fun BaseViewModel.observedBy(activity: BaseActivity) {
    activity.observeViewModel(this)
  }

  private fun observeViewModel(viewModel: BaseViewModel) {
    viewModel.loading.observe(this, ::setLoadingVisibility)
    viewModel.showKeyboard.observe(this) { show ->
      if (show) {
        showKeyboard()
      } else {
        hideKeyboard()
      }
    }
  }

  private fun setLoadingVisibility(isVisible: Boolean) {
    if (isVisible) {
      loadingDialog.show()
    } else {
      loadingDialog.dismiss()
    }
  }
}
