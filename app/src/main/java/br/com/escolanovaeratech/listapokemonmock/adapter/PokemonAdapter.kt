package br.com.escolanovaeratech.listapokemonmock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.escolanovaeratech.listapokemonmock.R
import br.com.escolanovaeratech.listapokemonmock.model.Pokemon
import br.com.escolanovaeratech.listapokemonmock.model.PokemonType

class PokemonAdapter(
    private val onPokemonClick: (Pokemon) -> Unit = {}
) : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view, onPokemonClick)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PokemonViewHolder(
        itemView: View,
        private val onPokemonClick: (Pokemon) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.imagePokemon)
        private val numberView: TextView = itemView.findViewById(R.id.textNumber)
        private val nameView: TextView = itemView.findViewById(R.id.textName)
        private val typesContainer: LinearLayout = itemView.findViewById(R.id.typesContainer)

        fun bind(pokemon: Pokemon) {
            imageView.setImageResource(pokemon.imageRes)
            numberView.text = pokemon.number
            nameView.text = pokemon.name
            bindTypes(pokemon.types)
            itemView.setOnClickListener { onPokemonClick(pokemon) }
        }

        private fun bindTypes(types: List<PokemonType>) {
            typesContainer.removeAllViews()
            val inflater = LayoutInflater.from(itemView.context)
            types.forEach { type ->
                val chip = inflater.inflate(R.layout.view_type_chip, typesContainer, false)
                val dot = chip.findViewById<View>(R.id.typeDot)
                val label = chip.findViewById<TextView>(R.id.textType)
                val color = ContextCompat.getColor(itemView.context, type.colorRes)
                dot.background = dot.background.mutate().also { it.setTint(color) }
                label.text = type.displayName
                typesContainer.addView(chip)
            }
        }
    }

    private object PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }
}
