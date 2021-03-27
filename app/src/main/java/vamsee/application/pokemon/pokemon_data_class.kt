package vamsee.application.pokemon

import org.json.JSONArray

data class pokemon_data_class(
        val id: String,
        val name: String,
        val img_url: String,
        val type: JSONArray,
        val x_desc: String,
        val height: String,
        val weight: String,
        val weakness: JSONArray,
        val evolution: ArrayList<String>,
        val hp: Int,
        val attack: Int,
        val defence: Int,
        val speed: Int,
        val total: Int,
)


