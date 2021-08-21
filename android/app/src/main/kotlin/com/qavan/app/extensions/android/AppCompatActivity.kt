package com.qavan.app.extensions.android

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


fun AppCompatActivity.navController(view: FragmentContainerView): NavController {
    return (supportFragmentManager.findFragmentById(view.id) as NavHostFragment).navController
}