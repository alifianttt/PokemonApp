package com.submission.pokemonapp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.submission.pokemonapp.core.data.source.local.entity.PokemonEntitiy

@Database(entities = [PokemonEntitiy::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}