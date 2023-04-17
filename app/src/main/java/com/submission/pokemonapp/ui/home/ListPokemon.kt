package com.submission.pokemonapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.submission.pokemonapp.core.data.Resource
import com.submission.pokemonapp.core.domain.uiadapter.PokemonAdapter
import com.submission.pokemonapp.core.utils.setVisible
import com.submission.pokemonapp.core.utils.toasShort
import com.submission.pokemonapp.databinding.FragmentListPokemonBinding
import com.submission.pokemonapp.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class ListPokemon : Fragment() {

    private val pokemonViewModel: PokemonViewModel by viewModel()
    private var _binding: FragmentListPokemonBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){

            val pokemonAdapter = PokemonAdapter()
            pokemonAdapter.onItemClick = { selectedItem ->
                requireActivity().startActivity(DetailActivity.newIntent(requireContext(), selectedItem.id.toLong()))
            }

            pokemonViewModel.pokemon.observe(viewLifecycleOwner){ pokemon ->
                if (pokemon != null){
                    when(pokemon){
                        is Resource.OnLoading -> binding.progressView.setVisible(true)
                        is Resource.OnSucces -> {
                            pokemonAdapter.setData(pokemon.data)
                            binding.progressView.setVisible(false)
                        }
                        is Resource.OnError -> {
                            binding.progressView.setVisible(false)
                            requireActivity().toasShort(pokemon.message ?: "")
                        }
                    }
                }
            }

            with(binding.rvPokemon){
                adapter = pokemonAdapter
                layoutManager = GridLayoutManager(requireActivity(), 2)
                setHasFixedSize(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}