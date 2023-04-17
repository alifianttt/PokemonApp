package com.submission.pokemonapp.core.data

sealed class Resource<T>(val data: T? = null, val message: String? = null){
    class OnSucces<T>(data: T): Resource<T>(data)
    class OnLoading<T>(data: T? = null) : Resource<T>(data)
    class OnError<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
