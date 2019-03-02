package com.example.tvlaunchertoukir.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tvlaunchertoukir.R;
import com.example.tvlaunchertoukir.app.AppModel;

public class AppRowPresenter extends Presenter {

    private Context mContext;
    private int CARD_WIDTH = 313;
    private int CARD_HEIGHT =176;
    private Drawable mImageCardDrawable;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        mImageCardDrawable = mContext.getResources().getDrawable(R.drawable.hdback);
        ImageCardView imageCardView = new ImageCardView(mContext){
            @Override
            public void setSelected(boolean selected) {
                int selected_color = mContext.getResources().getColor(R.color.selected_background);
                int default_color = mContext.getResources().getColor(R.color.default_background);
                int color = selected ? selected_color : default_color;
                findViewById(R.id.info_field).setBackgroundColor(color);
                super.setSelected(selected);
            }
        };
        imageCardView.setFocusable(true);
        imageCardView.setFocusableInTouchMode(true);
        return new ViewHolder(imageCardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ImageCardView imageCardView = (ImageCardView) viewHolder.view;
        imageCardView.setMainImageDimensions(CARD_WIDTH,CARD_HEIGHT);
        AppModel appModel = (AppModel) item;
        imageCardView.setMainImageScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageCardView.setMainImage(appModel.getIcon());
        imageCardView.setTitleText(appModel.getName());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
//        ImageCardView imageCardView = (ImageCardView) viewHolder.view;
//        imageCardView.setBadgeImage(null);
//        imageCardView.setMainImage(null);
    }
}
