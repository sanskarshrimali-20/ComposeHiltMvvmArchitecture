package com.newcompose.composehiltmvvm.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.newcompose.composehiltmvvm.ui.screen.detail.DetailScreen
import com.newcompose.composehiltmvvm.ui.screen.listing.ListingScreen
import androidx.navigation.compose.composable


sealed class ComposeSampleScreens(
    val route:String
){
    object ListingScreen : ComposeSampleScreens("listing_screen")
    object DetailScreen : ComposeSampleScreens("detail_screen")
}


const val DETAIL_SCREEN_ARGS = "DETAIL_SCREEN_ARGS"
const val DEF_DETAIL_SCREEN_ARGS = "DEF_DETAIL_SCREEN_ARGS"

@Composable
fun NavigationHost(navHostController: NavHostController){

    NavHost(
        navController = navHostController,
        startDestination = ComposeSampleScreens.ListingScreen.route,
    ) {


       composable(
            route = ComposeSampleScreens.ListingScreen.route,
        ) { backStackEntry ->
            ListingScreen(
                navController = navHostController,
                viewModel = androidx.hilt.navigation.compose.hiltViewModel()
            )
        }

        composable(
            route = ComposeSampleScreens.DetailScreen.route
                    + "/{$DETAIL_SCREEN_ARGS}",
            arguments = listOf(
                androidx.navigation.navArgument(DETAIL_SCREEN_ARGS) {
                    type = androidx.navigation.NavType.StringType
                }
            ),

            ) { backStackEntry ->

            DetailScreen(
                navController = navHostController,
                args = backStackEntry.arguments?.getString(DETAIL_SCREEN_ARGS)
                    ?: DEF_DETAIL_SCREEN_ARGS,
                viewModel = androidx.hilt.navigation.compose.hiltViewModel()
            )
        }
    }
}