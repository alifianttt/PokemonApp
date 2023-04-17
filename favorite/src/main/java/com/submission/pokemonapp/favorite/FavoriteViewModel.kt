package com.submission.pokemonapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.pokemonapp.core.domain.usecase.PokemonUseCase

class FavoriteViewModel(pokemonUseCase: PokemonUseCase): ViewModel() {
    val favoritePokemon = pokemonUseCase.getFavoritePokemon().asLiveData()
}