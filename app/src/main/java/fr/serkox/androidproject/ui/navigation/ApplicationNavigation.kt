package fr.serkox.androidproject.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.serkox.androidproject.ui.screen.MapScreen
import fr.serkox.androidproject.ui.screen.MetarScreen

object NavigationPath{
    const val MAP_SCREEN = "map_screen"
    const val METAR_SCREEN = "metar_screen"
}

fun NavGraphBuilder.addMapScreenNav(
    onButtonClick: () -> Unit
){
    composable(
        route = NavigationPath.MAP_SCREEN
    ) {
        MapScreen(
            onButtonClick = { onButtonClick() }
        )
    }
}

fun NavGraphBuilder.addMetarScreenNav() {
    composable(
        route = NavigationPath.METAR_SCREEN,
    ) {
        MetarScreen()
    }
}
@Composable
fun HomeNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = NavigationPath.MAP_SCREEN,

    ) {
        addMapScreenNav(onButtonClick = {
            navController.navigate(NavigationPath.METAR_SCREEN)
        })
        addMetarScreenNav()
    }
}