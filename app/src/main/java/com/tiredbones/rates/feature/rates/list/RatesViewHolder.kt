package com.tiredbones.rates.feature.rates.list

import android.text.Editable
import androidx.recyclerview.widget.RecyclerView
import com.tiredbones.rates.databinding.ItemRateBinding
import com.tiredbones.rates.util.setText
import com.tiredbones.rates.widget.SimpleTextWatcher
import java.math.BigDecimal

class RatesViewHolder(
    private val binding: ItemRateBinding,
    private val onItemSelectedListener: (RateItem) -> Unit,
    private val onValueChangedListener: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

  init {
    binding.root.setOnClickListener {
      binding.rateValue.requestFocus()
    }
    binding.rateValue.setOnFocusChangeListener { _, hasFocus ->
      if (hasFocus && adapterPosition != 0) {
        binding.item?.let(onItemSelectedListener)
      }
    }
    binding.rateValue.addTextChangedListener(object : SimpleTextWatcher {
      override fun afterTextChanged(s: Editable?) {
        if(adapterPosition == 0) {
          onValueChangedListener(s.toString())
        }
      }
    })
  }

  fun bind(item: RateItem) {
    binding.item = item
  }

  fun updateRateValue(value: BigDecimal) {
    binding.rateValue.setText(value)
  }
}
