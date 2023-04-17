package com.submission.pokemonapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.pokemonapp.core.data.Resource
import com.submission.pokemonapp.core.domain.model.Pokemon
import com.submission.pokemonapp.core.domain.usecase.PokemonUseCase

class DetailViewModel(private val pokemonUseCase: PokemonUseCase): ViewModel(){
    fun setToFavorite(pokemon: Pokemon, state: Boolean) =
        pokemonUseCase.savePokemonFavorite(pokemon, state)

    fun getDetail(id: Long): LiveData<Resource<Pokemon>> {
        return pokemonUseCase.getByIdPokemon(id).asLiveData()
    }

    fun getDetailFavorite(id: Long) = pokemonUseCase.getFavoriteById(id).asLiveData()
}