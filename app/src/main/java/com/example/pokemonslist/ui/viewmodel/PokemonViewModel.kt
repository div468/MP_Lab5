package com.example.pokemonslist.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonslist.data.PokemonResponse
import com.example.pokemonslist.network.PokeApiService
import com.example.pokemonslist.network.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PokemonListUiState {
    data class Success(val pokemons: List<PokemonResponse>) : PokemonListUiState
    data object Error : PokemonListUiState
    data object Loading : PokemonListUiState
}

class PokemonViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    private val pokeApiService: PokeApiService = RetrofitClient.instance

    init {
        fetchPokemons()
    }

    fun fetchPokemons() {
        _uiState.value = PokemonListUiState.Loading
        viewModelScope.launch {
            try {
                val pokemonListItems = pokeApiService.getPokemonList(limit = 100, offset = 0).results

                val detailedPokemons = pokemonListItems.map { listItem ->
                    async {
                        Log.d("PokemonViewModel", "Fetching details for URL: ${listItem.url}")
                        val urlParts = listItem.url.trimEnd('/').split('/')
                        val pokemonId = urlParts.last()
                        Log.d("PokemonViewModel", "Extracted ID: $pokemonId for URL: ${listItem.url}")
                        pokeApiService.getPokemon(pokemonId)
                    }
                }.awaitAll()

                _uiState.value = PokemonListUiState.Success(detailedPokemons)
            } catch (e: Exception) {
                Log.e("PokemonViewModel", "Error fetching Pokemons: ${e.message}", e)
                _uiState.value = PokemonListUiState.Error
            }
        }
    }
}
