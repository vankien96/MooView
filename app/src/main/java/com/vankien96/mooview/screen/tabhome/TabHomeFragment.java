package com.vankien96.mooview.screen.tabhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.vankien96.mooview.MainApplication;
import com.vankien96.mooview.R;
import com.vankien96.mooview.data.datasource.AdsDataSoure;
import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.screen.BaseFragment;
import com.vankien96.mooview.screen.OnRecyclerViewItemListener;
import com.vankien96.mooview.screen.detailsmovie.DetailsMovieActivity;
import com.vankien96.mooview.screen.moremovies.MoreMoviesActivity;
import com.vankien96.mooview.utils.Constant;
import com.vankien96.mooview.widget.AutoScrollViewPager;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;

/**
 * TabHome Screen.
 */
public class TabHomeFragment extends BaseFragment
        implements TabHomeContract.HomeView, OnRecyclerViewItemListener, View.OnClickListener {

    TabHomeContract.Presenter mPresenter;
    private RecyclerView mRecyclerNowPlaying, mRecyclerUpComing, mRecyclerTopRated,
            mRecyclerPopular;
    private MovieAdapter mAdapterNowPlaying, mAdapterUpComing, mAdapterTopRated, mAdapterPopular;
    private Button mButtonMoreNowPlaying, mButtonMoreUpComing, mButtonMoreTopRated,
            mButtonMorePopular;
    private RecyclerView.LayoutManager mLayoutManagerNowPlaying, mLayoutManagerUpComing,
            mLayoutManagerTopRated, mLayoutManagerPopular;
    private AutoScrollViewPager mAutoScrollViewPager;
    private AdsViewPagerAdapter mPagerAdapter;
    private CircleIndicator mCircleIndicator;

    public static TabHomeFragment newInstance() {
        return new TabHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mPresenter = new TabHomePresenter();
//        mPresenter.setView(this);
//        MoviesApi moviesApi = MainApplication.getMoviesApi();
//        mPresenter.setMoviesApi(moviesApi);
//
//        mPresenter.getListNowPlayingMovie();
//        mPresenter.getListUpComingMovie();
//        mPresenter.getListTopRatedMovie();
//        mPresenter.getListPopularMovie();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabhome, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
//        mRecyclerNowPlaying = view.findViewById(R.id.recycler_now_playing);
//        mRecyclerUpComing = view.findViewById(R.id.recycler_up_coming);
//        mRecyclerTopRated = view.findViewById(R.id.recycler_top_rated);
//        mRecyclerPopular = view.findViewById(R.id.recycler_popuplar);
//
//        mAutoScrollViewPager = view.findViewById(R.id.view_pager_ads);
//        mAutoScrollViewPager.startAutoScroll();
//        mAutoScrollViewPager.setInterval(4000);
//        mAutoScrollViewPager.setStopScrollWhenTouch(true);
//
//        mPagerAdapter = new AdsViewPagerAdapter(getActivity(), AdsDataSoure.getListAds());
//        mAutoScrollViewPager.setAdapter(mPagerAdapter);
//
//        mCircleIndicator = view.findViewById(R.id.circle_indicator);
//        mCircleIndicator.setViewPager(mAutoScrollViewPager);
//
//        mLayoutManagerNowPlaying =
//                new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
//        mLayoutManagerUpComing =
//                new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
//        mLayoutManagerTopRated =
//                new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
//        mLayoutManagerPopular =
//                new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
//
//        mAdapterNowPlaying = new MovieAdapter(getActivity());
//        mAdapterNowPlaying.setOnRecyclerViewItemListener(this);
//        mAdapterUpComing = new MovieAdapter(getActivity());
//        mAdapterUpComing.setOnRecyclerViewItemListener(this);
//        mAdapterTopRated = new MovieAdapter(getActivity());
//        mAdapterTopRated.setOnRecyclerViewItemListener(this);
//        mAdapterPopular = new MovieAdapter(getActivity());
//        mAdapterPopular.setOnRecyclerViewItemListener(this);
//
//        mRecyclerNowPlaying.setLayoutManager(mLayoutManagerNowPlaying);
//        mRecyclerNowPlaying.setAdapter(mAdapterNowPlaying);
//        mRecyclerNowPlaying.setHasFixedSize(true);
//        mRecyclerUpComing.setLayoutManager(mLayoutManagerUpComing);
//        mRecyclerUpComing.setAdapter(mAdapterUpComing);
//        mRecyclerUpComing.setHasFixedSize(true);
//        mRecyclerTopRated.setLayoutManager(mLayoutManagerTopRated);
//        mRecyclerTopRated.setAdapter(mAdapterTopRated);
//        mRecyclerTopRated.setHasFixedSize(true);
//        mRecyclerPopular.setLayoutManager(mLayoutManagerPopular);
//        mRecyclerPopular.setAdapter(mAdapterPopular);
//        mRecyclerPopular.setHasFixedSize(true);
//
//        mButtonMoreNowPlaying = view.findViewById(R.id.button_more_now_playing);
//        mButtonMoreUpComing = view.findViewById(R.id.button_more_up_coming);
//        mButtonMoreTopRated = view.findViewById(R.id.button_more_top_rated);
//        mButtonMorePopular = view.findViewById(R.id.button_more_popular);
//
//        mButtonMoreNowPlaying.setOnClickListener(this);
//        mButtonMoreUpComing.setOnClickListener(this);
//        mButtonMoreTopRated.setOnClickListener(this);
//        mButtonMorePopular.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //mPresenter.onStart();
    }

    @Override
    public void onStop() {
        //mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailsMovieActivity.class);
        intent.putExtra(Constant.EXTRA_MOVIE_ID, movie.getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.button_more_now_playing:
//                openMoreMoviesActivity(Constant.CATEGORY_NOW_PLAYING);
//                break;
//            case R.id.button_more_up_coming:
//                openMoreMoviesActivity(Constant.CATEGORY_UPCOMING);
//                break;
//            case R.id.button_more_top_rated:
//                openMoreMoviesActivity(Constant.CATEGORY_TOP_RATED);
//                break;
//            case R.id.button_more_popular:
//                openMoreMoviesActivity(Constant.CATEGORY_POPULAR);
//                break;
//            default:
//                break;
//        }
    }

    private void openMoreMoviesActivity(String category) {
        Intent intent = new Intent(getActivity(), MoreMoviesActivity.class);
        intent.putExtra(Constant.EXTRA_CATEGORY, category);
        startActivity(intent);
    }

    @Override
    public void onListNowPlayingMovieSuccess(List<Movie> listNowPlayingMovie) {
        if (listNowPlayingMovie == null) {
            return;
        }
        mAdapterNowPlaying.updateData(listNowPlayingMovie);
    }

    @Override
    public void onListUpComingMovieSuccess(List<Movie> listUpComingMovie) {
        if (listUpComingMovie == null) {
            return;
        }
        mAdapterUpComing.updateData(listUpComingMovie);
    }

    @Override
    public void onListTopRatedMovieSuccess(List<Movie> listTopRatedMovie) {
        if (listTopRatedMovie == null) {
            return;
        }
        mAdapterTopRated.updateData(listTopRatedMovie);
    }

    @Override
    public void onListPopularMoviesSuccess(List<Movie> listPopularMovie) {
        if (listPopularMovie == null) {
            return;
        }
        mAdapterPopular.updateData(listPopularMovie);
    }
}
