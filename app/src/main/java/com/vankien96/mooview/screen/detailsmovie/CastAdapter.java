package com.vankien96.mooview.screen.detailsmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.vankien96.mooview.R;
import com.vankien96.mooview.data.model.Cast;
import com.vankien96.mooview.screen.OnRecyclerViewItemListener;
import com.vankien96.mooview.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/20/17.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {

    private List<Cast> mCastList = new ArrayList<>();
    private OnRecyclerViewItemListener mOnRecyclerViewItemListener;
    private Context mContext;
    private LayoutInflater mInflater;

    public CastAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.cast_item, parent, false);
        return new CastHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CastHolder holder, int position) {
        holder.bind(mCastList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCastList.size();
    }

    public void setOnRecyclerViewItemListener(
            OnRecyclerViewItemListener onRecyclerViewItemListener) {
        mOnRecyclerViewItemListener = onRecyclerViewItemListener;
    }

    public void updateData(List<Cast> castList) {
        if (castList == null) {
            return;
        }
        mCastList.clear();
        mCastList.addAll(castList);
        notifyDataSetChanged();
    }

    static class CastHolder extends RecyclerView.ViewHolder {

        private ImageView mImageCast;
        private TextView mTextNameCast;
        private OnRecyclerViewItemListener mOnRecyclerViewItemListener;

        CastHolder(View itemView) {
            super(itemView);
            mImageCast = itemView.findViewById(R.id.image_cast);
            mTextNameCast = itemView.findViewById(R.id.text_name_cast);
        }

        public void bind(Cast cast) {
            String urlImageCast = StringUtils.convertPosterPathToUrlPoster(cast.getProfilePath());
            String nameCast = StringUtils.convertLongNameToShortName(cast.getName());

            Glide.with(itemView.getContext()).load(urlImageCast).into(mImageCast);
            mTextNameCast.setText(nameCast);
        }
    }
}
