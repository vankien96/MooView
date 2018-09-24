package com.vankien96.mooview.data.local;

import com.vankien96.mooview.data.local.entity.MovieEntity;
import java.util.List;

/**
 * Created by Admin on 19/12/2017.
 */

public interface OnSelectDataListener {
    void onSelectDataSuccess(List<MovieEntity> movieEntityList);
}
