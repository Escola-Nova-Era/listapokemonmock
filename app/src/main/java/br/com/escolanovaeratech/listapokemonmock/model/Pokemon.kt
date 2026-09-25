package br.com.escolanovaeratech.listapokemonmock.model

import androidx.annotation.DrawableRes
import java.util.Locale

data class Pokemon(
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
) {
    val number: String
        get() = "#${id.toString().padStart(3, '0')}"

    val formattedHeight: String
        get() = String.format(Locale.forLanguageTag("pt-BR"), "%.1f m", heightMeters)

    val formattedWeight: String
        get() = String.format(Locale.forLanguageTag("pt-BR"), "%.1f kg", weightKg)
}
