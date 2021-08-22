package com.qavan.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.NavHost
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.text.AppTextSubtitle
import com.qavan.app.data.source.local.DevicePreferencesDataSource
import com.qavan.app.manager.ToastManager
import com.qavan.app.ui.screens.LoginMVI
import com.qavan.app.ui.screens.LoginScreen
import com.qavan.app.ui.screens.bloggers.BloggersContract
import com.qavan.app.ui.screens.bloggers.BloggersMVI
import com.qavan.app.ui.screens.bloggers.BloggersScreen
import com.qavan.app.ui.screens.create.CreateContract
import com.qavan.app.ui.screens.create.CreateMVI
import com.qavan.app.ui.screens.create.CreateScreen
import com.qavan.app.ui.screens.events.EventMVI
import com.qavan.app.ui.screens.events.EventScreen
import com.qavan.app.ui.screens.launch.LaunchMVI
import com.qavan.app.ui.screens.launch.LaunchScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class AppActivity: AppCompatActivity() {

    private var initialScreenName = Route.Events.name

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

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun Content() {
        val scope = rememberCoroutineScope()
        val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx().toInt() }
        val pagerState = rememberPagerState(pageCount = 3)

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Transparent,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = MaterialTheme.colors.primary,
                    )
                }
            ) {
                repeat(pagerState.pageCount) {
                    Tab(
                        text = {
                            AppTextSubtitle(
                                text = when(it) {
                                    0 -> "События"
                                    1 -> "Блоггеры"
                                    else -> "Контент"
                                },
                                color = if (pagerState.currentPage == it)
                                    MaterialTheme.colors.primary
                                else
                                    MaterialTheme.colors.secondary,
                            )
                        },
                        selected = pagerState.currentPage == it,
                        onClick = {
                              scope.launch {
                                  pagerState.animateScrollToPage(it)
                              }
                        },
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
                dragEnabled = false,
            ) {
                when(it) {
                    0 -> {
                        val navController = rememberAnimatedNavController()
                        val mviEvents: EventMVI = viewModel()
                        val mviCreate: CreateMVI = viewModel()
                        AnimatedNavHost(navController = navController, startDestination = Route.Events.name) {
                            screen(Route.Events, screenWidth) {
                                val state by mviEvents.uiState.collectAsState()
                                val events = mviEvents.events.collectAsLazyPagingItems()
                                EventScreen(
                                    state = state.state,
                                    events = events,
                                    onCreateEventClick = {
                                        navController.navigate(Route.Create.name)
                                    },
                                    onEventClick = {

                                    },
                                )
                            }
                            screen(Route.Create, screenWidth) {
                                val stateCreate by mviCreate.uiState.collectAsState()
                                val title by mviCreate.title.collectAsState()
                                val description by mviCreate.description.collectAsState()
                                val time by mviCreate.time.collectAsState()
                                val tags by mviCreate.tags.collectAsState()
                                val mentions by mviCreate.mentions.collectAsState()
                                CreateScreen(
                                    state = stateCreate.state,
                                    title = title,
                                    description = description,
                                    time = time,
                                    tags = tags,
                                    mentions = mentions,
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
                                        scope.launch {
                                            delay(500)
                                            mviCreate.drop()
                                        }
                                    },
                                    onAddTagClicked = { tag ->
                                        mviCreate.setEvent(CreateContract.Event.AddTag(tag))
                                    },
                                    onRemoveTagClicked = { tag ->
                                        mviCreate.setEvent(CreateContract.Event.RemoveTag(tag))
                                    },
                                    onAddMentionClicked = { mention ->
                                        mviCreate.setEvent(CreateContract.Event.AddMention(mention))
                                    },
                                    onRemoveMentionClicked = { mention ->
                                        mviCreate.setEvent(CreateContract.Event.RemoveMention(mention))
                                    },
                                )
                            }
                        }
                    }
                    1 -> {
                        val navController = rememberAnimatedNavController()
                        val mviBloggers: BloggersMVI = viewModel()
                        val state by mviBloggers.uiState.collectAsState()
                        val bloggers = mviBloggers.bloggers.collectAsLazyPagingItems()
                        val selectedBloggers by mviBloggers.selectedBloggers.collectAsState()
                        AnimatedNavHost(navController = navController, startDestination = Route.Bloggers.name) {
                            screen(Route.Bloggers, screenWidth) {
                                BloggersScreen(
                                    state = state.state,
                                    checkable = false,
                                    bloggers = bloggers,
                                    selectedBloggers = selectedBloggers,
                                    onAddBloggerClick = {

                                    },
                                    onBloggerClick = { selected, blogger ->
                                        mviBloggers.setEvent(
                                            if (selected)
                                                BloggersContract.Event.DeselectBlogger(blogger)
                                            else
                                                BloggersContract.Event.SelectBlogger(blogger)
                                        )
                                    }
                                )
                            }
                        }

                    }
                    2 -> {
                        val navController = rememberAnimatedNavController()

                    }
                }
            }
        }
    }

    @Composable
    private fun oldImpl(
        navController: NavHostController,
        screenWidth: Int,
    ) {
        val scope = rememberCoroutineScope()
        val mviLaunch: LaunchMVI = viewModel()
        val mviEvent: EventMVI = viewModel()
        val mviCreate: CreateMVI = viewModel()
        val mviBloggers: BloggersMVI = viewModel()
        AnimatedNavHost(navController = navController, startDestination = initialScreenName) {
            screen(Route.Launch, screenWidth) {
                val state = mviLaunch.uiState.collectAsState()
                LaunchScreen(
                    state = state.value.state,
                    onLoginAsDepartmentSpecialistClicked = {
                        navController.navigate("${Route.Login.name}$IsDepartament")
                    },
                    onLoginAsBloggerClicked = {
                        navController.navigate(Route.Events.name)
                    },
                )
            }
            screen("${Route.Login}$IsDepartament", screenWidth) {
                val mviLogin: LoginMVI = viewModel()
                val state by mviLogin.uiState.collectAsState()
                LoginScreen(
                    state = state.state,
                    onLogin = {
                        scope.launch {
                            delay(400)
                            navController.navigate(Route.Events.name)
                        }
                    }
                )
            }
            screen("${Route.Login}", screenWidth) {
                val mviLogin: LoginMVI = viewModel()
                val state by mviLogin.uiState.collectAsState()
                LoginScreen(
                    state = state.state,
                    onLogin = {
                        scope.launch {
                            delay(400)
                            //TODO
                        }
                    }
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
            screen(Route.Bloggers, screenWidth) {
                val state by mviBloggers.uiState.collectAsState()
                val bloggers = mviBloggers.bloggers.collectAsLazyPagingItems()
                val selectedBloggers by mviBloggers.selectedBloggers.collectAsState()
                BloggersScreen(
                    state = state.state,
                    bloggers = bloggers,
                    selectedBloggers = selectedBloggers,
                    onAddBloggerClick = {

                    },
                    onBloggerClick = { selected, blogger ->
                        mviBloggers.setEvent(
                            if (selected)
                                BloggersContract.Event.DeselectBlogger(blogger)
                            else
                                BloggersContract.Event.SelectBlogger(blogger)
                        )
                    }
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
        arguments: List<NamedNavArgument> = emptyList(),
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
            route = route.name,
            arguments = arguments,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) {
            content(it)
        }
    }

    private fun NavGraphBuilder.screen(
        route: String,
        screenWidth: Int,
        arguments: List<NamedNavArgument> = emptyList(),
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
            route = route,
            arguments = arguments,
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
        private const val IsDepartament = "IsDepartament"
    }


}