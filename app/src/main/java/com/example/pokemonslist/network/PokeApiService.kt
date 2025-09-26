package com.example.pokemonslist.network

import com.example.pokemonslist.data.PokemonListResponse
import com.example.pokemonslist.data.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonResponse

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonListResponse
}