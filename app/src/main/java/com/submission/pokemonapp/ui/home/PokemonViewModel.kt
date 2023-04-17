package com.submission.pokemonapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.submission.pokemonapp.core.data.Resource
import com.submission.pokemonapp.core.data.source.remote.PokemonRemoteData
import com.submission.pokemonapp.core.data.source.remote.network.ApiResponse
import com.submission.pokemonapp.core.data.source.remote.response.PokemonDetail
import com.submission.pokemonapp.core.domain.model.Pokemon
import com.submission.pokemonapp.core.domain.usecase.PokemonUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel(){

    val pokemon = pokemonUseCase.getAllPokemon().asLiveData()

}