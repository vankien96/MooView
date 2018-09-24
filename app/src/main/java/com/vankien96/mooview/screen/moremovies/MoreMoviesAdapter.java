package com.vankien96.mooview.screen.moremovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.vankien96.mooview.R;
import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.screen.OnRecyclerViewItemListener;
import com.vankien96.mooview.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/18/17.
 */

public class MoreMoviesAdapter extends RecyclerView.Adapter<MoreMoviesAdapter.MoreMoviesHolder> {

    private List<Movie> mMovieList = new ArrayList<>();
    private OnRecyclerViewItemListener mOnRecyclerViewItemListener;
    private Context mContext;
    private LayoutInflater mInflater;

    public MoreMoviesAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MoreMoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.more_movies_item, parent, false);
        return new MoreMoviesHolder(itemView, mOnRecyclerViewItemListener);
    }

    @Override
    public void onBindViewHolder(
            MoreMoviesHolder holder, int position) {
        holder.bind(mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setOnRecyclerViewItemListener(
            OnRecyclerViewItemListener onRecyclerViewItemListener) {
        mOnRecyclerViewItemListener = onRecyclerViewItemListener;
    }

    public void updateData(List<Movie> movieList) {
        if (movieList == null) {
            return;
        }
        mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    static class MoreMoviesHolder extends RecyclerView.ViewHolder {

        private Movie mMovie;
        private ImageView mMoreMoviesPoster;
        private TextView mMoreMoviesName;
        private OnRecyclerViewItemListener mOnRecyclerViewItemListener;

        MoreMoviesHolder(View itemView, OnRecyclerViewItemListener onRecyclerViewItemListener) {
            super(itemView);
            mMoreMoviesPoster = itemView.findViewById(R.id.image_more_poster_item);
            mMoreMoviesName = itemView.findViewById(R.id.text_more_name_item);
            mOnRecyclerViewItemListener = onRecyclerViewItemListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnRecyclerViewItemListener.onItemClick(mMovie);
                }
            });
        }

        public void bind(Movie movie) {
            mMovie = movie;
            String urlPoster = StringUtils.convertPosterPathToUrlPoster(movie.getPosterPath());
            String titleMovie = StringUtils.convertLongTitleToShortTitle(movie.getTitle(),
                    movie.getReleaseDate());
            Glide.with(itemView.getContext()).load(urlPoster).into(mMoreMoviesPoster);
            mMoreMoviesName.setText(titleMovie);
        }
    }
}
