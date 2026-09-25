package br.com.escolanovaeratech.listapokemonmock

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import br.com.escolanovaeratech.listapokemonmock.adapter.PokemonAdapter
import br.com.escolanovaeratech.listapokemonmock.data.PokemonMock

class MainActivity : AppCompatActivity() {

    private val adapter = PokemonAdapter { pokemon ->
        startActivity(PokemonDetailActivity.createIntent(this, pokemon.id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        applyWindowInsets()
        setupList()
        setupSearch()
    }

    private fun applyWindowInsets() {
        val header = findViewById<View>(R.id.header)
        val recycler = findViewById<RecyclerView>(R.id.recyclerPokemons)
        val headerPaddingTop = header.paddingTop
        val recyclerPaddingBottom = recycler.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(header) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = headerPaddingTop + bars.top,
                bottom = recyclerPaddingBottom + bars.bottom
            )
            insets
        }

    }

    private fun setupList() {
        val recycler = findViewById<RecyclerView>(R.id.recyclerPokemons)
        recycler.adapter = adapter
        adapter.submitList(PokemonMock.pokemons)
    }

    private fun setupSearch() {
        val search = findViewById<EditText>(R.id.inputSearch)
        search.doOnTextChanged { text, _, _, _ ->
            filterPokemon(text?.toString().orEmpty())
        }
    }

    private fun filterPokemon(query: String) {
        val filtered = if (query.isBlank()) {
            PokemonMock.pokemons
        } else {
            PokemonMock.pokemons.filter { pokemon ->
                pokemon.name.contains(query, ignoreCase = true) ||
                        pokemon.number.contains(query, ignoreCase = true) ||
                        pokemon.types.any { it.displayName.contains(query, ignoreCase = true) }
            }
        }
        adapter.submitList(filtered)
    }
}
