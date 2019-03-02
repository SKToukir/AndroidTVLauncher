package com.example.tvlaunchertoukir.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.Presenter;
import android.view.View;
import android.view.ViewGroup;

import com.example.tvlaunchertoukir.R;
import com.example.tvlaunchertoukir.model.VideoModel;
import com.hitherejoe.leanbackcards.LiveCardView;

public class VideoPresenter extends Presenter {

    private int CARD_WIDTH = 313;
    private int CARD_HEIGHT = 176;
    private Context mContext;
    private Drawable mDefaultDrawable;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        mDefaultDrawable = mContext.getResources().getDrawable(R.drawable.hdback);

        final LiveCardView liveCardView = new LiveCardView(mContext){
            @Override
            public void setSelected(boolean selected) {
                int selected_color = mContext.getResources().getColor(R.color.selected_background);
                int default_color = mContext.getResources().getColor(R.color.default_background);
                int color = selected ? selected_color : default_color;
                findViewById(R.id.info_field).setBackgroundColor(color);
                super.setSelected(selected);
            }
        };

        liveCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liveCardView.stopVideo();
            }
        });

        liveCardView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    liveCardView.startVideo();
                }else {
                    liveCardView.stopVideo();
                }
            }
        });
        liveCardView.setFocusable(true);
        liveCardView.setFocusableInTouchMode(true);
        return new ViewHolder(liveCardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        LiveCardView liveCardView = (LiveCardView) viewHolder.view;
        VideoModel videoModel = (VideoModel) item;
        liveCardView.setMainContainerDimensions(CARD_WIDTH, CARD_HEIGHT);
        int size = (int) (CARD_WIDTH * 1.25);
        liveCardView.setVideoViewSize(size, size);
        liveCardView.setTitleText(videoModel.getTitle());
        liveCardView.setVideoUrl(videoModel.getVideoUrl());
        liveCardView.getMainImageView().setImageDrawable(mContext.getResources().getDrawable(R.drawable.hitherejoe));
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
    }
}
