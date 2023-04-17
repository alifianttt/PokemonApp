package com.submission.pokemonapp.core.domain.usecase

import com.submission.pokemonapp.core.data.Resource
import com.submission.pokemonapp.core.data.source.remote.response.PokemonDetail
import com.submission.pokemonapp.core.domain.model.Pokemon
import com.submission.pokemonapp.core.domain.repository.IPokemonRepo
import kotlinx.coroutines.flow.Flow

class PokemonInteractor(private val pokemonRepo: IPokemonRepo) : PokemonUseCase {
    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> = pokemonRepo.getListPokemon()

    override fun getFavoritePokemon(): Flow<List<Pokemon>> = pokemonRepo.getFavoritePokemon()

    override fun savePokemonFavorite(pokemon: Pokemon, isSave: Boolean) = pokemonRepo.saveToFavorite(pokemon, isSave)

    override fun getByIdPokemon(id: Long): Flow<Resource<Pokemon>> = pokemonRepo.getPokemonById(id)

    override fun getFavoriteById(id: Long): Flow<Pokemon> = pokemonRepo.getFavoriteById(id)


}