package com.tiredbones.rates.feature.rates.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.tiredbones.rates.R
import com.tiredbones.rates.feature.rates.list.RatesAsyncDiffUtil.Payload.RATE_CHANGED

class RatesAdapter(
    private val onItemClickListener: (RateItem) -> Unit,
    private val onValueChangedListener: (String) -> Unit
) : RecyclerView.Adapter<RatesViewHolder>() {

  private val differ = AsyncListDiffer(this, RatesAsyncDiffUtil())

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder =
      RatesViewHolder(
          DataBindingUtil.inflate(
              LayoutInflater.from(parent.context),
              R.layout.item_rate,
              parent,
              false
          ),
          onItemClickListener,
          onValueChangedListener
      )

  override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
    holder.bind(differ.currentList[position])
  }

  override fun onBindViewHolder(holder: RatesViewHolder, position: Int, payloads: MutableList<Any>) {
    when {
      payloads.contains(RATE_CHANGED) && position > 0 -> holder.updateRateValue(differ.currentList[position].amount)
      payloads.contains(RATE_CHANGED) && position == 0 -> { /* Don't make updates to not lose the focus */ }
      else -> super.onBindViewHolder(holder, position, payloads)
    }
  }

  override fun getItemCount() = differ.currentList.size

  fun updateList(newList: List<RateItem>) {
    differ.submitList(newList)
  }
}
