# Pokédex
App Android da **Escola Nova Era Tech** que lista Pokémon, permite buscar e abre uma tela de detalhe. Os dados são **100% locais**: não há API, internet nem banco de dados.


<img width="260" alt="Screenshot_20260928_140506" src="https://github.com/user-attachments/assets/855ec7ae-c0e5-462b-800c-589339d0d6d7" />
<img width="260" alt="Screenshot_20260928_140540" src="https://github.com/user-attachments/assets/b5e0830e-bf58-401c-a32b-d8481f40e524" />
<img width="260" alt="Screenshot_20260928_140517" src="https://github.com/user-attachments/assets/7f9abcf0-395d-4b7a-b6f1-402b5311c107" />


## O que o app faz
- **Lista** com sprite, número (`#001`), nome e tipos
- **Busca** em tempo real por nome, número ou tipo
- **Detalhe** com descrição, altura, peso, chips de tipo e barras de estatísticas (HP, Ataque, Defesa, Ataque Esp., Defesa Esp., Velocidade)
- **Favoritar** na tela de detalhe (estado só em memória; não persiste)
Os 9 Pokémon vêm do object `PokemonMock`: Bulbasaur, Charmander, Squirtle, Pikachu, Meowth, Psyduck, Gengar, Eevee e Snorlax. Os sprites estão em `res/drawable`.
## Stack
| Item | Valor |
| --- | --- |
| Linguagem | Kotlin |
| UI | Views XML + Material 3 (sem Compose) |
| minSdk / targetSdk | 24 / 37 |
| AGP | 9.4.1 |
| Pacote | `br.com.escolanovaeratech.listapokemonmock` |
Dependências principais: AppCompat, Material, ConstraintLayout, RecyclerView e Activity KTX.
Não usa ViewModel, Navigation Component, Retrofit, Room, Hilt/Koin nem bibliotecas de imagem.
## Arquitetura
Arquitetura simples, centrada em Activities:
```
MainActivity  →  PokemonAdapter (ListAdapter + DiffUtil)
                      ↓ clique (pokemon.id)
              PokemonDetailActivity
                      ↓
                 PokemonMock.findById(id)
```
- **Model:** `Pokemon` (data class) e `PokemonType` (enum com cor e ícone)
- **Dados:** `PokemonMock` — lista estática em memória
- **UI:** Activities + XML + `findViewById`
- **Navegação:** `Intent` explícito via `PokemonDetailActivity.createIntent(context, pokemonId)`
## Estrutura
```
app/src/main/java/.../listapokemonmock/
├── MainActivity.kt              # lista + busca
├── PokemonDetailActivity.kt     # tela de detalhe
├── adapter/PokemonAdapter.kt    # ListAdapter + ViewHolder
├── data/PokemonMock.kt          # 9 Pokémon mockados
└── model/
    ├── Pokemon.kt
    └── PokemonType.kt
app/src/main/res/layout/
├── activity_main.xml
├── activity_pokemon_detail.xml
├── item_pokemon.xml
├── view_type_chip.xml
├── view_detail_type_chip.xml
└── view_stat_row.xml
```
## Como rodar
Requisitos: Android Studio com SDK 37 e emulador/dispositivo **API 24+**.
1. Abra o projeto no Android Studio
2. Sync Gradle
3. Run no emulador ou device
Pela linha de comando:
```bash
./gradlew :app:installDebug
```

- ConstraintLayout, edge-to-edge e window insets
A versão deste repositório é a referência polida (busca também por número e tipo, número formatado `#001`, botão de favoritar e layouts mais consistentes). A branch `aula` é o código escrito em sala, com os mesmos conceitos e nomes mais didáticos (`DetailActivity`, `PokemonModel`).
