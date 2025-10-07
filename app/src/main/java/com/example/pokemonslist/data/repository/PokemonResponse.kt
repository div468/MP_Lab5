package com.example.pokemonslist.data.repository

//Data model to represent a Pokemon list item with its name and URL images
data class PokemonResponse (
    val name: String,
    val id: Int,
) {
    val imageUrlFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

    val imageUrlBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png"

    val imageUrlShinyFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png"

    val imageUrlShinyBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/$id.png"

}
