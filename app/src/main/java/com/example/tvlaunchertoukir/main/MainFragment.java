package com.example.tvlaunchertoukir.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.FocusHighlight;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.tvlaunchertoukir.R;
import com.example.tvlaunchertoukir.app.AppDataManage;
import com.example.tvlaunchertoukir.app.AppModel;
import com.example.tvlaunchertoukir.details.DetailsActivity;
import com.example.tvlaunchertoukir.model.FunctionModel;
import com.example.tvlaunchertoukir.model.MediaModel;
import com.example.tvlaunchertoukir.model.VideoModel;
import com.example.tvlaunchertoukir.presenter.AppRowPresenter;
import com.example.tvlaunchertoukir.presenter.FunctionPresenter;
import com.example.tvlaunchertoukir.presenter.ImageCardPresenter;
import com.example.tvlaunchertoukir.presenter.VideoPresenter;
import com.example.tvlaunchertoukir.util.ClockView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends BrowseFragment {

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    public static String BACK_IMAGE_URL = "";

    private Handler mHandler = new Handler();
    private ArrayObjectAdapter rowsAdapter;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUIElements();

        prepareBackgroundManager();

        buildRowsItem();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void buildRowsItem() {

        ListRowPresenter listRowPresenter = new ListRowPresenter();
        listRowPresenter.setShadowEnabled(false);        // 不显示阴影部分
        listRowPresenter.setKeepChildForeground(false);

        rowsAdapter = new ArrayObjectAdapter(listRowPresenter);

        addPhotoRow();
        addVideoAdapter();
        addAppRow();
        appFunctions();

        setAdapter(rowsAdapter);
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof MediaModel){
                    MediaModel mediaModel = (MediaModel) item;
                    @SuppressLint({"NewApi", "LocalSuppress"}) Intent intent = new Intent(getContext(), DetailsActivity.class);
                    intent.putExtra(DetailsActivity.KEY_MOVIE,mediaModel);

                    @SuppressLint({"NewApi", "LocalSuppress"})
                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getContext(),((ImageCardView)itemViewHolder.view).getMainImageView(),
                            DetailsActivity.SHARED_NAME).toBundle();
                    startActivity(intent, bundle);
                }else if (item instanceof AppModel){
                    AppModel appModel = (AppModel) item;
                    Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(appModel.getPackageName());
                    if (intent != null){
                        getActivity().startActivity(intent);
                    }
                }
            }
        });

        setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof MediaModel){
                    MediaModel mediaModel = (MediaModel) item;
//                    setImageBackground(mediaModel.getImageUrl());
                    startBackgroundTimer();
                    BACK_IMAGE_URL =  mediaModel.getImageUrl();
                }else if (item instanceof VideoModel){
                    VideoModel videoModel = (VideoModel) item;
//                    setImageBackground("http://e.hiphotos.baidu.com/zhidao/pic/item/5ab5c9ea15ce36d3418e754838f33a87e850b1c4.jpg");
                    BACK_IMAGE_URL =  "http://e.hiphotos.baidu.com/zhidao/pic/item/5ab5c9ea15ce36d3418e754838f33a87e850b1c4.jpg";
                    startBackgroundTimer();

                }else {
                    mBackgroundManager.setBitmap(null);
                    BACK_IMAGE_URL = "null";
//                    setImageBackground(BACK_IMAGE_URL);
                    startBackgroundTimer();
                }
            }
        });
    }

    private void startBackgroundTimer() {
        if(null != mBackgroundTimer){
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private void setImageBackground(String imageUrl) {

        if (imageUrl != "null") {
            int width = mMetrics.widthPixels;
            int height = mMetrics.heightPixels;

            Glide.with(getActivity())
                    .load(imageUrl)
                    .asBitmap()
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>(width, height) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mBackgroundManager.setBitmap(resource);
                        }
                    });
        }else {
            mBackgroundManager.setBitmap(null);
        }
    }

    private void appFunctions() {
        String headerName = "Others";
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(new FunctionPresenter());
        HeaderItem headerItem = new HeaderItem(0, headerName);
        ArrayList<FunctionModel> list = new FunctionModel().getFunctionsList(getActivity());
        adapter.add(list.get(0));
        rowsAdapter.add(new ListRow(headerItem, adapter));
    }

    private void addAppRow() {
        String headerName = "App";
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(new AppRowPresenter());
        ArrayList<AppModel> appModelArrayList = new AppDataManage(getActivity()).getLaunchAppList();
        for (int i = 0; i < appModelArrayList.size(); i++){
            adapter.add(appModelArrayList.get(i));
        }
        HeaderItem headerItem = new HeaderItem(0,headerName);
        rowsAdapter.add(new ListRow(headerItem,adapter));
    }

    private void addVideoAdapter() {
        String headerName = "Video";
        ArrayObjectAdapter rowAdapter = new ArrayObjectAdapter(new VideoPresenter());

        for (int i = 0; i < 3; i++){
            VideoModel videoModel = new VideoModel();
            videoModel.setTitle("Video One");
            videoModel.setVideoUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");

            rowAdapter.add(videoModel);
        }
        HeaderItem headerItem = new HeaderItem(0,headerName);
        rowsAdapter.add(new ListRow(headerItem,rowAdapter));
    }

    private void addPhotoRow() {
        String headerName = "Photo";
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new ImageCardPresenter());

        for (MediaModel mediaModel : MediaModel.getPhotoModels()) {
            listRowAdapter.add(mediaModel);
        }
        HeaderItem header = new HeaderItem(0, headerName);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
    }


    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));


//        setTitle(clockView); // Badge, when set, takes precedent
        // over title

        addClockView();



        setHeadersState(HEADERS_DISABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
//        setBrandColor(ContextCompat.getColor(getActivity(), R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.search_opaque));
    }

    @SuppressLint("ResourceType")
    private void addClockView() {
        ClockView clockView = new ClockView(getActivity());
        clockView.setTextSize(22);
        clockView.setId(1);
        clockView.setTextColor(Color.YELLOW);
        setMenuVisibility(true);

        RelativeLayout.LayoutParams txtParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        txtParams.addRule(RelativeLayout.RIGHT_OF,clockView.getId());
        txtParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        txtParams.bottomMargin = 30;

        TextView txtTimeFormat = new TextView(getActivity());
        txtTimeFormat.setText("PM");
        txtTimeFormat.setTextColor(Color.WHITE);



        RelativeLayout mainLayout = new RelativeLayout(getActivity());

        RelativeLayout.LayoutParams mainLayoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,100);
        mainLayout.setGravity(Gravity.RIGHT);
        mainLayout.addView(txtTimeFormat, txtParams);
        mainLayout.setPadding(0,0,10,0);

        mainLayout.setBackground(getResources().getDrawable(R.drawable.clock_back));
//        mainLayout.setBackgroundColor(Color.TRANSPARENT);
        mainLayout.addView(clockView);

        getActivity().addContentView(mainLayout, mainLayoutParam);
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("life_cycle","onResume");
        setImageBackground(BACK_IMAGE_URL);
    }


    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    setImageBackground(BACK_IMAGE_URL);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer){
            mBackgroundTimer.cancel();
        }
    }
}
