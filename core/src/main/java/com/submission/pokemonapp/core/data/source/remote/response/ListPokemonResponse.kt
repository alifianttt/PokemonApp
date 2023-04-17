package com.submission.pokemonapp.core.data.source.remote.response


data class ListPokemonResponse(
    val count: Int,
    val results: List<PokemonResponse>
)
