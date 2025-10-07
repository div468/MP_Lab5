package com.example.pokemonslist.data.repository

import com.example.pokemonslist.data.remote.PokeApiService

//Main repository to handle the API calls and provide the data to the ViewModel
class MainRepository(private val pokeApiService: PokeApiService) {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
        return pokeApiService.getPokemonList(limit, offset)
    }

    suspend fun getPokemon(name: String): PokemonResponse {
        return pokeApiService.getPokemon(name)
    }
}