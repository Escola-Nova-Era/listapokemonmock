package br.com.escolanovaeratech.listapokemonmock.model

import androidx.annotation.DrawableRes

data class PokemonModel(
    val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val description: String,
    val heightMeters: Double,
    val weightKg: Double,
    @param:DrawableRes val imageRes: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val isFavorite: Boolean = false
)