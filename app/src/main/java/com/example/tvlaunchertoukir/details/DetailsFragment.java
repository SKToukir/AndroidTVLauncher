package com.example.tvlaunchertoukir.details;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.tvlaunchertoukir.model.MediaModel;

public class DetailsFragment extends android.support.v17.leanback.app.DetailsFragment {


    private Context mContext;
    private MediaModel mediaModel;
    private DisplayMetrics mMetricks;
    private BackgroundManager mBackgroundManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        mediaModel =((Activity) mContext).getIntent().getParcelableExtra(DetailsActivity.KEY_MOVIE);

        prepareBackgoundManager();
        buildDetails(mediaModel);
    }

    private void buildDetails(MediaModel mediaModel) {
        updateBackground(mediaModel);
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
}
