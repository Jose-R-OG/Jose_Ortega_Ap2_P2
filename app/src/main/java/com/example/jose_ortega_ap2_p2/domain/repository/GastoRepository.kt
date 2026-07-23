package com.example.jose_ortega_ap2_p2.domain.repository

import com.example.jose_ortega_ap2_p2.data.remote.Resource
import com.example.jose_ortega_ap2_p2.domain.model.Gasto
import com.example.jose_ortega_ap2_p2.data.remote.dto.GastoRequest
import kotlinx.coroutines.flow.Flow

interface GastoRepository {
    fun getGastos(): Flow<Resource<List<Gasto>>>
    fun getGastoDetail(id: Int): Flow<Resource<Gasto>>
    fun saveGasto(gasto: GastoRequest): Flow<Resource<Gasto>>
    fun putGasto(id: Int, gasto: GastoRequest): Flow<Resource<Unit>>
}