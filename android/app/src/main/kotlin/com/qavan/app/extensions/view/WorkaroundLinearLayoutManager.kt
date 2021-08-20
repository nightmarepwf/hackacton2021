package com.qavan.app.extensions.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class WorkaroundLinearLayoutManager : LinearLayoutManager {

    constructor(
        context: Context,
        orientation: Int,
        isReverse: Boolean
    ) : super(
        context,
        orientation,
        isReverse
    )

    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }

    override fun onLayoutChildren(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

}