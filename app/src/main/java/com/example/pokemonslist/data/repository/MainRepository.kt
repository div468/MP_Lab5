package com.example.pokemonslist.data.repository

import com.example.pokemonslist.data.remote.PokeApiService

class MainRepository(private val pokeApiService: PokeApiService) {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
        return pokeApiService.getPokemonList(limit, offset)
    }

    suspend fun getPokemon(name: String): PokemonResponse {
        return pokeApiService.getPokemon(name)
    }
}