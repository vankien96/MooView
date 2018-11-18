package com.vankien96.mooview.screen.moremovies;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vankien96.mooview.data.model.Film;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.data.service.response.GetListMoviesResponse;
import com.vankien96.mooview.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Listens to user actions from the UI ({@link MoreMoviesActivity}), retrieves the data and updates
 * the UI as required.
 */
final class MoreMoviesPresenter implements MoreMoviesContract.Presenter {
    private static final String TAG = MoreMoviesPresenter.class.getName();

    private MoreMoviesContract.MoreMoviesView mMoreMoviesView;
    private MoviesApi mMoviesApi;

    MoreMoviesPresenter() {
    }

    @Override
    public void setView(MoreMoviesContract.MoreMoviesView moreMoviesView) {
        mMoreMoviesView = moreMoviesView;
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
    public void getListMoreMovie(int currentPage) {
        String category = mMoreMoviesView.getCategoryMovie();
        mMoviesApi.getListMovies(category, Constant.API_KEY, Constant.LANGUAGE, currentPage)
                .enqueue(new Callback<GetListMoviesResponse>() {
                    @Override
                    public void onResponse(Call<GetListMoviesResponse> call,
                            Response<GetListMoviesResponse> response) {
                        if (response.body() == null || response.body().getResults() == null) {
                            return;
                        }
                        mMoreMoviesView.onListMoreMoviesSuccess(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<GetListMoviesResponse> call, Throwable t) {
                        Log.e(TAG, "onListMoreMoviesFailure: ", t);
                    }
                });
    }

    @Override
    public void getListMoviesByCategory() {
        String category = mMoreMoviesView.getCategoryMovie();
        if(category.equals(Constant.CATEGORY_NOW_PLAYING)) {
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
                    mMoreMoviesView.onListMovieSuccess(list);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
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
                    mMoreMoviesView.onListMovieSuccess(list);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
