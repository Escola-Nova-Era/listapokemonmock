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
import br.com.escolanovaeratech.listapokemonmock.model.PokemonModel
import br.com.escolanovaeratech.listapokemonmock.model.PokemonType
import org.w3c.dom.Text

class PokemonAdapter(
    private val onClickListener: (PokemonModel) -> Unit
) : ListAdapter<PokemonModel, PokemonAdapter.PokemonViewHolder>(
    PokemonDiffCallback
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int
    ) {
        val model = getItem(position)
        holder.bind(model)
    }


    class PokemonViewHolder(
        itemView: View,
        val onClickListener: (PokemonModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val imgPokemon = itemView.findViewById<ImageView>(R.id.imgPokemon)
        private val txtId = itemView.findViewById<TextView>(R.id.txtId)
        private val txtName = itemView.findViewById<TextView>(R.id.txtName)

        private val typeContainer = itemView.findViewById<LinearLayout>(R.id.typeContainer)

        fun bind(model: PokemonModel) {
            imgPokemon.setImageResource(model.imageRes)
            txtName.text = model.name
            txtId.text = model.id.toString()

            itemView.setOnClickListener {
                onClickListener.invoke(model)
            }

            bindType(model.types)
        }

        private fun bindType(types: List<PokemonType>) {
            typeContainer.removeAllViews()

            val inflater = LayoutInflater.from(itemView.context)
            types.forEach { type ->
                val chip = inflater.inflate(R.layout.view_type_chip, typeContainer, false)
                val typeName = chip.findViewById<TextView>(R.id.txtTypeName)
                val typeDot = chip.findViewById<View>(R.id.typeDot)
                typeName.text = type.displayName

                val color = ContextCompat.getColor(itemView.context, type.colorRes)
                typeDot.background = typeDot.background.mutate().also { it.setTint(color) }
                typeContainer.addView(chip)
            }

        }

    }


    private object PokemonDiffCallback : DiffUtil.ItemCallback<PokemonModel>() {
        override fun areItemsTheSame(
            oldItem: PokemonModel,
            newItem: PokemonModel
        ): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: PokemonModel,
            newItem: PokemonModel
        ): Boolean {
            return newItem == oldItem
        }

    }

}