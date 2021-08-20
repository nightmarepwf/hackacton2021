package com.qavan.app.base.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.qavan.app.base.mvi.BaseModelViewIntent
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import com.qavan.app.custom.WheelRecycleView
import com.qavan.app.extensions.view.gone
import com.qavan.app.extensions.view.visible
import com.qavan.app.manager.ImageSnackbarManager

/**
 * Базовая активити с поддержкой навигации(меню) двух видов: колесиком и обычной
 * BottomNavigationView
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseNavigationActivity<T: ViewDataBinding, VS: BaseViewState, OSE: BaseOneShotEvent, MVI: BaseModelViewIntent<VS, OSE>>(
    layoutId: Int,
    private val fragmentContainerId: Int,
    initialTabId: Int,
) : BaseBindingActivity<T, VS, OSE, MVI>(layoutId), NavController.OnDestinationChangedListener,
    NavigationBarView.OnItemReselectedListener {

    private val keyNavSelectedItemId by lazy { "KEY_BNSII" }
    private val keyRootFragment by lazy { "KEY_RF" }

    private var isRootFragment = false
    /**
     * Текущий выбранный элемент меню
     */
    protected var navSelectedItemId = initialTabId

    protected val navController: NavController by lazy { (supportFragmentManager.findFragmentById(fragmentContainerId) as NavHostFragment).navController }
    abstract val bottomNavigationView: BottomNavigationView
    abstract val rvNavigation: WheelRecycleView

    abstract val snackbarManager: ImageSnackbarManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            navSelectedItemId = savedInstanceState.getInt(keyNavSelectedItemId, navSelectedItemId)
        }
        setupBottomNavigationViewNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(this)
        bottomNavigationView.setOnItemReselectedListener(null)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        isRootFragment = savedInstanceState.getBoolean(keyRootFragment)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(keyNavSelectedItemId, navSelectedItemId)
        outState.putBoolean(keyRootFragment, isRootFragment)
    }

    /**
     * Установка способа навигации(меню) с использованием BottomNavigationView
     */
    fun setupBottomNavigationViewNavigation() {
        rvNavigation.gone
        rvNavigation.adapter = null
        rvNavigation.layoutManager = null
        bottomNavigationView.visible
        bottomNavigationView.selectedItemId = navSelectedItemId

        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)
        bottomNavigationView.setOnItemReselectedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        navSelectedItemId = controller.graph.id
        snackbarManager.clear()
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val d = (navController.currentDestination?.hierarchy?.find { d -> d.id == item.itemId } as? NavGraph)?.findStartDestination()
        navController.popBackStack(d!!.id, inclusive = false, false)
    }

}

