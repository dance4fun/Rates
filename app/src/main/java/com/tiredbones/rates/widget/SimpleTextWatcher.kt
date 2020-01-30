package com.tiredbones.rates.widget

import android.text.Editable
import android.text.TextWatcher

interface SimpleTextWatcher : TextWatcher {
  override fun afterTextChanged(s: Editable?) {
    // Empty
  }

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    // Empty
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    // Empty
  }
}
