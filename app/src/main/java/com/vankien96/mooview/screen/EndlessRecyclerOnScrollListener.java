package com.vankien96.mooview.screen;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Admin on 12/18/17.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 5;
    private int mPreviousTotal;
    private boolean isLoading = true;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private int mTotalItemCount;

    private int mCurrentPage = 1;

    private GridLayoutManager mGridLayoutManager;

    public EndlessRecyclerOnScrollListener(GridLayoutManager gridLayoutManager) {
        mGridLayoutManager = gridLayoutManager;
    }

    public void refreshData() {
        mPreviousTotal = 0;
        isLoading = true;
        mFirstVisibleItem = 0;
        mVisibleItemCount = 0;
        mTotalItemCount = 0;
        mCurrentPage = 1;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mVisibleItemCount = recyclerView.getChildCount();
        mTotalItemCount = mGridLayoutManager.getItemCount();
        mFirstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

        if (isLoading && mTotalItemCount > mPreviousTotal) {
            isLoading = false;
            mPreviousTotal = mTotalItemCount;
        }
        if (!isLoading && (mTotalItemCount - mVisibleItemCount) <= (mFirstVisibleItem
                + VISIBLE_THRESHOLD)) {
            mCurrentPage++;
            onLoadMore(mCurrentPage);
            isLoading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
