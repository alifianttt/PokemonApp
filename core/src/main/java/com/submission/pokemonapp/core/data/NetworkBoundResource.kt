package com.submission.pokemonapp.core.data

import com.submission.pokemonapp.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>{
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.OnLoading())
        when(val apiResponse = createCall().first()){
            is ApiResponse.Succes -> {
                saveCallResult(apiResponse.data)
                emitAll(loadFromDB().map {
                    Resource.OnSucces(it)
                })
            }
            is ApiResponse.Empty -> {
                emitAll(loadFromDB().map {
                    Resource.OnSucces(it)
                })
            }
            is ApiResponse.Error -> {
                onFetchedFailed()
                emit(
                    Resource.OnError(apiResponse.errMessage)
                )
            }
        }
    }

    protected open fun onFetchedFailed() {}
    protected abstract fun loadFromDB(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType) : Boolean
    protected abstract suspend fun createCall() : Flow<ApiResponse<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}