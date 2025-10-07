package com.example.pokemonslist.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokemonslist.data.repository.PokemonResponse
import coil.compose.AsyncImage

//Card for each pokemon in the list
@Composable
fun PokemonCard(
    pokemon: PokemonResponse,
    onPokemonClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CardDefaults.shape
            )
            .clickable { onPokemonClick(pokemon.id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = pokemon.imageUrlFront,
                contentDescription = pokemon.name,
                modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(2f)
            )
        }
    }
}

@Composable
fun PokemonsLazyList(
    pokemons: List<PokemonResponse>,
    onPokemonClick: (Int) -> Unit
) {
    LazyColumn (
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Color(0xFFFFFFFF))
    ){
        items(pokemons) { pokemon ->
            PokemonCard(pokemon = pokemon, onPokemonClick = onPokemonClick)
        }
    }
}
