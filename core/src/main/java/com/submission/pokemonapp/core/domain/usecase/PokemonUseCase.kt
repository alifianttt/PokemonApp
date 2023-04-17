package com.submission.pokemonapp.core.domain.usecase

import com.submission.pokemonapp.core.data.Resource
import com.submission.pokemonapp.core.data.source.remote.response.PokemonDetail
import com.submission.pokemonapp.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>
    fun getFavoritePokemon(): Flow<List<Pokemon>>
    fun savePokemonFavorite(pokemon: Pokemon, isSave: Boolean)
    fun getByIdPokemon(id: Long): Flow<Resource<Pokemon>>
    fun getFavoriteById(id: Long) : Flow<Pokemon>
}