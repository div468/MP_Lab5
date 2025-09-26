package com.example.pokemonslist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pokemonslist.R
import com.example.pokemonslist.ui.composables.PokemonsLazyList
import com.example.pokemonslist.ui.viewmodel.PokemonListUiState
import com.example.pokemonslist.ui.viewmodel.PokemonViewModel

@Composable
fun PokemonListScreen(
    viewModel: PokemonViewModel,
    onPokemonClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFCE2939))
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.pokemon_list),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is PokemonListUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is PokemonListUiState.Success -> {
                    PokemonsLazyList(
                        pokemons = state.pokemons,
                        onPokemonClick = onPokemonClick
                    )
                }
                is PokemonListUiState.Error -> {
                    Text(stringResource(R.string.error_loading_pokemon))
                }
            }
        }
    }
}