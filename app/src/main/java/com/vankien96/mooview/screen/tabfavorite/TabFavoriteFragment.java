package com.vankien96.mooview.screen.tabfavorite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.vankien96.mooview.MainApplication;
import com.vankien96.mooview.R;
import com.vankien96.mooview.data.local.InsertListMovieToDatabase;
import com.vankien96.mooview.data.local.MovieDatabase;
import com.vankien96.mooview.data.local.OnSelectDataListener;
import com.vankien96.mooview.data.local.SelectListMovieFromDatabase;
import com.vankien96.mooview.data.local.entity.MovieEntity;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.screen.BaseFragment;
import com.vankien96.mooview.screen.detailsmovie.DetailsMovieActivity;
import com.vankien96.mooview.utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * TabFavorite Screen.
 */
public class TabFavoriteFragment extends BaseFragment
        implements TabFavoriteContract.FavoriteView, OnFavoriteItemListener, OnSelectDataListener,
        FavoriteAdapterCallback {

    private static final String TAG = "TabFavoriteFragment";
    private static final String USER_ID = "id";
    private static final String USERS_NODE = "Users";
    private static final int NUMBER_COLUMNS = 2;
    TabFavoriteContract.Presenter mPresenter;
    private MovieDatabase mMovieDatabase;
    private RecyclerView mRecyclerView;
    private FavoriteAdapter mFavoriteAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabaseRef;
    private SharedPreferences mSharedPreferences;
    private boolean mIsLogin = false;
    private LinearLayout mLinearLayout;
    private List<MovieEntity> mListMovieFirebase = new ArrayList<>();

    public static TabFavoriteFragment newInstance() {
        return new TabFavoriteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new TabFavoritePresenter();
        mPresenter.setView(this);
        MoviesApi moviesApi = MainApplication.getMoviesApi();
        mPresenter.setMovieApi(moviesApi);

        mMovieDatabase = MainApplication.getMovieDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabfavorite, container, false);
        initView(view);
        initDatabaseReference();
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_fravorite);
        mLayoutManager = new GridLayoutManager(getActivity(), NUMBER_COLUMNS);
        mFavoriteAdapter = new FavoriteAdapter(getActivity());
        mFavoriteAdapter.setOnFavoriteItemListener(this);
        mFavoriteAdapter.setFavoriteAdapterCallback(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mFavoriteAdapter);
        mLinearLayout = view.findViewById(R.id.linear_favorite_not_found);

        mSharedPreferences =
                getActivity().getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
    }

    private void initDatabaseReference() {
        String dataUser = mSharedPreferences.getString(Constant.PREF_USER, Constant.DEFAULT);
        if (!dataUser.equals(Constant.DEFAULT)) {
            mIsLogin = true;
            mDatabaseRef = FirebaseDatabase.getInstance().getReference();
            try {
                JSONObject mDataUser = new JSONObject(dataUser);
                mDataUser.getString(USER_ID);
                mDatabaseRef = mDatabaseRef.child(USERS_NODE).child(mDataUser.getString(USER_ID));
            } catch (JSONException e) {
                Log.e(TAG, "JSONException: ", e);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        new SelectListMovieFromDatabase(mMovieDatabase, this).execute();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            new SelectListMovieFromDatabase(mMovieDatabase, this).execute();
        }
    }

    @Override
    public void onSelectDataSuccess(List<MovieEntity> movieEntityList) {
        if (movieEntityList == null) {
            return;
        }
        if (mIsLogin) {
            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mListMovieFirebase.clear();
                    for (DataSnapshot movieSnapshot : dataSnapshot.getChildren()) {
                        MovieEntity movieEntity = movieSnapshot.getValue(MovieEntity.class);
                        mListMovieFirebase.add(movieEntity);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            if (mListMovieFirebase.size() > movieEntityList.size()) {
                new InsertListMovieToDatabase(mMovieDatabase).execute(mListMovieFirebase);
                movieEntityList.clear();
                movieEntityList.addAll(mListMovieFirebase);
            }
        }
        if (movieEntityList.size() > 0) {
            invisibleNoFavoredLayout();
        } else {
            invisibleRecyclerView();
        }
        mFavoriteAdapter.updateData(movieEntityList);
    }

    @Override
    public void onClickItem(MovieEntity movieEntity) {
        Intent intent = new Intent(getActivity(), DetailsMovieActivity.class);
        intent.putExtra(Constant.EXTRA_MOVIE_ID, movieEntity.getId());
        startActivity(intent);
    }

    public void invisibleRecyclerView() {
        mLinearLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    public void invisibleNoFavoredLayout() {
        mLinearLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRemoveAllMovies() {
        invisibleRecyclerView();
    }
}
