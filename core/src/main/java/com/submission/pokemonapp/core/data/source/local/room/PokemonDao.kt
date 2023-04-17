package com.submission.pokemonapp.core.data.source.local.room

import androidx.room.*
import com.submission.pokemonapp.core.data.source.local.entity.PokemonEntitiy
import com.submission.pokemonapp.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAllPokemonLocal() : Flow<List<PokemonEntitiy>>

    @Query("SELECT * FROM pokemon where isSave = 1")
    fun getAllPokemonFavorite() : Flow<List<PokemonEntitiy>>

    @Update
    fun insertPokemon(pokemon: PokemonEntitiy)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon(pokemonList: List<PokemonEntitiy>)

    @Query("SELECT * FROM pokemon where id = :id")
    fun getPokemonId(id: Long) : Flow<PokemonEntitiy>
}