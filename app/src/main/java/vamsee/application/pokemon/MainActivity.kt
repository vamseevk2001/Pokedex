package vamsee.application.pokemon
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import vamsee.application.pokemon.databinding.ActivityMainBinding
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), PokeClick, SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener, Serializable {

    lateinit var mAdapter: PokemonListAdapter
    var mPokeArray: ArrayList<pokemon_data_class> = ArrayList()

    //View Binding:
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        mAdapter = PokemonListAdapter(this)
        var recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(applicationContext, 1)
        recyclerView.layoutManager = gridLayoutManager // set LayoutManager to RecyclerView
        recyclerView.adapter = mAdapter
        loadPoke()
        search()
    }





    private fun loadPoke() {
        val url = "https://gist.githubusercontent.com/mrcsxsiq/b94dbe9ab67147b642baa9109ce16e44/raw/97811a5df2df7304b5bc4fbb9ee018a0339b8a38/"
        //JSON Array  REQUEST:
        val loader: LottieAnimationView = findViewById(R.id.recyclerViewLoader)
        loader.visibility = View.VISIBLE
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            Log.d("tag", response.toString())
            for (i in 0 until response.length()) {
                val pokeJsonObject = response.getJSONObject(i)
                val evl :ArrayList<String> = ArrayList()
                val evolution: JSONArray = pokeJsonObject.getJSONArray("evolutions")
                for (i in 0 until evolution.length()){
                    evl.add(evolution[i].toString())
                }
                val pokemon = pokemon_data_class(
                        pokeJsonObject.getString("id"),
                        pokeJsonObject.getString("name"),
                        pokeJsonObject.getString("imageurl"),
                        pokeJsonObject.getJSONArray("typeofpokemon"),
                        pokeJsonObject.getString("xdescription"),
                        pokeJsonObject.getString("height"),
                        pokeJsonObject.getString("weight"),
                        pokeJsonObject.getJSONArray("weaknesses"),
                        evl,
                        pokeJsonObject.getInt("hp"),
                        pokeJsonObject.getInt("attack"),
                        pokeJsonObject.getInt("defense"),
                        pokeJsonObject.getInt("speed"),
                        pokeJsonObject.getInt("total"),
                )
                mPokeArray.add(pokemon)
            }
            mAdapter.updatePokemons(mPokeArray)
            loader.visibility = View.GONE

        }, { _ ->
            Toast.makeText(
                this,
                "Please check your internet connection!",
                Toast.LENGTH_LONG
            ).show()

            loader.visibility = View.GONE
        })
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }


    private fun getPokemonById(num : String?) : pokemon_data_class?{
        for (pokemon in mPokeArray)
            if(pokemon.id.equals(num))
                return pokemon

        return null
    }
    override fun onPokeClick(item: pokemon_data_class) {
        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra("img", item.img_url)
        intent.putExtra("name", item.name)

        val evolution: ArrayList<String> = ArrayList(item.evolution.size)
        for (element in item.evolution){
            evolution.add(element.toString())
        }

        val weaknesses: ArrayList<String> = ArrayList()
        for (element in 0 until item.weakness.length()){
            weaknesses.add(item.weakness[element].toString())
        }

        for (i in 0 until item.type.length()){
            intent.putExtra("type_${i + 1}", item.type[i].toString())
        }

        val pokeEvolution: ArrayList<String> = ArrayList()

        Log.d("evolution", pokeEvolution.toString())


        for (i in evolution) {
            val pokemon: pokemon_data_class? = getPokemonById(i)
            if (pokemon != null) {
                pokeEvolution.add(pokemon.img_url)
            }

        }


//        for(i in 0 until mPokeArray.size) {
//            if(mPokeArray[i].id == evolution[0]) {
//                val pokemon = mPokeArray[i].img_url
//                pokeEvolution.add(pokemon)
//            }
//            if(mPokeArray[i].id == evolution[1]) {
//                val pokemon = mPokeArray[i].img_url
//                pokeEvolution.add(pokemon)
//            }
//            if(mPokeArray[i].id == evolution[2]) {
//                val pokemon = mPokeArray[i].img_url
//                pokeEvolution.add(pokemon)
//            }
//        }


        intent.putExtra("description", item.x_desc)
        intent.putExtra("height", item.height)
        intent.putExtra("weight", item.weight)
        intent.putExtra("hp", item.hp)
        intent.putExtra("attack", item.attack)
        intent.putExtra("defense", item.defence)
        intent.putExtra("speed", item.speed)
        intent.putExtra("total", item.total)
        //intent.putExtra("allPokemons", mPokeArray)
        intent.putStringArrayListExtra("evolution", pokeEvolution)
        intent.putStringArrayListExtra("weakness", weaknesses)

        startActivity(intent)
    }

    private fun search(){
        val searchView= findViewById<androidx.appcompat.widget.SearchView>(R.id.search)
        searchView.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            applySearch(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            applySearch(newText)
        }
        return true
    }

    private fun applySearch(searchStr: String) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].name.toLowerCase().contains(searchStr.toLowerCase(Locale.ROOT))){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)
    }

    fun electric(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].type[0].toString() == "Electric"){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)
    }
    fun fire(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].type[0].toString() == "Fire"){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)

    }
    fun grass(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].type[0].toString() == "Grass"){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)


    }
    fun psychic(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].type[0].toString() == "Psychic"){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)
    }


    fun all(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
                searchPoke.add(mPokeArray[i])
        }
        mAdapter.updatePokemons(searchPoke)
    }

    fun Water(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].type[0].toString() == "Water"){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)

    }

    fun dragon(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].type[0].toString() == "Dragon"){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)

    }

    fun ice(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].type[0].toString() == "Ice"){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)

    }

    fun fighting(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].type[0].toString() == "Fighting"){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)

    }

    fun rock(view: View) {
        val searchPoke: ArrayList<pokemon_data_class> = ArrayList()
        for (i in 0 until mPokeArray.size){
            if (mPokeArray[i].type[0].toString() == "Rock"){
                searchPoke.add(mPokeArray[i])
            }
        }
        mAdapter.updatePokemons(searchPoke)

    }

    fun aboutApp(view: View) {
        val intent = Intent(this, AboutPokedex::class.java)
        startActivity(intent)
    }
}
