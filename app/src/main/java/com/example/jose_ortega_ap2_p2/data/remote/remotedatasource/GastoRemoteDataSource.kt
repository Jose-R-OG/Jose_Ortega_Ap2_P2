package com.example.jose_ortega_ap2_p2.data.remote.remotedatasource

import com.example.jose_ortega_ap2_p2.data.remote.GastoApi
import com.example.jose_ortega_ap2_p2.data.remote.dto.GastoRequest
import com.example.jose_ortega_ap2_p2.data.remote.dto.GastoResponse
import retrofit2.HttpException
import javax.inject.Inject

class GastoRemoteDataSource @Inject constructor(
    private val api: GastoApi
) {
    suspend fun getGastos(): Result<List<GastoResponse>> {
        return try {
            val response = api.getGastos()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de red ${response.code()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun getGastoDetail(id: Int): Result<GastoResponse> {
        return try {
            val response = api.getGastoDetail(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de red ${response.code()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun createGasto(gasto: GastoRequest): Result<GastoResponse> {
        return try {
            val response = api.saveGasto(gasto)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error de red al crear ${response.code()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun updateGasto(id: Int, gasto: GastoRequest): Result<Unit> {
        return try {
            val response = api.putGasto(id, gasto)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error de red al actualizar ${response.code()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido", e))
        }
    }
}

