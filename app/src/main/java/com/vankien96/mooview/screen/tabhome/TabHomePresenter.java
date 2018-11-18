package com.vankien96.mooview.screen.tabhome;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vankien96.mooview.data.model.Film;
import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.data.service.response.GetListMoviesResponse;
import com.vankien96.mooview.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Listens to user actions from the UI ({@link TabHomeFragment}), retrieves the data and updates
 * the UI as required.
 */
final class TabHomePresenter implements TabHomeContract.Presenter {
    public static final String TAG = TabHomePresenter.class.getName();
    private MoviesApi mMoviesApi;

    private TabHomeContract.HomeView mHomeView;

    TabHomePresenter() {
    }

    @Override
    public void setView(TabHomeContract.HomeView view) {
        mHomeView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setMoviesApi(MoviesApi moviesApi) {
        mMoviesApi = moviesApi;
    }

    @Override
    public void getListNowPlayingMovie() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("CurrentFilm");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Film> list = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Film film = new Film();
                    film.setId(snapshot.getKey());
                    film.setName(snapshot.child("name").getValue().toString());
                    film.setPoster(snapshot.child("poster").getValue().toString().trim());
                    film.setActors(snapshot.child("actor").getValue().toString());
                    film.setDirector(snapshot.child("director").getValue().toString());
                    film.setDate(snapshot.child("date").getValue().toString());
                    film.setTime(snapshot.child("time").getValue().toString());
                    Double score = (Double) snapshot.child("point").getValue();
                    film.setScore(score);
                    film.setDescription(snapshot.child("description").getValue().toString());
                    film.setTrailer(snapshot.child("trailer").getValue().toString());
                    list.add(film);
                }
                mHomeView.onListNowPlayingMovieSuccess(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getListUpComingMovie() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("UpcomingFilm");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Film> list = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Film film = new Film();
                    film.setId(snapshot.getKey());
                    film.setName(snapshot.child("name").getValue().toString());
                    film.setPoster(snapshot.child("poster").getValue().toString().trim());
                    film.setActors(snapshot.child("actor").getValue().toString());
                    film.setDirector(snapshot.child("director").getValue().toString());
                    film.setDate(snapshot.child("date").getValue().toString());
                    film.setDescription(snapshot.child("description").getValue().toString());
                    list.add(film);
                }
                mHomeView.onListUpComingMovieSuccess(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getListTopRatedMovie() {
        mMoviesApi.getListMovies(Constant.CATEGORY_TOP_RATED, Constant.API_KEY, Constant.LANGUAGE,
                Constant.DEFAULT_PAGE).enqueue(new Callback<GetListMoviesResponse>() {
            @Override
            public void onResponse(Call<GetListMoviesResponse> call,
                    Response<GetListMoviesResponse> response) {
                if (response.body() == null || response.body().getResults() == null) {
                    return;
                }
                mHomeView.onListTopRatedMovieSuccess(response.body().getResults());
            }

            @Override
            public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                Log.e(TAG, "onGetListTopRatedMovieFailure: ", t);
            }
        });
    }

    @Override
    public void getListPopularMovie() {
        mMoviesApi.getListMovies(Constant.CATEGORY_POPULAR, Constant.API_KEY, Constant.LANGUAGE,
                Constant.DEFAULT_PAGE).enqueue(new Callback<GetListMoviesResponse>() {
            @Override
            public void onResponse(Call<GetListMoviesResponse> call,
                    Response<GetListMoviesResponse> response) {
                if (response.body() == null || response.body().getResults() == null) {
                    return;
                }
                mHomeView.onListPopularMoviesSuccess(response.body().getResults());
            }

            @Override
            public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                Log.e(TAG, "onGetListPopularMovieFailure: ", t);
            }
        });
    }
}
