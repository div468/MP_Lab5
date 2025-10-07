package com.example.pokemonslist.data.remote

import com.example.pokemonslist.data.repository.PokemonListResponse
import com.example.pokemonslist.data.repository.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonResponse

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonListResponse
}