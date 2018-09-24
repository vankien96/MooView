package com.vankien96.mooview.screen.moremovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.vankien96.mooview.MainApplication;
import com.vankien96.mooview.R;
import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.screen.BaseActivity;
import com.vankien96.mooview.screen.EndlessRecyclerOnScrollListener;
import com.vankien96.mooview.screen.OnRecyclerViewItemListener;
import com.vankien96.mooview.screen.detailsmovie.DetailsMovieActivity;
import com.vankien96.mooview.utils.Constant;
import java.util.List;

/**
 * MoreMovies Screen.
 */
public class MoreMoviesActivity extends BaseActivity
        implements MoreMoviesContract.MoreMoviesView, OnRecyclerViewItemListener {

    private static final int NUMBER_COLUMNS = 3;
    MoreMoviesContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private MoreMoviesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moremovies);

        mPresenter = new MoreMoviesPresenter();
        mPresenter.setView(this);
        MoviesApi moviesApi = MainApplication.getMoviesApi();
        mPresenter.setMoviesApi(moviesApi);

        initViews();
        setHomeButtonToolbar();
        mPresenter.getListMoviesByCategory();

        mRecyclerView.addOnScrollListener(
                new EndlessRecyclerOnScrollListener((GridLayoutManager) mLayoutManager) {
                    @Override
                    public void onLoadMore(int currentPage) {
                        mPresenter.getListMoreMovie(currentPage);
                    }
                });
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recycler_movies);
        mLayoutManager = new GridLayoutManager(this, NUMBER_COLUMNS);
        mAdapter = new MoreMoviesAdapter(this);
        mAdapter.setOnRecyclerViewItemListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mToolbar = findViewById(R.id.tool_bar_more_movies);
        if (getCategoryMovie().equals(Constant.CATEGORY_NOW_PLAYING)) {
            mToolbar.setTitle(Constant.TITLE_NOW_PLAYING);
        } else if (getCategoryMovie().equals(Constant.CATEGORY_UPCOMING)) {
            mToolbar.setTitle(Constant.TITLE_UP_COMING);
        } else if (getCategoryMovie().equals(Constant.CATEGORY_TOP_RATED)) {
            mToolbar.setTitle(Constant.TITLE_TOP_RATED);
        } else {
            mToolbar.setTitle(Constant.TITLE_POPULAR);
        }
    }

    private void setHomeButtonToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailsMovieActivity.class);
        intent.putExtra(Constant.EXTRA_MOVIE_ID, movie.getId());
        startActivity(intent);
    }

    @Override
    public String getCategoryMovie() {
        return getIntent().getStringExtra(Constant.EXTRA_CATEGORY);
    }

    @Override
    public void onListMoreMoviesSuccess(List<Movie> movieList) {
        if (movieList == null) {
            return;
        }
        mAdapter.updateData(movieList);
    }

    @Override
    public void onListMovieSuccess(List<Movie> movieList) {
        if (movieList == null) {
            return;
        }
        mAdapter.updateData(movieList);
    }
}
