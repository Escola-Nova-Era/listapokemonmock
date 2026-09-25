package br.com.escolanovaeratech.listapokemonmock

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.NestedScrollView
import br.com.escolanovaeratech.listapokemonmock.data.PokemonMock
import br.com.escolanovaeratech.listapokemonmock.model.Pokemon
import br.com.escolanovaeratech.listapokemonmock.model.PokemonType

class PokemonDetailActivity : AppCompatActivity() {

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_detail)

        val pokemon = PokemonMock.findById(intent.getIntExtra(EXTRA_POKEMON_ID, -1))
        if (pokemon == null) {
            finish()
            return
        }

        applyWindowInsets()
        bindPokemon(pokemon)
    }

    private fun applyWindowInsets() {
        val toolbar = findViewById<View>(R.id.toolbar)
        val header = findViewById<View>(R.id.header)
        val details = findViewById<View>(R.id.detailsContainer)
        val toolbarTop = toolbar.paddingTop
        val headerTop = header.paddingTop
        val detailsBottom = details.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailRoot)) { _, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            toolbar.updatePadding(top = toolbarTop + bars.top)
            header.updatePadding(top = headerTop + bars.top)
            details.updatePadding(bottom = detailsBottom + bars.bottom)
            insets
        }
    }

    private fun bindPokemon(pokemon: Pokemon) {
        val primaryType = pokemon.types.first()
        val headerColor = ContextCompat.getColor(this, primaryType.headerColorRes)

        findViewById<View>(R.id.detailRoot).setBackgroundColor(headerColor)
        findViewById<View>(R.id.header).setBackgroundColor(headerColor)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            primaryType.isLightHeader

        val toolbar = findViewById<View>(R.id.toolbar)
        val header = findViewById<View>(R.id.header)
        val scroll = findViewById<NestedScrollView>(R.id.contentSheet)
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        scroll.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val threshold = (header.height - toolbar.height).coerceAtLeast(0)
            toolbar.setBackgroundColor(if (scrollY > threshold) headerColor else Color.TRANSPARENT)
        }

        findViewById<ImageView>(R.id.imagePokemon).setImageResource(pokemon.imageRes)
        findViewById<TextView>(R.id.textNumber).text = pokemon.number
        findViewById<TextView>(R.id.textName).text = pokemon.name
        findViewById<TextView>(R.id.textDescription).text = pokemon.description
        findViewById<TextView>(R.id.textHeight).text = pokemon.formattedHeight
        findViewById<TextView>(R.id.textWeight).text = pokemon.formattedWeight

        bindTypes(pokemon.types)
        bindStats(pokemon)

        isFavorite = pokemon.isFavorite
        val favoriteButton = findViewById<ImageButton>(R.id.buttonFavorite)
        updateFavoriteIcon(favoriteButton)
        favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteIcon(favoriteButton)
        }

        findViewById<ImageButton>(R.id.buttonBack).setOnClickListener { finish() }
    }

    private fun bindTypes(types: List<PokemonType>) {
        val container = findViewById<LinearLayout>(R.id.typesContainer)
        container.removeAllViews()
        val inflater = LayoutInflater.from(this)

        types.forEach { type ->
            val chip = inflater.inflate(R.layout.view_detail_type_chip, container, false) as TextView
            val typeColor = ContextCompat.getColor(this, type.colorRes)
            chip.text = type.displayName
            chip.background = chip.background.mutate().also { it.setTint(typeColor) }
            chip.setTextColor(if (isLightColor(typeColor)) ContextCompat.getColor(this, R.color.text_primary) else Color.WHITE)
            container.addView(chip)
        }
    }

    private fun bindStats(pokemon: Pokemon) {
        val container = findViewById<LinearLayout>(R.id.statsContainer)
        container.removeAllViews()

        val stats = listOf(
            Triple(getString(R.string.stat_hp), pokemon.hp, R.color.stat_hp),
            Triple(getString(R.string.stat_attack), pokemon.attack, R.color.stat_attack),
            Triple(getString(R.string.stat_defense), pokemon.defense, R.color.stat_defense),
            Triple(getString(R.string.stat_special_attack), pokemon.specialAttack, R.color.stat_special_attack),
            Triple(getString(R.string.stat_special_defense), pokemon.specialDefense, R.color.stat_special_defense),
            Triple(getString(R.string.stat_speed), pokemon.speed, R.color.stat_speed)
        )

        val inflater = LayoutInflater.from(this)
        stats.forEach { (label, value, colorRes) ->
            val row = inflater.inflate(R.layout.view_stat_row, container, false)
            row.findViewById<TextView>(R.id.textStatLabel).text = label
            row.findViewById<TextView>(R.id.textStatValue).text = value.toString()

            val progress = row.findViewById<ProgressBar>(R.id.progressStat)
            val layers = ContextCompat.getDrawable(this, R.drawable.bg_stat_progress)
                ?.mutate() as LayerDrawable
            layers.findDrawableByLayerId(android.R.id.progress)
                .setTint(ContextCompat.getColor(this, colorRes))
            progress.progressDrawable = layers
            progress.progress = value.coerceIn(0, 100)
            container.addView(row)
        }
    }

    private fun updateFavoriteIcon(button: ImageButton) {
        button.setImageResource(
            if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        )
        button.contentDescription = getString(
            if (isFavorite) R.string.unfavorite else R.string.favorite
        )
    }

    private fun isLightColor(color: Int): Boolean {
        val luminance = (Color.red(color) * 0.299 + Color.green(color) * 0.587 + Color.blue(color) * 0.114) / 255
        return luminance > 0.6
    }

    companion object {
        private const val EXTRA_POKEMON_ID = "extra_pokemon_id"

        fun createIntent(context: Context, pokemonId: Int): Intent {
            return Intent(context, PokemonDetailActivity::class.java)
                .putExtra(EXTRA_POKEMON_ID, pokemonId)
        }
    }
}
