package com.tiredbones.rates.feature.rates.list

import androidx.recyclerview.widget.DiffUtil

class RatesAsyncDiffUtil : DiffUtil.ItemCallback<RateItem>() {

  companion object Payload {
    const val RATE_CHANGED = 1
  }

  override fun areItemsTheSame(oldItem: RateItem, newItem: RateItem): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: RateItem, newItem: RateItem): Boolean = oldItem == newItem

  override fun getChangePayload(oldItem: RateItem, newItem: RateItem): Any? =
      when {
        oldItem.id == newItem.id && oldItem.amount != newItem.amount -> RATE_CHANGED
        else -> null
      }
}
