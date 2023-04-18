package com.submission.pokemonapp.core.domain.uiadapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.pokemonapp.core.R
import com.submission.pokemonapp.core.databinding.ItemPokemonBinding
import com.submission.pokemonapp.core.domain.model.Pokemon
import com.submission.pokemonapp.core.utils.Constant
import com.submission.pokemonapp.core.utils.capitalizeFirstLetter
import com.submission.pokemonapp.core.utils.extractColorFromUrl

class PokemonAdapter: RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var listPokemon = ArrayList<Pokemon>()
    var onItemClick: ((Pokemon) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListPokemon: List<Pokemon>?){
        if (newListPokemon == null) return
        listPokemon.clear()
        listPokemon.addAll(newListPokemon)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false))

    override fun getItemCount(): Int = listPokemon.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val data = listPokemon[position]
        holder.bind(data)
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPokemonBinding.bind(itemView)
        fun bind(pokemon: Pokemon){
            val imageUrl = Constant.URL_IMAGE + "${pokemon.id.toString().padStart(3, '0')}.png"

            with(binding){
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .into(imagePokemon)
                extractColorFromUrl(itemView.context, imageUrl){ color ->
                    if (color != null)
                        container.setCardBackgroundColor(color)
                }
                pokemonName.text = capitalizeFirstLetter(pokemon.name)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listPokemon[adapterPosition])
            }
        }
    }
}