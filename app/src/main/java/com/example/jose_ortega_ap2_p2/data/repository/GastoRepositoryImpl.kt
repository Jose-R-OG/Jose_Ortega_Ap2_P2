package com.example.jose_ortega_ap2_p2.data.repository

import com.example.jose_ortega_ap2_p2.data.remote.Resource
import com.example.jose_ortega_ap2_p2.data.remote.remotedatasource.GastoRemoteDataSource
import com.example.jose_ortega_ap2_p2.data.remote.dto.GastoRequest
import com.example.jose_ortega_ap2_p2.domain.model.Gasto
import com.example.jose_ortega_ap2_p2.domain.repository.GastoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.onSuccess

class GastoRepositoryImpl @Inject constructor(
    private val remoteDataSource: GastoRemoteDataSource
) : GastoRepository {

    override fun getGastos(): Flow<Resource<List<Gasto>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getGastos()
        response.onSuccess { gastosResponse ->
            val gastosDominio = gastosResponse.map { it.toDomain() }
            emit(Resource.Success(gastosDominio))
        }.onFailure { exception ->
            emit(Resource.Error(exception.message ?: "Error al obtener gastos"))
        }
    }

    override fun getGastoDetail(id: Int): Flow<Resource<Gasto>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getGastoDetail(id)
        response.onSuccess { gastoDto ->
            emit(Resource.Success(gastoDto.toDomain()))
        }.onFailure { exception ->
            emit(Resource.Error(exception.message ?: "Error al obtener el detalle"))
        }
    }

    override fun saveGasto(gasto: GastoRequest): Flow<Resource<Gasto>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.createGasto(gasto)
        response.onSuccess { result ->
            emit(Resource.Success(result.toDomain()))
        }.onFailure { exception ->
            emit(Resource.Error(exception.message ?: "Error al registrar el gasto"))
        }
    }

    override fun putGasto(id: Int, gasto: GastoRequest): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.updateGasto(id, gasto)
        response.onSuccess {
            emit(Resource.Success(Unit))
        }.onFailure { exception ->
            emit(Resource.Error(exception.message ?: "Error al modificar el gasto"))
        }
    }
}