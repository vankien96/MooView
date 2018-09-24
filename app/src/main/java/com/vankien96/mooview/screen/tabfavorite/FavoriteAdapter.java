package com.vankien96.mooview.screen.tabfavorite;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.vankien96.mooview.MainApplication;
import com.vankien96.mooview.R;
import com.vankien96.mooview.data.local.DeleteMovieFromDatabase;
import com.vankien96.mooview.data.local.MovieDatabase;
import com.vankien96.mooview.data.local.OnDeleteDataListener;
import com.vankien96.mooview.data.local.entity.MovieEntity;
import com.vankien96.mooview.utils.Constant;
import com.vankien96.mooview.utils.StringUtils;
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
 * Created by Admin on 19/12/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    private List<MovieEntity> mMovieEntityList = new ArrayList<>();
    private OnFavoriteItemListener mOnFavoriteItemListener;
    private Context mContext;
    private LayoutInflater mInflater;
    private FavoriteAdapterCallback mFavoriteAdapterCallback;

    public FavoriteAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.movie_favorite_item_layout, parent, false);
        return new FavoriteHolder(itemView, mOnFavoriteItemListener, mContext,
                mFavoriteAdapterCallback);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteHolder holder, int position) {
        holder.bind(mMovieEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieEntityList.size();
    }

    public void setOnFavoriteItemListener(OnFavoriteItemListener onFavoriteItemListener) {
        mOnFavoriteItemListener = onFavoriteItemListener;
    }

    public void setFavoriteAdapterCallback(FavoriteAdapterCallback callback) {
        mFavoriteAdapterCallback = callback;
    }

    public void updateData(List<MovieEntity> movieEntityList) {
        if (movieEntityList == null) {
            return;
        }
        mMovieEntityList.clear();
        mMovieEntityList.addAll(movieEntityList);
        notifyDataSetChanged();
    }

    //Favorite Holder
    class FavoriteHolder extends RecyclerView.ViewHolder implements OnDeleteDataListener {
        private static final String TAG = "FavoriteHolder";
        private static final String USER_ID = "id";
        private static final String MOVIE_ID = "id";
        private static final String USERS_NODE = "Users";
        private MovieEntity mMovieEntity;
        private ImageView mFavoritePoster;
        private TextView mFavoriteMovieName;
        private ImageView mImageFavoriteMovie;
        private Context mContext;
        private OnFavoriteItemListener mOnFavoriteItemListener;
        private SharedPreferences mSharedPreferences;
        private DatabaseReference mDatabaseRef;
        private MovieDatabase mMovieDatabase;
        private boolean mIsLogin = false;
        private FavoriteAdapterCallback mCallback;

        FavoriteHolder(View itemView, OnFavoriteItemListener onFavoriteItemListener,
                Context context, FavoriteAdapterCallback callback) {
            super(itemView);
            mContext = context;
            mFavoritePoster = itemView.findViewById(R.id.image_poster_item);
            mFavoriteMovieName = itemView.findViewById(R.id.text_name_item);
            mImageFavoriteMovie = itemView.findViewById(R.id.image_favorite_item);
            mOnFavoriteItemListener = onFavoriteItemListener;
            mMovieDatabase = MainApplication.getMovieDatabase();
            mSharedPreferences =
                    mContext.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference();
            mCallback = callback;
            initDatabaseReference();

            mImageFavoriteMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRemoveFavoriteMovie(mMovieEntity);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnFavoriteItemListener.onClickItem(mMovieEntity);
                }
            });
        }

        public void bind(MovieEntity movieEntity) {
            mMovieEntity = movieEntity;
            String urlPoster =
                    StringUtils.convertPosterPathToUrlPoster(movieEntity.getPosterPath());
            String titleMovie = StringUtils.convertLongTitleToShortTitle(movieEntity.getTitle(),
                    movieEntity.getReleaseDate());
            Glide.with(itemView.getContext()).load(urlPoster).into(mFavoritePoster);
            mFavoriteMovieName.setText(titleMovie);
            mImageFavoriteMovie.setImageResource(R.drawable.ic_favorite);
        }

        private void initDatabaseReference() {
            String dataUser = mSharedPreferences.getString(Constant.PREF_USER, Constant.DEFAULT);
            if (!dataUser.equals(Constant.DEFAULT)) {
                mIsLogin = true;
                try {
                    JSONObject mDataUser = new JSONObject(dataUser);
                    mDataUser.getString(USER_ID);
                    mDatabaseRef =
                            mDatabaseRef.child(USERS_NODE).child(mDataUser.getString(USER_ID));
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException: ", e);
                }
            }
        }

        private void onRemoveFavoriteMovie(MovieEntity movieEntity) {
            int position = mMovieEntityList.indexOf(movieEntity);
            mImageFavoriteMovie.setImageResource(R.drawable.ic_favorite_border_small);
            new DeleteMovieFromDatabase(mMovieDatabase, this).execute(movieEntity);

            if (mIsLogin) {
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
            mMovieEntityList.remove(position);
            notifyItemRemoved(position);
            if (mMovieEntityList.size() == 0) {
                mCallback.onRemoveAllMovies();
            }
        }

        @Override
        public void onDeleteDataSuccess() {
            Toast.makeText(mContext,
                    mContext.getResources().getString(R.string.remove_favorite_movie),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
