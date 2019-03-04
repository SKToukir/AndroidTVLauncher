package com.example.tvlaunchertoukir.details;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.tvlaunchertoukir.model.MediaModel;
import com.example.tvlaunchertoukir.presenter.MediaDetailsDescriptiorPresenter;

public class DetailsFragment extends android.support.v17.leanback.app.DetailsFragment {


    private Context mContext;
    private MediaModel mediaModel;
    private DisplayMetrics mMetricks;
    private ArrayObjectAdapter mArrayObjAdapter;
    private BackgroundManager mBackgroundManager;
    private static final int ACTION_WATCH_TRAILER = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        mediaModel =((Activity) mContext).getIntent().getParcelableExtra(DetailsActivity.KEY_MOVIE);

        prepareBackgoundManager();
        buildDetails(mediaModel);
    }

    private void buildDetails(MediaModel mediaModel) {
        ClassPresenterSelector selector = new ClassPresenterSelector();
        FullWidthDetailsOverviewRowPresenter rowPresenter = new FullWidthDetailsOverviewRowPresenter(
                new MediaDetailsDescriptiorPresenter(),
                new DetailsOverviewLogoPresenter());

        selector.addClassPresenter(DetailsOverviewRow.class,rowPresenter);
        selector.addClassPresenter(ListRow.class,new ListRowPresenter());
        mArrayObjAdapter = new ArrayObjectAdapter(selector);

        final DetailsOverviewRow detailsOverviewRow = new DetailsOverviewRow(mediaModel);

        Glide.with(getActivity())
                .load(mediaModel.getImageUrl())
                .asBitmap()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        detailsOverviewRow.setImageBitmap(getActivity(),resource);
                        return true;
                    }
                }).into(-1,-1);

        updateBackground(mediaModel);

        SparseArrayObjectAdapter adapter = new SparseArrayObjectAdapter();
        if (!mediaModel.getVideoUrl().isEmpty()){
            adapter.set(ACTION_WATCH_TRAILER,new Action(ACTION_WATCH_TRAILER,"Label"));
        }
        detailsOverviewRow.setActionsAdapter(adapter);
        mArrayObjAdapter.add(detailsOverviewRow);

        setAdapter(mArrayObjAdapter);


    }

    private void updateBackground(MediaModel mediaModel) {
        Glide.with(getActivity())
                .load(mediaModel.getImageUrl())
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>(mMetricks.widthPixels,mMetricks.heightPixels) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mBackgroundManager.setBitmap(resource);
                    }
                });
    }

    private void prepareBackgoundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mMetricks = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetricks);
    }

    @Override
    public void onStop() {
        mBackgroundManager.release();
        super.onStop();
    }
}
