package com.submission.pokemonapp.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.GridLayoutManager
import com.submission.pokemonapp.core.domain.uiadapter.PokemonAdapter
import com.submission.pokemonapp.core.utils.setVisible
import com.submission.pokemonapp.favorite.databinding.FragmentFavoritePokemonBinding
import com.submission.pokemonapp.favorite.di.favoriteModule
import com.submission.pokemonapp.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoritePokemon : Fragment() {
    @delegate:Keep
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private  var _binding: FragmentFavoritePokemonBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritePokemonBinding.inflate(inflater, container, false)
        loadKoinModules(favoriteModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            val favoriteAdapter = PokemonAdapter()
            favoriteAdapter.onItemClick = { selectedData ->
                startActivity(DetailActivity.newIntent(requireContext(), selectedData.id.toLong(), true))
            }
            favoriteViewModel.favoritePokemon.observe(viewLifecycleOwner){ pokemon ->
                binding.noDataFavorite.setVisible(pokemon.isEmpty())
                favoriteAdapter.setData(pokemon)
            }

            with(binding.rvFavoritePokemon){
                adapter = favoriteAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                setHasFixedSize(true)
            }
        }
    }
}