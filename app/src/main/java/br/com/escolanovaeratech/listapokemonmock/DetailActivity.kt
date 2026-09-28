package br.com.escolanovaeratech.listapokemonmock

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.escolanovaeratech.listapokemonmock.data.PokemonMock

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Find pokemon by id
        val id = intent.getIntExtra(EXTRA_POKEMON_ID, 1000)
        val pokemonModel = PokemonMock.findById(id)

        findViewById<ImageView>(R.id.backButton)
            .setOnClickListener {
                finish()
            }

        val txtName = findViewById<TextView>(R.id.txtName)
        val txtNumber = findViewById<TextView>(R.id.txtNumber)
        val txtDescription = findViewById<TextView>(R.id.txtDescription)
        val txtHeight = findViewById<TextView>(R.id.txtHeight)
        val txtWeight = findViewById<TextView>(R.id.txtWeight)
        val imgPokemon = findViewById<ImageView>(R.id.imgPokemon)
        val main = findViewById<ConstraintLayout>(R.id.main)
        val statsContainer = findViewById<LinearLayout>(R.id.statsContainer)

        val typesContainer = findViewById<LinearLayout>(R.id.typesContainer)

        if (pokemonModel != null) {
            txtName.text = pokemonModel.name
            txtNumber.text = pokemonModel.id.toString()
            txtDescription.text = pokemonModel.description
            txtHeight.text = pokemonModel.heightMeters.toString()
            txtWeight.text = pokemonModel.weightKg.toString()
            imgPokemon.setImageResource(pokemonModel.imageRes)

            // get main color
            val typeFirst = pokemonModel.types.first()
            val headerColor = ContextCompat.getColor(this, typeFirst.headerColorRes)
            main.background = main.background.mutate().also { it.setTint(headerColor) }

            //bind types
            typesContainer.removeAllViews()
            val inflater = LayoutInflater.from(this)
            pokemonModel.types.forEach { type ->
                val chip = inflater.inflate(R.layout.view_detail_type_chip, typesContainer, false) as TextView
                chip.text = type.displayName
                val typeColor = ContextCompat.getColor(this, type.colorRes)
                chip.background = chip.background.mutate().also { it.setTint(typeColor) }
                typesContainer.addView(chip)
            }

            //bind stats
            statsContainer.removeAllViews()
            val stats = listOf(
                Triple(getString(R.string.stat_hp), pokemonModel.hp, R.color.stat_hp),
                Triple(getString(R.string.stat_attack), pokemonModel.attack, R.color.stat_attack),
                Triple(getString(R.string.stat_defense), pokemonModel.defense, R.color.stat_defense),
                Triple(getString(R.string.stat_special_attack), pokemonModel.specialAttack, R.color.stat_special_attack),
                Triple(getString(R.string.stat_special_defense), pokemonModel.specialDefense, R.color.stat_special_defense),
                Triple(getString(R.string.stat_speed), pokemonModel.speed, R.color.stat_speed),
            )

            val inflaterStat = LayoutInflater.from(this)
            stats.forEach { (label, value, colorRes) ->
                val row = inflaterStat.inflate(R.layout.view_stat_row, statsContainer, false)
                val txtLabel = row.findViewById<TextView>(R.id.txtStatLabel)
                val txtValue = row.findViewById<TextView>(R.id.txtStatValue)
                val progressStat = row.findViewById<ProgressBar>(R.id.progressStat)

                txtLabel.text = label
                txtValue.text = value.toString()

                val progressColor = ContextCompat.getColor(this, colorRes)
                val layers = ContextCompat.getDrawable(this, R.drawable.bg_stat_progress)
                as LayerDrawable

                layers.findDrawableByLayerId(android.R.id.progress)
                    .setTint(progressColor)

                progressStat.progressDrawable = layers
                progressStat.progress = value


                statsContainer.addView(row)


            }

        }




    }

    companion object {
        private const val EXTRA_POKEMON_ID = "POKEMON_ID"

        fun createIntent(context: Context, pokemonId: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_POKEMON_ID, pokemonId)
            return intent
        }
    }
}