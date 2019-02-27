package com.example.tvlaunchertoukir.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tvlaunchertoukir.R;
import com.example.tvlaunchertoukir.model.FunctionModel;

public class FunctionPresenter extends Presenter {


    private Context mContext;
    private int CARD_WIDTH = 313;
    private int CARD_HEIGHT = 176;
    private Drawable mImageDefaultDrawable;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

        mContext = parent.getContext();
        mImageDefaultDrawable = mContext.getResources().getDrawable(R.drawable.uninstall);
        ImageCardView imageCardView = new ImageCardView(mContext){
            @Override
            public void setSelected(boolean selected) {
                int selectedColor = mContext.getResources().getColor(R.color.selected_background);
                int defaultColor = mContext.getResources().getColor(R.color.default_background);
                int color = selected ? selectedColor : defaultColor;
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
        Log.d("adapter_log","bind");
        ImageCardView imageCardView = (ImageCardView) viewHolder.view;
        FunctionModel functionModel = (FunctionModel) item;

        imageCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
        imageCardView.setTitleText(functionModel.getTitle());
        imageCardView.setMainImageScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageCardView.setMainImage(functionModel.getIcon());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        Log.d("adapter_log","Unbind");
        ImageCardView imageCardView = (ImageCardView) viewHolder.view;
        imageCardView.setBadgeImage(null);
        imageCardView.setMainImage(null);
    }
}
