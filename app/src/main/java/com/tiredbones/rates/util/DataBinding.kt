package com.tiredbones.rates.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.bumptech.glide.Glide
import java.math.BigDecimal

@BindingAdapter("image")
fun ImageView.setImage(@DrawableRes resId: Int) {
  Glide.with(this)
      .load(resId)
      .into(this)
}

@BindingAdapter("text")
fun TextView.setText(value: BigDecimal) {
  text = value.stripTrailingZeros().toPlainString()
}

@BindingConversion
fun setVisibility(isVisible: Boolean): Int = if (isVisible) View.VISIBLE else View.GONE
