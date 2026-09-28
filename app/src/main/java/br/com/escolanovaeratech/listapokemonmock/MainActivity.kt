package br.com.escolanovaeratech.listapokemonmock

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import br.com.escolanovaeratech.listapokemonmock.adapter.PokemonAdapter
import br.com.escolanovaeratech.listapokemonmock.data.PokemonMock

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Recuperar recyclerview por id
        val rvPokemon = findViewById<RecyclerView>(R.id.rvPokemon)
        val header = findViewById<ConstraintLayout>(R.id.header)

        applyWindowInsets(header)

        // Criar adapter
        val adapter = PokemonAdapter { clickedPokemon ->

            val intent = DetailActivity.createIntent(this, clickedPokemon.id)
            startActivity(intent)
        }

        // conectar recyclerview com adapter
        rvPokemon.adapter = adapter

        // Recuperar lista de mocks
        val pokemons = PokemonMock.pokemons

        // Submter a lista
        adapter.submitList(pokemons)

        val edtInputSearch = findViewById<EditText>(R.id.inputSearch)
        edtInputSearch.doOnTextChanged { text, _, _, _ ->
            val filteredPokemons = if(text.isNullOrBlank()){
                PokemonMock.pokemons
            }else{
                PokemonMock.pokemons.filter { pokemon ->
                    pokemon.name.contains(text, ignoreCase = true)
                }
            }

            adapter.submitList(filteredPokemons)
        }

    }

    private fun applyWindowInsets(header: ConstraintLayout) {
        val main = findViewById<ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(main) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val headerPadding = header.paddingTop

            view.updatePadding(
                top = headerPadding +  bars.top,
            )
            insets
        }
    }

}
