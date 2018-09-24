package com.vankien96.mooview.screen;

/**
 * Created by Admin on 12/12/2017.
 */

public interface BasePresenter<T> {
    void setView(T view);
    void onStart();
    void onStop();
}
