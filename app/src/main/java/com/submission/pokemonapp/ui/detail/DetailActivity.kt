package com.submission.pokemonapp.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.submission.pokemonapp.R
import com.submission.pokemonapp.core.data.Resource
import com.submission.pokemonapp.core.domain.model.Pokemon
import com.submission.pokemonapp.core.utils.*
import com.submission.pokemonapp.databinding.ActivityDetailBinding
import com.submission.pokemonapp.ui.home.PokemonViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val pokemonViewModel: DetailViewModel by viewModel()
    private var idPokemon: Long = 0L
    private var isFromFavorite = false
    private var buttonState = false
    companion object{
        private const val ID_POKEMON = "id"
        private const val MENU = "menu"
        fun newIntent(context: Context, id: Long, isFromFavorite: Boolean = false) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(ID_POKEMON, id)
                putExtra(MENU, isFromFavorite)
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        idPokemon = intent.getLongExtra(ID_POKEMON, 0L)
        isFromFavorite = intent.getBooleanExtra(MENU, false)
        setContentView(binding.root)
        initView()
    }

    @SuppressLint("ResourceType")
    private fun initView(){
        var pokemonData : Pokemon? = null
        if (isFromFavorite){
            pokemonViewModel.getDetailFavorite(idPokemon).observe(this){ pokemonFavorite ->
                pokemonData = pokemonFavorite
                setDetailView(pokemonData)
            }
        } else {
            pokemonViewModel.getDetail(idPokemon).observe(this){ pokemonDetail ->
                if (pokemonDetail != null){
                    when(pokemonDetail){
                        is Resource.OnLoading -> binding.progressView.setVisible(true)
                        is Resource.OnSucces -> {
                            binding.progressView.setVisible(false)
                            pokemonData = pokemonDetail.data
                            setDetailView(pokemonData)
                        }
                        is Resource.OnError -> {
                            binding.progressView.setVisible(false)
                            toasShort(pokemonDetail.message ?: "")
                        }
                    }
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            if (pokemonData != null){
                buttonState = !buttonState
                setButtonFav(buttonState)
                pokemonViewModel.setToFavorite(pokemonData!!, buttonState)
            }
        }
    }

    private fun setButtonFav(state: Boolean){
       binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(this, if (state) R.drawable.favorite else R.drawable.favorite_border))
    }

    private fun setDetailView(pokemonDetail: Pokemon?){
        val imageUrl = Constant.URL_IMAGE + "${pokemonDetail?.id.toString().padStart(3, '0')}.png"
        extractColorFromUrl(this, imageUrl){ color ->
            if (color != null){
                binding.imageView.setBackgroundColor(color)
            }
        }
        Glide.with(this).load(imageUrl).into(binding.imageView)
        pokemonDetail?.let {
            buttonState = it.isSave
            setButtonFav(buttonState)
            binding.namePokemon.text = capitalizeFirstLetter(it.name)
        }
    }
}