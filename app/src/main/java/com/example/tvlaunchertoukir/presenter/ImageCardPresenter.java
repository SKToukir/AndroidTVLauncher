package com.example.tvlaunchertoukir.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.tvlaunchertoukir.R;
import com.example.tvlaunchertoukir.model.MediaModel;

public class ImageCardPresenter extends Presenter {

    private Context mContext;
    private int CARD_WIDTH = 313;
    private int CARD_HEIGHT = 173;
    private Drawable mDefaultCardImage;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {

        mContext = viewGroup.getContext();
        mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.hdback);
        ImageCardView imageCardView = new ImageCardView(mContext){
            @Override
            public void setSelected(boolean selected) {
                int selected_background = mContext.getResources().getColor(R.color.selected_background);
                int default_background = mContext.getResources().getColor(R.color.default_background);
                int color = selected ? selected_background : default_background;
                findViewById(R.id.info_field).setBackgroundColor(color);
                super.setSelected(selected);
            }
        };

        imageCardView.setFocusable(true);
        imageCardView.setFocusableInTouchMode(true);

        return new ViewHolder(imageCardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {
        ImageCardView imageCardView = (ImageCardView) viewHolder.view;
        imageCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        if (o instanceof MediaModel){
            MediaModel mediaModel = (MediaModel) o;
            imageCardView.setTitleText(mediaModel.getTitle());
            imageCardView.setContentText(mediaModel.getContent());
            Glide.with(imageCardView.getMainImageView().getContext()).load(mediaModel.getImageUrl()).crossFade().into(imageCardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageCardView imageCardView = (ImageCardView) viewHolder.view;
        imageCardView.setBadgeImage(null);
        imageCardView.setMainImage(null);
    }
}
