package xyz.belvi.motion.custonViews

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by zone2 on 6/25/18.
 */
class EnhanceGridRecyclerView : RecyclerView {

    private var mLayoutManager: GridLayoutManager? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null

    private var scrollCallback: listenToScroll? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

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
        if (mAdapter != null)
            if (mLayoutManager!!.findLastCompletelyVisibleItemPosition() == mAdapter!!.itemCount - THRESHOLD) {
                if (scrollCallback != null)
                    scrollCallback!!.reachedEndOfList()
            }
    }

    fun listen(sc: listenToScroll) {
        scrollCallback = sc
    }

    interface listenToScroll {
        fun reachedEndOfList()
    }

    companion object {

        private val THRESHOLD = 1
    }
}

