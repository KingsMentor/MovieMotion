package xyz.belvi.motion.enchanceViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by zone2 on 4/12/17.
 */

public class EnhanceGridRecyclerView extends RecyclerView {

    private static final int THRESHOLD = 1;

    public EnhanceGridRecyclerView(Context context) {
        super(context);
    }

    public EnhanceGridRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EnhanceGridRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private GridLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        super.setLayoutManager(layout);
        mLayoutManager = (GridLayoutManager) layout;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        mAdapter = adapter;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (mAdapter != null)
            if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mAdapter.getItemCount() - THRESHOLD) {
                if (scrollCallback != null)
                    scrollCallback.reachedEndOfList();
            }
    }

    private listenToScroll scrollCallback;

    public void listen(listenToScroll sc) {
        scrollCallback = sc;
    }

    public interface listenToScroll {
        void reachedEndOfList();
    }
}


