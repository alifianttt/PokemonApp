package com.submission.pokemonapp.core.data

import com.submission.pokemonapp.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class RemoteService<ResultType, RequestType> {
    private var result : Flow<Resource<ResultType>> = flow {
        emit(Resource.OnLoading())
        when(val apiResponse = createCall().first()){
            is ApiResponse.Succes -> {
                saveResult(apiResponse.data)
                emitAll(fetchResponse().map {
                    Resource.OnSucces(it)
                })
            }
            is ApiResponse.Empty -> {
                emitAll(fetchResponse().map {
                    Resource.OnSucces(it)
                })
            }
            is ApiResponse.Error -> {
                onFetchedFailed()
                emit(Resource.OnError(apiResponse.errMessage))
            }
        }
    }

    protected open fun onFetchedFailed() {}
    protected abstract fun saveResult(data: RequestType)
    protected abstract fun fetchResponse() : Flow<ResultType>
    protected abstract suspend fun createCall() : Flow<ApiResponse<RequestType>>
    fun asFlow(): Flow<Resource<ResultType>> = result
}