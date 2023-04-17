package com.submission.pokemonapp.core.domain.repository

import com.submission.pokemonapp.core.data.Resource
import com.submission.pokemonapp.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface IPokemonRepo {
    fun getListPokemon(): Flow<Resource<List<Pokemon>>>

    fun getFavoritePokemon(): Flow<List<Pokemon>>

    fun saveToFavorite(pokemon: Pokemon, isSave: Boolean)

    fun getPokemonById(id: Long): Flow<Resource<Pokemon>>

    fun getFavoriteById(id: Long) : Flow<Pokemon>
}