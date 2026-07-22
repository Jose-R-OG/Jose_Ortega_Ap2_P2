package com.example.jose_ortega_ap2_p2.data.remote

import com.example.jose_ortega_ap2_p2.data.remote.dto.GastoRequest
import com.example.jose_ortega_ap2_p2.data.remote.dto.GastoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GastoApi {
    @GET("api/Gastos")
    suspend fun getGastos(): Response<List<GastoResponse>>

    @GET("api/Gastos/{id}")
    suspend fun getGastoDetail(
        @Path("id") id: Int
    ): Response<GastoResponse>

    @POST("api/Gastos")
    suspend fun saveGasto(
        @Body gasto: GastoRequest
    ): Response<GastoResponse>

    @PUT("api/Gastos/{id}")
    suspend fun putGasto(
        @Path("id") id: Int,
        @Body gasto: GastoRequest
    ): Response<Unit>
}