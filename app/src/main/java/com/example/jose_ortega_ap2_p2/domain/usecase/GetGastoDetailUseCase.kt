package com.example.jose_ortega_ap2_p2.domain.usecase

import com.example.jose_ortega_ap2_p2.data.remote.Resource
import com.example.jose_ortega_ap2_p2.domain.model.Gasto
import com.example.jose_ortega_ap2_p2.domain.repository.GastoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGastoDetailUseCase @Inject constructor(
    private val repository: GastoRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Gasto>> {
        return repository.getGastoDetail(id)
    }
}