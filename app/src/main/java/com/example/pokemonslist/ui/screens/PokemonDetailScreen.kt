package com.example.pokemonslist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemonslist.R
import com.example.pokemonslist.data.repository.PokemonResponse
import com.example.pokemonslist.ui.viewmodel.PokemonListUiState
import com.example.pokemonslist.ui.viewmodel.PokemonViewModel

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    viewModel: PokemonViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var pokemon: PokemonResponse? = null

    if (uiState is PokemonListUiState.Success) {
        pokemon = (uiState as PokemonListUiState.Success).pokemons.find { it.id == pokemonId }
    }

    LaunchedEffect(pokemonId, viewModel) {
        val currentPokemonInState = (uiState as? PokemonListUiState.Success)?.pokemons?.find { it.id == pokemonId }
        if (uiState !is PokemonListUiState.Success || currentPokemonInState == null) {
            viewModel.fetchPokemons()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFCE2939))
                .padding(vertical = 12.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }

            val titleText = when (val state = uiState) {
                is PokemonListUiState.Loading -> stringResource(id = R.string.loading)
                is PokemonListUiState.Success -> {
                    val foundPokemon = state.pokemons.find { it.id == pokemonId }
                    foundPokemon?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                        ?: stringResource(id = R.string.pokemon_not_found)
                }
                is PokemonListUiState.Error -> stringResource(id = R.string.error_details)
            }

            Text(
                text = titleText,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 56.dp)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (val state = uiState) {
                is PokemonListUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is PokemonListUiState.Success -> {
                    val currentPokemon = state.pokemons.find { it.id == pokemonId }
                    if (currentPokemon != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                ImageWithLabel(
                                    label = stringResource(R.string.front),
                                    imageUrl = currentPokemon.imageUrlFront,
                                    contentDescription = "${currentPokemon.name} - ${stringResource(R.string.front)}"
                                )
                                ImageWithLabel(
                                    label = stringResource(R.string.back),
                                    imageUrl = currentPokemon.imageUrlBack,
                                    contentDescription = "${currentPokemon.name} - ${stringResource(R.string.back)}"
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                ImageWithLabel(
                                    label = stringResource(R.string.shiny_front),
                                    imageUrl = currentPokemon.imageUrlShinyFront,
                                    contentDescription = "${currentPokemon.name} - ${stringResource(R.string.shiny_front)}"
                                )
                                ImageWithLabel(
                                    label = stringResource(R.string.shiny_back),
                                    imageUrl = currentPokemon.imageUrlShinyBack,
                                    contentDescription = "${currentPokemon.name} - ${stringResource(R.string.shiny_back)}"
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(id = R.string.pokemon_not_found))
                        }
                    }
                }
                is PokemonListUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(id = R.string.error_details))
                    }
                }
            }
        }
    }
}

@Composable
fun ImageWithLabel(label: String, imageUrl: String, contentDescription: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(120.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
