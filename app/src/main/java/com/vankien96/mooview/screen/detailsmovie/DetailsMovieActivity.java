package com.vankien96.mooview.screen.detailsmovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.vankien96.mooview.MainApplication;
import com.vankien96.mooview.R;
import com.vankien96.mooview.data.local.DeleteMovieFromDatabase;
import com.vankien96.mooview.data.local.GetMovieFromDatabase;
import com.vankien96.mooview.data.local.InsertMovieToDatabase;
import com.vankien96.mooview.data.local.MovieDatabase;
import com.vankien96.mooview.data.local.OnDeleteDataListener;
import com.vankien96.mooview.data.local.OnInsertDataListener;
import com.vankien96.mooview.data.local.entity.MovieEntity;
import com.vankien96.mooview.data.model.Cast;
import com.vankien96.mooview.data.model.Movie;
import com.vankien96.mooview.data.model.Trailer;
import com.vankien96.mooview.data.service.config.MoviesApi;
import com.vankien96.mooview.screen.BaseActivity;
import com.vankien96.mooview.utils.Constant;
import com.vankien96.mooview.utils.DateTimeUtils;
import com.vankien96.mooview.utils.StringUtils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * DetailsMovie Screen.
 */
public class DetailsMovieActivity extends BaseActivity
        implements DetailsMovieContract.DetailsMovieView, View.OnClickListener,
        YouTubePlayer.OnInitializedListener, YouTubeThumbnailView.OnInitializedListener,
        OnDeleteDataListener, OnInsertDataListener {
    private static final String TAG = "DetailsMovieActivity";
    public static final String DATE_FORMATT_DD_MM_YYYY = "yyyy-MM-dd";
    public static final String DATE_FORMATT_DD_MMM_YYYY = "dd MMM yyyy";
    private static final String MINUTE = " min";
    private static final String MAX_POINT = "/10";
    private static final String OFFICIAL = "Official";
    private static final String USER_ID = "id";
    private static final String MOVIE_ID = "id";
    private static final int FIRST_TRAILER = 0;
    private static final String USERS_NODE = "Users";
    private ImageView mImageBackdrop, mImagePoster;
    private TextView mTextReleaseDate, mTextRunTime, mTextGenreMovie, mTextOverview, mTextRateMovie;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingFavoriteButton;
    private MovieDatabase mMovieDatabase;
    private boolean isFavorite = false;
    private RelativeLayout mRelativeLayout;
    private YouTubePlayerFragment mYouTubePlayer;
    private YouTubePlayer.OnInitializedListener mOnPlayerInitListener;
    private YouTubeThumbnailView mThumbnailView;
    private RecyclerView mRecyclerCast;
    private RecyclerView.LayoutManager mLayoutManager;
    private CastAdapter mAdapter;
    DetailsMovieContract.Presenter mPresenter;
    private String mYoutubeKey;
    private String mTitleMovie;
    private String mPosterPath;
    private String mReleaseDate;
    private SharedPreferences mSharedPreferences;
    private DatabaseReference mDatabaseRef;
    private boolean mIsLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsmovie);

        mPresenter = new DetailsMoviePresenter();
        mPresenter.setView(this);
        MoviesApi moviesApi = MainApplication.getMoviesApi();
        mMovieDatabase = MainApplication.getMovieDatabase();
        mPresenter.setMovieApi(moviesApi);
        mPresenter.getDetailsMovie();
        mPresenter.getMovieTrailers();
        mPresenter.getCastsMovie();

        initViews();
        setHomeButtonToolbar();
        initDatabaseReference();
    }

    private void initViews() {
        mImageBackdrop = findViewById(R.id.image_backdrop);
        mImagePoster = findViewById(R.id.image_poster);
        mTextReleaseDate = findViewById(R.id.text_release_date);
        mTextRunTime = findViewById(R.id.text_run_time);
        mTextGenreMovie = findViewById(R.id.text_kind_movie);
        mTextOverview = findViewById(R.id.text_describe_movie);
        mTextRateMovie = findViewById(R.id.text_rate);
        mFloatingFavoriteButton = findViewById(R.id.floating_favorite);
        mRelativeLayout = findViewById(R.id.relative_play);
        mRelativeLayout.setOnClickListener(this);
        mThumbnailView = findViewById(R.id.youtube_thumnail);

        mYouTubePlayer = YouTubePlayerFragment.newInstance();
        mYouTubePlayer.initialize(Constant.GOOGLE_API_KEY, mOnPlayerInitListener);
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_fragment, mYouTubePlayer)
                .commit();

        mFloatingFavoriteButton.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.colorOrange)));
        if (isFavoriteMovie()) {
            mFloatingFavoriteButton.setImageResource(R.drawable.ic_action_favorite);
            isFavorite = !isFavorite;
        } else {
            mFloatingFavoriteButton.setImageResource(R.drawable.ic_favorite_border);
        }

        mCollapsingToolbarLayout = findViewById(R.id.collapsing_detail_movie);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mToolbar = findViewById(R.id.toolbar_detail_movie);

        mRecyclerCast = findViewById(R.id.recycler_cast);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new CastAdapter(this);
        mRecyclerCast.setLayoutManager(mLayoutManager);
        mRecyclerCast.setAdapter(mAdapter);

        mSharedPreferences = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
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

    private boolean isFavoriteMovie() {
        int isLike;
        try {
            isLike = new GetMovieFromDatabase(mMovieDatabase).execute(getMovieId()).get();
            if (isLike > 0) {
                return true;
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "isFavoriteMovie: Ops! ", e);
        } catch (ExecutionException e) {
            Log.e(TAG, "isFavoriteMovie: Ops! ", e);
        }
        return false;
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
    public Integer getMovieId() {
        return getIntent().getIntExtra(Constant.EXTRA_MOVIE_ID, 0);
    }

    @Override
    public void onDetailsMovieSuccess(Movie movie, List<String> listGenre) {
        String urlBackdrop = StringUtils.convertPosterPathToUrlPoster(movie.getBackdropPath());
        String urlPoster = StringUtils.convertPosterPathToUrlPoster(movie.getPosterPath());
        String genresCommaSeparated = StringUtils.convertListToStringCommaSeparated(listGenre);
        String rateMovie = movie.getVoteAverage() + MAX_POINT;
        String runTimeMovie = movie.getRuntime() + MINUTE;
        Date date =
                DateTimeUtils.convertStringToDate(movie.getReleaseDate(), DATE_FORMATT_DD_MM_YYYY);
        String releaseDate = DateTimeUtils.getStrDateTimeFormatted(date, DATE_FORMATT_DD_MMM_YYYY);

        mTitleMovie = movie.getTitle();
        mPosterPath = movie.getPosterPath();
        mReleaseDate = movie.getReleaseDate();

        Glide.with(this).load(urlBackdrop).into(mImageBackdrop);
        Glide.with(this).load(urlPoster).into(mImagePoster);
        mTextReleaseDate.setText(releaseDate);
        mTextGenreMovie.setText(genresCommaSeparated);
        mTextRateMovie.setText(rateMovie);
        mTextRunTime.setText(runTimeMovie);
        mTextOverview.setText(movie.getOverview());
        mCollapsingToolbarLayout.setTitle(movie.getTitle());
        mFloatingFavoriteButton.setOnClickListener(this);
    }

    @Override
    public void onListTrailerSuccess(List<Trailer> listTrailer) {
        if (listTrailer.size() == 0) {
            mRelativeLayout.setVisibility(View.INVISIBLE);
            mRelativeLayout.setVisibility(View.GONE);
        }
        for (Trailer trailer : listTrailer) {
            if (trailer.getName().contains(OFFICIAL)) {
                mYoutubeKey = trailer.getKey();
                break;
            } else {
                mYoutubeKey = listTrailer.get(FIRST_TRAILER).getKey();
            }
        }
        mThumbnailView.initialize(Constant.GOOGLE_API_KEY, this);
    }

    @Override
    public void onListCastMovieSuccess(List<Cast> listCast) {
        if (listCast.size() == 0) {
            return;
        }
        mAdapter.updateData(listCast);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating_favorite:
                if (!isFavorite) {
                    mFloatingFavoriteButton.setImageResource(R.drawable.ic_action_favorite);
                    MovieEntity movieEntity = new MovieEntity();
                    movieEntity.setId(getMovieId());
                    movieEntity.setTitle(mTitleMovie);
                    movieEntity.setPosterPath(mPosterPath);
                    movieEntity.setReleaseDate(mReleaseDate);
                    new InsertMovieToDatabase(mMovieDatabase, this).execute(movieEntity);

                    if (mIsLogin) {
                        mDatabaseRef.push().setValue(movieEntity);
                    }
                } else {
                    mFloatingFavoriteButton.setImageResource(R.drawable.ic_favorite_border);
                    MovieEntity movieEntity = new MovieEntity();
                    movieEntity.setId(getMovieId());
                    new DeleteMovieFromDatabase(mMovieDatabase, this).execute(movieEntity);

                    mDatabaseRef.orderByChild(MOVIE_ID)
                            .equalTo(movieEntity.getId())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        child.getRef().setValue(null);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e(TAG, "onCancelled: " + databaseError);
                                }
                            });
                }
                isFavorite = !isFavorite;
                break;
            case R.id.relative_play:
                mRelativeLayout.setVisibility(View.INVISIBLE);
                mRelativeLayout.setVisibility(View.GONE);
                mYouTubePlayer.initialize(Constant.GOOGLE_API_KEY, this);
                break;
            default:
                break;
        }
    }

    //Initialize YoutubePlayer
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
            YouTubePlayer youTubePlayer, boolean b) {
        mThumbnailView.setVisibility(View.GONE);
        youTubePlayer.loadVideo(mYoutubeKey);
        youTubePlayer.setShowFullscreenButton(false);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
            YouTubeInitializationResult youTubeInitializationResult) {
        Log.e(TAG, "onInitializationFailure: ");
    }

    //Initialize YoutubeThumnailView
    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
            final YouTubeThumbnailLoader youTubeThumbnailLoader) {
        youTubeThumbnailLoader.setVideo(mYoutubeKey);
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(
                new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView,
                            String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView,
                            YouTubeThumbnailLoader.ErrorReason errorReason) {
                        Log.e(TAG, "onThumbnailError: ");
                    }
                });
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView,
            YouTubeInitializationResult youTubeInitializationResult) {
        Log.e(TAG, "onInitializationFailure: ");
    }

    @Override
    public void onDeleteDataSuccess() {
        Toast.makeText(this, getResources().getString(R.string.remove_favorite_movie),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInsertDataSuccess() {
        Toast.makeText(this, getResources().getString(R.string.add_favorite_movie),
                Toast.LENGTH_SHORT).show();
    }
}
