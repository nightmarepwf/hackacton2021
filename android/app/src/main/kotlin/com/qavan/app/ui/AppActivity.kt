package com.qavan.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.qavan.app.compose.AppTheme
import com.qavan.app.data.source.local.DevicePreferencesDataSource
import com.qavan.app.ui.screens.launch.LaunchMVI
import com.qavan.app.ui.screens.launch.LaunchScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class AppActivity: ComponentActivity() {

    private var initialScreenName = Route.Launch.name

    @Inject
    lateinit var devicePreferences: DevicePreferencesDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Content()
                }
            }
        }
    }

    @Composable
    private fun Content() {
        val scope = rememberCoroutineScope()
        val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx().toInt() }
        val navController = rememberAnimatedNavController()
        AnimatedNavHost(navController = navController, startDestination = initialScreenName) {
            screen(Route.Launch, screenWidth) {
                val mvi: LaunchMVI = viewModel()
                val state = mvi.uiState.collectAsState()
                LaunchScreen(
                    state = state.value.state,
                    onLoginAsDepartmentSpecialistClicked = {
                         //TODO NAVIGATION
                    },
                    onLoginAsBloggerClicked = {
                         //TODO NAVIGATION
                    },
                )
            }
        }
    }

    @Composable
    private inline fun <reified T: ViewModel> viewModel(
        key: String? = null,
    ): T {
        return viewModel(
            viewModelStoreOwner = requireNotNull(LocalViewModelStoreOwner.current),
            key = key,
        )
    }

    private fun NavGraphBuilder.screen(
        route: Route,
        screenWidth: Int,
        enterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = { initial, _ ->
            slideInHorizontally(initialOffsetX = { screenWidth }, animationSpec = tween(400))
        },
        exitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = { _, target ->
            slideOutHorizontally(targetOffsetX = { -screenWidth }, animationSpec = tween(400))
        },
        popEnterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = { initial, _ ->
            slideInHorizontally(initialOffsetX = { -screenWidth }, animationSpec = tween(400))
        },
        popExitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = { _, target ->
            slideOutHorizontally(targetOffsetX = { screenWidth }, animationSpec = tween(400))
        },
        content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
    ) {
        composable(
            route.name,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) {
            content(it)
        }
    }


}