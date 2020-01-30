package com.tiredbones.rates.feature.rates

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tiredbones.rates.R
import com.tiredbones.rates.base.BaseActivity
import com.tiredbones.rates.databinding.ActivityRatesBinding
import com.tiredbones.rates.extensions.getViewModel
import com.tiredbones.rates.extensions.hideKeyboard
import com.tiredbones.rates.extensions.observe
import com.tiredbones.rates.feature.rates.list.RatesAdapter

class RatesActivity : BaseActivity() {

  private val viewModel: RatesViewModel by lazy {
    getViewModel<RatesViewModel>(viewModelFactory)
  }

  private val binding: ActivityRatesBinding by lazy {
    DataBindingUtil.setContentView<ActivityRatesBinding>(this, R.layout.activity_rates)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    val adapter = RatesAdapter(viewModel::onItemSelected, viewModel::onCurrencyAmountChanged)
    binding.ratesList.adapter = adapter
    binding.ratesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          hideKeyboard()
        }
      }
    })

    viewModel.bind(this)
    viewModel.rates.observe(this, adapter::updateList)
    viewModel.observedBy(this)
  }
}
