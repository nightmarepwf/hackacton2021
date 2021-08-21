package com.qavan.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.qavan.app.compose.AppTheme
import com.qavan.app.data.source.local.DevicePreferencesDataSource
import com.qavan.app.manager.ToastManager
import com.qavan.app.ui.screens.create.CreateContract
import com.qavan.app.ui.screens.create.CreateMVI
import com.qavan.app.ui.screens.create.CreateScreen
import com.qavan.app.ui.screens.events.EventMVI
import com.qavan.app.ui.screens.events.EventScreen
import com.qavan.app.ui.screens.launch.LaunchMVI
import com.qavan.app.ui.screens.launch.LaunchScreen
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class AppActivity: AppCompatActivity() {

    private var initialScreenName = Route.Create.name

    @Inject
    lateinit var devicePreferences: DevicePreferencesDataSource

    @Inject
    lateinit var toastManager: ToastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Закрываем диалоговое окно выбора даты после восстановления состояния
        (supportFragmentManager.fragments.firstOrNull { it.tag == DatePickerTag } as? MaterialDatePicker<*>)?.dismiss()
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
        val mviLaunch: LaunchMVI = viewModel()
        val mviEvent: EventMVI = viewModel()
        val mviCreate: CreateMVI = viewModel()
        AnimatedNavHost(navController = navController, startDestination = initialScreenName) {
            screen(Route.Launch, screenWidth) {
                val state = mviLaunch.uiState.collectAsState()
                LaunchScreen(
                    state = state.value.state,
                    onLoginAsDepartmentSpecialistClicked = {
                         navController.navigate(Route.Events.name)
                    },
                    onLoginAsBloggerClicked = {
                         //TODO NAVIGATION
                    },
                )
            }
            screen(Route.Events, screenWidth) {
                val state by mviEvent.uiState.collectAsState()
                val events = mviEvent.events.collectAsLazyPagingItems()
                EventScreen(
                    state = state.state,
                    events = events,
                    onCreateEventClick = {
                        navController.navigate(Route.Create.name)
                    },
                    onEventClick = { event ->

                    },
                )
            }
            screen(Route.Create, screenWidth) {
                val state by mviCreate.uiState.collectAsState()
                val title by mviCreate.title.collectAsState()
                val description by mviCreate.description.collectAsState()
                val time by mviCreate.time.collectAsState()
                CreateScreen(
                    state = state.state,
                    title = title,
                    description = description,
                    time = time,
                    onTitleChange = {
                        mviCreate.setEvent(CreateContract.Event.SetTitle(it))
                    },
                    onDescriptionChange = {
                        mviCreate.setEvent(CreateContract.Event.SetDescription(it))
                    },
                    onDateClicked = {
                        showDatePicker(
                            selection = if (time == -1L)
                                Calendar.getInstance().timeInMillis
                            else
                                time,
                            onSelection = {
                                mviCreate.setEvent(CreateContract.Event.SetDate(it))
                            },
                        )
                    },
                    onBackClicked = {
                        onBackPressed()
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
            factory = defaultViewModelProviderFactory,
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

    private fun showDatePicker(
        title: String = "Выберите дату",
        selection: Long,
        onSelection: (Long) -> Unit = {},
    ) {
        MaterialDatePicker.Builder.datePicker().apply {
            setTitle(title)
            this.setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setStart(Calendar.getInstance().timeInMillis)
                    .setEnd(Calendar.getInstance().apply {
                        set(Calendar.YEAR, this.get(Calendar.YEAR) + 3)
                    }.timeInMillis)
                    .setValidator(DateValidatorPointForward.from(Calendar.getInstance().apply {
                        set(Calendar.DAY_OF_MONTH, this.get(Calendar.DAY_OF_MONTH) - 1)
                    }.timeInMillis))
                    .build()
            )
            this.setSelection(selection)
            this.setInputMode(INPUT_MODE_CALENDAR)
        }.build().apply {
            addOnPositiveButtonClickListener {
                onSelection(it)
                dismiss()
            }
        }.showNow(supportFragmentManager, DatePickerTag)
    }

    companion object {
        private const val DatePickerTag = "DatePicker"
    }


}