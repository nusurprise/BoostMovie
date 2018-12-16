package com.connect.boostcamp.glenn.boostmovie;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.connect.boostcamp.glenn.boostmovie.databinding.DatabindingItemBinding;
import com.connect.boostcamp.glenn.boostmovie.model.Movie;
import com.connect.boostcamp.glenn.boostmovie.model.MyHandler;


public class DataBindingAdapter extends BaseRecyclerViewAdapter<Movie, DataBindingAdapter.MovieViewHolder> {

    public DataBindingAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindView(MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        holder.binding.setMovie(movie);
        holder.binding.setMyHandler(new MyHandler(movie.getLink()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.databinding_item, parent, false);
        return new MovieViewHolder(view);
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {
        DatabindingItemBinding binding;

        public MovieViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}

