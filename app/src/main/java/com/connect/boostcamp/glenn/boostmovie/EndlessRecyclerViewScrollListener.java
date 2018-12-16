package com.connect.boostcamp.glenn.boostmovie;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private final int THRESHOLD = 5;
    private int start;
    private int previousTotalItemCount = 0;
    private int jsonTotal = 0;
    private boolean loading = false;
    private LinearLayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        this.start = -1;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setPreviousTotalItemCount(int count) {
        this.previousTotalItemCount = count;
    }

    public void setJsonTotal(int jsonTotal) {
        this.jsonTotal = jsonTotal;
    }

    public void calledButNoData(Boolean loading) {
        this.loading = loading;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();;
        int totalItemCount = mLayoutManager.getItemCount();

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if ( !loading && start > 0 && (lastVisibleItemPosition + THRESHOLD) > totalItemCount && jsonTotal > totalItemCount) {
            onLoadMore(start);
            loading = true;
        }
    }

    public void resetState() {
        this.start = 1;
        this.loading = true;
    }

    public abstract void onLoadMore(int start);
}
