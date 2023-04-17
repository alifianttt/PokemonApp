package com.submission.pokemonapp.core.data.source.remote.network

sealed class ApiResponse<out R> {
    data class Succes<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errMessage: String) : ApiResponse<Nothing>()
    object Empty: ApiResponse<Nothing>()
}