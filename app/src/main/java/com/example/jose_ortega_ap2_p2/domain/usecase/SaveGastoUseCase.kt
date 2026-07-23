package com.example.jose_ortega_ap2_p2.domain.usecase

import com.example.jose_ortega_ap2_p2.data.remote.Resource
import com.example.jose_ortega_ap2_p2.data.remote.dto.GastoRequest
import com.example.jose_ortega_ap2_p2.domain.model.Gasto
import com.example.jose_ortega_ap2_p2.domain.repository.GastoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveGastoUseCase @Inject constructor(
    private val repository: GastoRepository
) {
    operator fun invoke(gastoId: Int, fecha: String, suplidor: String, ncf: String, itbis: Double, monto: Double): Flow<Resource<Any>> {
        val request = GastoRequest(fecha = fecha, suplidor = suplidor, ncf = ncf, itbis = itbis, monto = monto)
        return if (gastoId == 0) {
            repository.saveGasto(request) as Flow<Resource<Any>>
        } else {
            repository.putGasto(gastoId, request) as Flow<Resource<Any>>
        }
    }
}