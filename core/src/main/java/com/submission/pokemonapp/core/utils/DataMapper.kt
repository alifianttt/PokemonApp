package com.submission.pokemonapp.core.utils

import android.annotation.SuppressLint
import com.submission.pokemonapp.core.data.source.local.entity.PokemonEntitiy
import com.submission.pokemonapp.core.data.source.remote.response.PokemonDetail
import com.submission.pokemonapp.core.data.source.remote.response.PokemonResponse
import com.submission.pokemonapp.core.domain.model.Pokemon

object DataMapper {
    fun mapEntitisToDomain(inputData: List<PokemonEntitiy>): List<Pokemon> =
        inputData.map {
             Pokemon(
                id = it.id,
                name = it.name,
                url = it.url,
                isSave = it.isSave
            )
        }

    fun mapResponseToEntities(inputData: List<PokemonResponse>): List<PokemonEntitiy>{
        val pokemonList = ArrayList<PokemonEntitiy>()
        inputData.map {
            val pokemon = PokemonEntitiy(
                id = it.id,
                name = it.name,
                url = it.url,
                isSave = false
            )
            pokemonList.add(pokemon)
        }
        return pokemonList
    }

    fun mapDomainToEntity(inputData: Pokemon) = PokemonEntitiy(
        id = inputData.id,
        name = inputData.name,
        url = inputData.url,
        isSave = inputData.isSave
    )

    fun mapResponToDomain(inputData: PokemonDetail) = Pokemon(
        id = inputData.id,
        name = inputData.name,
        url = inputData.location_area_encounters,
        isSave = inputData.isSave
    )

    fun mapEntitiesToDomain(inputData: PokemonEntitiy) = Pokemon(
        id = inputData.id,
        name = inputData.name,
        url = inputData.url,
        isSave = inputData.isSave
    )

}