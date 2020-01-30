package com.tiredbones.rates.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.tiredbones.rates.R

@BindingMethods(
    BindingMethod(type = ErrorView::class, attribute = "onTryAgainClick", method = "setOnTryAgainClickListener"),
    BindingMethod(type = ErrorView::class, attribute = "errorMessage", method = "setErrorMessage")
)
class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

  private val errorMessage: TextView
  private val buttonTryAgain: Button

  init {
    View.inflate(context, R.layout.view_error, this)
    orientation = VERTICAL
    gravity = CENTER
    errorMessage = findViewById(R.id.error_message)
    buttonTryAgain = findViewById(R.id.button_try_again)
  }

  fun setOnTryAgainClickListener(action: () -> Unit) {
    buttonTryAgain.setOnClickListener { action() }
  }

  fun setErrorMessage(error: String?) {
    errorMessage.text = error
  }
}
