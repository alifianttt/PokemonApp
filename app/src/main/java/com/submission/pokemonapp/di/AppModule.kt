package com.submission.pokemonapp.di

import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.submission.pokemonapp.core.domain.usecase.PokemonInteractor
import com.submission.pokemonapp.core.domain.usecase.PokemonUseCase
import com.submission.pokemonapp.ui.detail.DetailViewModel
import com.submission.pokemonapp.ui.home.PokemonViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<PokemonUseCase> { PokemonInteractor(get()) }
}

val viewModelModule = module {
    viewModel { PokemonViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}


val splitManagerModule = module {
    single { SplitInstallManagerFactory.create(get()) }
}