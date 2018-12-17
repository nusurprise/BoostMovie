package com.connect.boostcamp.glenn.boostmovie;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private final int THRESHOLD = 5;
    private int start;
    private int jsonTotal;
    private boolean loading;
    private LinearLayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        this.jsonTotal = 0;
        this.loading = false;
    }

    public void setStart(int start) { this.start = start; }

    public void setJsonTotal(int jsonTotal) { this.jsonTotal = jsonTotal; }

    public void setLoading(Boolean loading) { this.loading = loading; }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();

        if ( !loading && (lastVisibleItemPosition + THRESHOLD) > totalItemCount && jsonTotal > totalItemCount) {
            onLoadMore(start);
            loading = true;
        }
    }

    public abstract void onLoadMore(int start);
}
