package com.submission.pokemonapp.core.data.source.remote.network

import com.submission.pokemonapp.core.data.source.remote.response.ListPokemonResponse
import com.submission.pokemonapp.core.data.source.remote.response.PokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getListPokemon(): ListPokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Long) : PokemonDetail
}