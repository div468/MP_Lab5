package com.example.pokemonslist.data.remote
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Retrofit instance inicialization for the PokeAPI service
object RetrofitClient {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    val instance: PokeApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(PokeApiService::class.java)
    }
}