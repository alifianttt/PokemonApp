package com.submission.pokemonapp.core.data

import android.util.Log
import com.submission.pokemonapp.core.data.source.local.PokemonLocalData
import com.submission.pokemonapp.core.data.source.local.entity.PokemonEntitiy
import com.submission.pokemonapp.core.data.source.remote.PokemonRemoteData
import com.submission.pokemonapp.core.data.source.remote.network.ApiResponse
import com.submission.pokemonapp.core.data.source.remote.response.PokemonDetail
import com.submission.pokemonapp.core.data.source.remote.response.PokemonResponse
import com.submission.pokemonapp.core.domain.model.Pokemon
import com.submission.pokemonapp.core.domain.repository.IPokemonRepo
import com.submission.pokemonapp.core.utils.AppExecutors
import com.submission.pokemonapp.core.utils.DataMapper
import kotlinx.coroutines.flow.*

class PokemonRepository(
    private val remoteDataSource: PokemonRemoteData,
    private val localDataSource: PokemonLocalData,
    private val appExecutors: AppExecutors
) : IPokemonRepo {
    private var detailPokemon = flow<Pokemon> {  }
    private val listFavorite = ArrayList<PokemonEntitiy>()
    override fun getListPokemon(): Flow<Resource<List<Pokemon>>> =
        object : NetworkBoundResource<List<Pokemon>, List<PokemonResponse>>() {
            override fun loadFromDB(): Flow<List<Pokemon>> {
                return localDataSource.getAllPokemonLocal().map {
                    DataMapper.mapEntitisToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<PokemonResponse>>> = remoteDataSource.getAllPokemon()

            override suspend fun saveCallResult(data: List<PokemonResponse>) {
                val pokemonList = DataMapper.mapResponseToEntities(data).mapIndexed { index, pokemonEntitiy ->
                    pokemonEntitiy.copy(id = pokemonEntitiy.id + (index +1))
                }
                listFavorite.map { favorite ->
                    pokemonList.map { newPokemon->
                        if (newPokemon.id == favorite.id)
                            newPokemon.isSave = favorite.isSave
                    }
                }

                localDataSource.insertAllPokemon(pokemonList)

            }

            override fun shouldFetch(data: List<Pokemon>): Boolean = true

        }.asFlow()

    override fun getFavoritePokemon(): Flow<List<Pokemon>> {
        return localDataSource.getListPokemonFavorite().map {
            DataMapper.mapEntitisToDomain(it)
        }
    }

    override fun saveToFavorite(pokemon: Pokemon, isSave: Boolean) {
        val pokemonEntity = DataMapper.mapDomainToEntity(pokemon)
        if (isSave){
            listFavorite.add(pokemonEntity)
        } else {
            listFavorite.remove(pokemonEntity)
        }
        appExecutors.diskIO().execute { localDataSource.insetFavorite(pokemonEntity, isSave) }
    }

    override fun getPokemonById(id: Long): Flow<Resource<Pokemon>>  =
        object : RemoteService<Pokemon, PokemonDetail>(){

            override fun fetchResponse(): Flow<Pokemon> {

                return detailPokemon
            }

            override suspend fun createCall(): Flow<ApiResponse<PokemonDetail>> = remoteDataSource.getPokemonBy(id)

            override fun saveResult(data: PokemonDetail) {
                detailPokemon = flow {
                    emit(DataMapper.mapResponToDomain(data))
                }
            }
        }.asFlow()

    override fun getFavoriteById(id: Long): Flow<Pokemon> {
        return localDataSource.getPokemonFavorite(id).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }
}