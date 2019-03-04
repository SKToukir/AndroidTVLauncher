package com.example.tvlaunchertoukir.presenter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.default_border);
        final ImageCardView imageCardView = new ImageCardView(new ContextThemeWrapper(mContext, R.style.CustomImageCardViewStyle)) {
            @Override
            public void setSelected(boolean selected) {
                int selected_background = mContext.getResources().getColor(R.color.selected_background);
                int default_background = mContext.getResources().getColor(R.color.default_background);
                int color = selected ? selected_background : default_background;
                findViewById(R.id.info_field).setBackgroundColor(color);
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };
        imageCardView.setMainImageAdjustViewBounds(false);
        imageCardView.setFocusable(true);
        imageCardView.setFocusableInTouchMode(true);
        imageCardView.setInfoVisibility(View.VISIBLE);
        imageCardView.setBackgroundColor(Color.TRANSPARENT);
        updateCardBackgroundColor(imageCardView, false);
        return new ViewHolder(imageCardView);
    }

    private void updateCardBackgroundColor(ImageCardView view, boolean selected) {

        if (selected){
//            view.setPadding(5,5,5,5);
            view.setBackgroundResource(R.drawable.icon_focus_border);
        }

        else {
//            view.setPadding(5,5,5,5);
//            view.setBackgroundResource(R.drawable.one);

            view.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {


        ImageCardView imageCardView = (ImageCardView) viewHolder.view;
        imageCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

        if (o instanceof MediaModel) {
            MediaModel mediaModel = (MediaModel) o;
            imageCardView.setTitleText(mediaModel.getTitle());
            imageCardView.setContentText(mediaModel.getContent());
            Glide.with(imageCardView.getMainImageView().getContext())
                    .load(mediaModel.getImageUrl()).crossFade()
                    .into(imageCardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageCardView imageCardView = (ImageCardView) viewHolder.view;
        imageCardView.setBadgeImage(null);
        imageCardView.setMainImage(null);
    }
}
