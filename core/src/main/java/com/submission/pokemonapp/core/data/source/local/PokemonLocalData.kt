package com.submission.pokemonapp.core.data.source.local

import com.submission.pokemonapp.core.data.source.local.entity.PokemonEntitiy
import com.submission.pokemonapp.core.data.source.local.room.PokemonDao
import kotlinx.coroutines.flow.Flow

class PokemonLocalData(private val pokemonDao: PokemonDao) {
    fun getAllPokemonLocal() : Flow<List<PokemonEntitiy>> = pokemonDao.getAllPokemonLocal()

    fun getListPokemonFavorite() : Flow<List<PokemonEntitiy>> = pokemonDao.getAllPokemonFavorite()

    fun insetFavorite(pokemonEntitiy: PokemonEntitiy, state: Boolean) {
        pokemonEntitiy.isSave = state
        pokemonDao.insertPokemon(pokemonEntitiy)
    }

    suspend fun insertAllPokemon(pokemonList: List<PokemonEntitiy>) = pokemonDao.insertAllPokemon(pokemonList)

    fun getPokemonFavorite(id: Long) = pokemonDao.getPokemonId(id)
}