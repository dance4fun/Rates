package com.tiredbones.api_client

import com.tiredbones.api_client.response.RatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

  @GET("latest")
  fun getRates(@Query("base") baseCurrency: String): Single<RatesResponse>
}
