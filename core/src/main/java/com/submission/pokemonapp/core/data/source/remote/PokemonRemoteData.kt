package com.submission.pokemonapp.core.data.source.remote

import com.submission.pokemonapp.core.data.Resource
import com.submission.pokemonapp.core.data.source.remote.network.ApiResponse
import com.submission.pokemonapp.core.data.source.remote.network.PokemonApiService
import com.submission.pokemonapp.core.data.source.remote.response.PokemonDetail
import com.submission.pokemonapp.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokemonRemoteData(val apiService: PokemonApiService) {
    suspend fun getAllPokemon() : Flow<ApiResponse<List<PokemonResponse>>>{
        return flow {
            try {
                val response = apiService.getListPokemon()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Succes(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: java.lang.Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPokemonBy(id: Long) : Flow<ApiResponse<PokemonDetail>> {
        return flow {
            try {
                val response = apiService.getPokemonById(id)
                if (response.name.isNotBlank()){
                    emit(ApiResponse.Succes(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}