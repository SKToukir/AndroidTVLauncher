package com.example.tvlaunchertoukir.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.example.tvlaunchertoukir.model.MediaModel;

public class MediaDetailsDescriptiorPresenter extends AbstractDetailsDescriptionPresenter {
    @Override
    protected void onBindDescription(ViewHolder vh, Object item) {

        if (item instanceof MediaModel){
            MediaModel mediaModel = (MediaModel) item;
            vh.getTitle().setText(mediaModel.getTitle());
            vh.getBody().setText(mediaModel.getContent());
            vh.getSubtitle().setText("This is a subtitle!");
        }
    }
}
