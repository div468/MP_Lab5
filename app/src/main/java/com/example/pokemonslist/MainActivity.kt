package com.example.pokemonslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonslist.data.remote.RetrofitClient
import com.example.pokemonslist.data.repository.MainRepository
import com.example.pokemonslist.ui.screens.PokemonDetailScreen
import com.example.pokemonslist.ui.screens.PokemonListScreen
import com.example.pokemonslist.ui.theme.PokemonsListTheme
import com.example.pokemonslist.ui.viewmodel.PokemonViewModel

//Main activity of the application
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonsListTheme {
                PokemonAppNavigation()
            }
        }
    }
}

@Composable
fun PokemonAppNavigation() {
    val navController = rememberNavController()
    val pokemonViewModel: PokemonViewModel = viewModel {
        val repository = MainRepository(RetrofitClient.instance)
        PokemonViewModel(repository)
    }

    NavHost(navController = navController, startDestination = "pokemon_list") {
        composable("pokemon_list") {
            PokemonListScreen(
                viewModel = pokemonViewModel,
                onPokemonClick = { pokemonId ->
                    navController.navigate("pokemon_detail/$pokemonId")
                }
            )
        }
        composable(
            route = "pokemon_detail/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId")
            if (pokemonId != null) {
                PokemonDetailScreen(
                    pokemonId = pokemonId,
                    viewModel = pokemonViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                navController.popBackStack()
            }
        }
    }
}
