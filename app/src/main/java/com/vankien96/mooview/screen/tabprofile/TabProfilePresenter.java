package com.vankien96.mooview.screen.tabprofile;

/**
 * Listens to user actions from the UI ({@link TabProfileFragment}), retrieves the data and updates
 * the UI as required.
 */
final class TabProfilePresenter implements TabProfileContract.Presenter {
    private static final String TAG = TabProfilePresenter.class.getName();

     TabProfileContract.ProfileView mProfileView;

     TabProfilePresenter() {
    }

    @Override
    public void setView(TabProfileContract.ProfileView view) {
        mProfileView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
