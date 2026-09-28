package br.com.escolanovaeratech.listapokemonmock.data

import br.com.escolanovaeratech.listapokemonmock.R
import br.com.escolanovaeratech.listapokemonmock.model.PokemonModel
import br.com.escolanovaeratech.listapokemonmock.model.PokemonType

object PokemonMock {

    val pokemons: List<PokemonModel> = listOf(
        PokemonModel(
            id = 1,
            name = "Bulbasaur",
            types = listOf(PokemonType.GRAMA, PokemonType.VENENO),
            description = "Há uma semente de planta nas costas deste Pokémon desde o nascimento. A semente cresce lentamente.",
            heightMeters = 0.7,
            weightKg = 6.9,
            imageRes = R.drawable.bulbasaur,
            hp = 45,
            attack = 49,
            defense = 49,
            specialAttack = 65,
            specialDefense = 65,
            speed = 45
        ),
        PokemonModel(
            id = 4,
            name = "Charmander",
            types = listOf(PokemonType.FOGO),
            description = "Prefere lugares quentes. Quando chove, dizem que sai vapor da ponta de sua cauda.",
            heightMeters = 0.6,
            weightKg = 8.5,
            imageRes = R.drawable.charmander,
            hp = 39,
            attack = 52,
            defense = 43,
            specialAttack = 60,
            specialDefense = 50,
            speed = 65
        ),
        PokemonModel(
            id = 7,
            name = "Squirtle",
            types = listOf(PokemonType.AGUA),
            description = "Após o nascimento, suas costas incham e endurecem, formando um casco. Dispara espuma pela boca.",
            heightMeters = 0.5,
            weightKg = 9.0,
            imageRes = R.drawable.squirtle,
            hp = 44,
            attack = 48,
            defense = 65,
            specialAttack = 50,
            specialDefense = 64,
            speed = 43
        ),
        PokemonModel(
            id = 25,
            name = "Pikachu",
            types = listOf(PokemonType.ELETRICO),
            description = "Pikachu armazena eletricidade em suas bochechas. Quando se sente ameaçado, libera poderosas descargas elétricas.",
            heightMeters = 0.4,
            weightKg = 6.0,
            imageRes = R.drawable.pikachu,
            hp = 35,
            attack = 55,
            defense = 40,
            specialAttack = 50,
            specialDefense = 50,
            speed = 90
        ),
        PokemonModel(
            id = 52,
            name = "Meowth",
            types = listOf(PokemonType.NORMAL),
            description = "Adora moedas. Vagueia pelas ruas coletando moedas durante o dia e patrulha seu território à noite.",
            heightMeters = 0.4,
            weightKg = 4.2,
            imageRes = R.drawable.meowth,
            hp = 40,
            attack = 45,
            defense = 35,
            specialAttack = 40,
            specialDefense = 40,
            speed = 90
        ),
        PokemonModel(
            id = 54,
            name = "Psyduck",
            types = listOf(PokemonType.AGUA),
            description = "Sofre constantemente com dores de cabeça. Quando a dor fica intensa, começa a usar poderes misteriosos.",
            heightMeters = 0.8,
            weightKg = 19.6,
            imageRes = R.drawable.psyduck,
            hp = 50,
            attack = 52,
            defense = 48,
            specialAttack = 65,
            specialDefense = 50,
            speed = 55
        ),
        PokemonModel(
            id = 94,
            name = "Gengar",
            types = listOf(PokemonType.FANTASMA, PokemonType.VENENO),
            description = "Na noite de lua cheia, se as sombras se movem sozinhas e riem, é obra de Gengar.",
            heightMeters = 1.5,
            weightKg = 40.5,
            imageRes = R.drawable.gengar,
            hp = 60,
            attack = 65,
            defense = 60,
            specialAttack = 130,
            specialDefense = 75,
            speed = 110
        ),
        PokemonModel(
            id = 133,
            name = "Eevee",
            types = listOf(PokemonType.NORMAL),
            description = "Seu código genético é irregular. Pode mutar se for exposto à radiação de pedras elementais.",
            heightMeters = 0.3,
            weightKg = 6.5,
            imageRes = R.drawable.eevee,
            hp = 55,
            attack = 55,
            defense = 50,
            specialAttack = 45,
            specialDefense = 65,
            speed = 55
        ),
        PokemonModel(
            id = 143,
            name = "Snorlax",
            types = listOf(PokemonType.NORMAL),
            description = "Muito preguiçoso. Só come e dorme. Conforme seu corpo cresce, fica cada vez mais indolente.",
            heightMeters = 2.1,
            weightKg = 460.0,
            imageRes = R.drawable.snorlax,
            hp = 160,
            attack = 110,
            defense = 65,
            specialAttack = 65,
            specialDefense = 110,
            speed = 30
        )
    )


    fun findById(id: Int): PokemonModel?{
        return pokemons.firstOrNull { it.id == id }
    }
}