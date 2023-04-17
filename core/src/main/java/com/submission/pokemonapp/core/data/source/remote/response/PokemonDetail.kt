package com.submission.pokemonapp.core.data.source.remote.response

data class PokemonDetail(
    val id: Int,
    val name: String,
    val location_area_encounters: String,
    val isSave: Boolean
)
