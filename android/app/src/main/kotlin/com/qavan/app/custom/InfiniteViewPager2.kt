package com.qavan.app.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.qavan.app.R

class InfiniteViewPager2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    companion object {
        val HORIZONTAL = RecyclerView.HORIZONTAL
        val VERTICAL = RecyclerView.VERTICAL
    }

    private val viewPager2: ViewPager2 by lazy {
        findViewById(R.id.view_pager_infinite)
    }

    private val internalRecyclerView by lazy {
        (viewPager2.getChildAt(0) as RecyclerView).apply {
            setItemViewCacheSize(2)
        }
    }

    private var totalItemCount = 0
    private var isHorizontalOrientation: Boolean = true

    var orientation: Int = HORIZONTAL
        set(value) {
            if (value != HORIZONTAL && value != VERTICAL ) return
            field = value
            val lm = (internalRecyclerView.layoutManager as? LinearLayoutManager) ?: return
            lm.orientation = value
            internalRecyclerView.layoutManager = lm
            isHorizontalOrientation = value == HORIZONTAL
        }

    var scrolledToListener: ScrolledToListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_pager2, this, true)
    }

    fun <T : RecyclerView.ViewHolder> setAdapter(
        adapter: RecyclerView.Adapter<T>,
        orientation: Int = HORIZONTAL,
    ) {
        viewPager2.adapter = adapter
        viewPager2.setCurrentItem(1, false)
        totalItemCount = adapter.itemCount
        this@InfiniteViewPager2.orientation = orientation
        isHorizontalOrientation = this@InfiniteViewPager2.orientation == HORIZONTAL

        internalRecyclerView.apply {
            addOnScrollListener(
                InfiniteScrollBehaviour(
                    totalItemCount,
                    layoutManager as LinearLayoutManager
                )
            )
        }
    }

    /**
     *                        Auto move over to actual First View when the we reach the Fake First View
     *                        ______________________________________________
     *                       |                                              |
     *                       v                                              |
     * Fake Last View <-> First View <-> Second View <-> Last View <-> Fake First View
     *      |                                                ^
     *      |________________________________________________|
     *      Auto move over to actual Last View when we reach the Fack Last View
     */
    inner class InfiniteScrollBehaviour(
        private val itemCount: Int,
        private val layoutManager: LinearLayoutManager
    ) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val firstItemVisible = layoutManager.findFirstVisibleItemPosition()
            val lastItemVisible = layoutManager.findLastVisibleItemPosition()

            val delta = if (isHorizontalOrientation) dx else dy

            if (firstItemVisible == (itemCount - 1) && delta > 0) {
                recyclerView.scrollToPosition(1)
            } else if (lastItemVisible == 0 && delta < 0) {
                recyclerView.scrollToPosition(itemCount - 2)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                scrolledToListener?.scrolledTo(getCurrentItem())
            }
        }
    }

    /**
     *          Fake Last View <-> First View <-> Second View <-> Last View <-> Fake First View
     * index :        0                1                2             3          ItemCount - 1
     * Actual = ItemCount - 3        Index-1        Index-1        Index-1             0
     *
     * count 에서 두개의 facked item 을 제거해야만 하기 때문에 ItemCount - 3 이고, 0 인덱스 기반 계산으로 정규화하려면 -1 이다.
     */
    fun getCurrentItem(): Int {
        return when (viewPager2.currentItem) {
            0 -> totalItemCount - 3
            totalItemCount - 1 -> 0
            else -> viewPager2.currentItem - 1
        }
    }

    fun addScrollListener(scrollListener: RecyclerView.OnScrollListener) {
        internalRecyclerView.addOnScrollListener(scrollListener)
    }

    fun removeScrollListener(scrollListener: RecyclerView.OnScrollListener) {
        internalRecyclerView.removeOnScrollListener(scrollListener)
    }

    fun interface ScrolledToListener {
        fun scrolledTo(position: Int)
    }
}