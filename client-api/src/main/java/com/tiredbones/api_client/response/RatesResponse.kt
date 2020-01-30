package com.tiredbones.api_client.response

import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: Map<String, Float>
)
