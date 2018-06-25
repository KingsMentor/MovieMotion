package xyz.belvi.motion.custonViews

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by Nosa Belvi on 6/25/18.
 *
 *
 * An enchanced version of Recyclerview tailored
 * specifically to for grid and to listen to when a
 * user scrolls to the end of the list
 *
 *
 *
 */
class EnhanceGridRecyclerView : RecyclerView {

    private var mLayoutManager: GridLayoutManager? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null

    private var scrollCallback: ScrollEndListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun setLayoutManager(layout: RecyclerView.LayoutManager) {
        super.setLayoutManager(layout)
        mLayoutManager = layout as GridLayoutManager
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        super.setAdapter(adapter)
        mAdapter = adapter
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        mAdapter?.let {
            if (mLayoutManager?.findLastCompletelyVisibleItemPosition() == it.itemCount - THRESHOLD) {
                if (scrollCallback != null)
                    scrollCallback?.hasReachedEndOfList()
            }
        }

    }

    fun listen(sc: ScrollEndListener) {
        scrollCallback = sc
    }

    interface ScrollEndListener {
        fun hasReachedEndOfList()
    }

    companion object {

        private val THRESHOLD = 1
    }
}

