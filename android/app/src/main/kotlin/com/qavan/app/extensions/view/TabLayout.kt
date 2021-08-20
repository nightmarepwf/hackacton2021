package com.qavan.app.extensions.view

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

inline fun TabLayout.attachTabLayoutMediator(
    pager: ViewPager2,
    crossinline onAttach: (TabLayout.Tab, Int) -> Unit = { _, _ -> }
) {
    TabLayoutMediator(this, pager) { tab, position ->
        onAttach(tab, position)
    }.attach()
}