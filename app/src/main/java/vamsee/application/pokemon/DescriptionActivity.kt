package vamsee.application.pokemon

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import vamsee.application.pokemon.databinding.ActivityPokemonDescriptionBinding


@Suppress("DEPRECATION")
class DescriptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityPokemonDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_description)
        binding = ActivityPokemonDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        incomingIntent()
    }

    private fun incomingIntent(){
        if (intent.hasExtra("img") && intent.hasExtra("name")){
            Log.d("tag", "my Message")
            val imageUrl = intent.getStringExtra("img")
            val pokemonName = intent.getStringExtra("name")
            val type1 = intent.getStringExtra("type_1")
            val type2 = intent.getStringExtra("type_2")
            val description = intent.getStringExtra("description")
            val height = intent.getStringExtra("height")
            val weight = intent.getStringExtra("weight")
            val hp = intent.getIntExtra("hp", 0)
            val attack = intent.getIntExtra("attack", 0)
            val speed = intent.getIntExtra("speed", 0)
            val defence = intent.getIntExtra("defense", 0)
            val total = intent.getIntExtra("total", 0)

            val weakness:ArrayList<String> = intent.getStringArrayListExtra("weakness") as ArrayList<String>
            val evolution:ArrayList<String> = intent.getStringArrayListExtra("evolution") as ArrayList<String>


            setEvoluionData(evolution)

            if(description != null && height != null && weight != null && imageUrl != null && pokemonName != null && type1 != null)
            setAboutData(description, height, weight, hp, attack, defence, speed, total, weakness)

            if (imageUrl != null && pokemonName != null && type1 != null) {
                if (type2 != null) {
                    setData(imageUrl, pokemonName, type1, type2)
                }
                else{
                    setData(imageUrl, pokemonName, type1, null)
                }
            }
            Log.d("glide", "image")
        }
    }

    private fun setData(img: String?, pokemonName: String?, pokeType_1: String?, pokeType_2: String?){
        binding.loader.visibility = View.VISIBLE
        Glide.with(this).load(img).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
            ): Boolean {
                binding.loader.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
            ): Boolean {
                binding.loader.visibility = View.GONE
                return false
            }
        }

        ).dontTransform().into(binding.pokemonImage)

        binding.descName.text = pokemonName
        binding.type1.text = pokeType_1
        if (pokeType_2 == null){
            binding.type2.visibility = View.GONE
        }
        else {
            binding.type2.text = pokeType_2
        }
        if (pokeType_1 != null) {
            background(pokeType_1)
        }
    }

    private fun setAboutData(desc: String?, height: String?, weight: String?, hp: Int, attack: Int, defence: Int, speed: Int, total: Int, weakness: ArrayList<String>){
        val poke_weakness = findViewById<TextView>(R.id.weakness)


        binding.pokemonDescription.text = desc
        binding.height.text = height
        binding.weight.text = weight
        binding.hpProgressBar.progress = hp
        binding.defenceProgressBar.progress = defence
        binding.attackProgressBar.progress = attack
        binding.speedProgressBar.progress = speed
        binding.totalProgressBar.progress = total

        var pokeWeakness: StringBuilder = StringBuilder()
        for (i in weakness){
            pokeWeakness.append("$i  ")
        }
        poke_weakness.text = pokeWeakness
    }

    private fun setEvoluionData(evolution: ArrayList<String>){
        when (evolution.size) {
            1 -> {
                binding.first.visibility = View.GONE
                binding.second.visibility = View.GONE
                binding.third.visibility = View.GONE
                binding.arrow1.visibility = View.GONE
                binding.arrow2.visibility = View.GONE
                binding.lable.text = "This pokemon does not evolve......"
                binding.lable.layout
                binding.lable.gravity
            }
            2 -> {
                binding.third.visibility = View.GONE
                binding.arrow2.visibility = View.GONE
                Glide.with(this).load(evolution[0]).into(binding.first)
                Glide.with(this).load(evolution[1]).into(binding.second)
            }
            3  -> {
                Glide.with(this).load(evolution[0]).into(binding.first)
                Glide.with(this).load(evolution[1]).into(binding.second)
                Glide.with(this).load(evolution[2]).into(binding.third)
            }
            else ->{
                Glide.with(this).load(evolution[0]).into(binding.first)
                Glide.with(this).load(evolution[1]).into(binding.second)
                Glide.with(this).load(evolution[2]).into(binding.third)
            }

        }
    }

    private fun background(type: String){

        val bg = binding.descriptionBg
        val pokeball = binding.pokeball
        val type1 = binding.type1
        val type2 = binding.type2

        when(type){
            "Grass" -> {
                viewBg(bg, pokeball, type1, type2, R.color.grassLight, R.color.grassDark)
            }
            "Fire" -> {
                viewBg(bg, pokeball, type1, type2, R.color.fireLight, R.color.fireDark)
            }
            "Water" -> {
                viewBg(bg, pokeball, type1, type2, R.color.watersLight, R.color.waterDark)
            }
            "Electric" -> {
                viewBg(bg, pokeball, type1, type2, R.color.electricLight, R.color.electricDark)

            }
            "Bug", "Poison" -> {
                viewBg(bg, pokeball, type1, type2, R.color.bugLight, R.color.bugDark)

            }
            "Ground" -> {
                viewBg(bg, pokeball, type1, type2, R.color.groundLight, R.color.groundDark)

            }
            "Normal", "Psychic" -> {
                viewBg(bg, pokeball, type1, type2, R.color.normalLight, R.color.normalDark)
            }
            "Dragon" -> {
                viewBg(bg, pokeball, type1, type2, R.color.dragonLight, R.color.dragonDark)
            }
            else -> {
                viewBg(bg, pokeball, type1, type2, R.color.defaultLight, R.color.defaultDark)
            }

        }

    }

    private fun viewBg(bg: ConstraintLayout, pokeball: ImageView, type1: TextView, type2: TextView, light: Int, dark: Int){
        bg.setBackgroundColor(bg.context.resources.getColor(light))
        pokeball.setColorFilter(dark)
        type1.setBackgroundColor(bg.context.resources.getColor(dark))
        type2.setBackgroundColor(bg.context.resources.getColor(dark))
    }

}
