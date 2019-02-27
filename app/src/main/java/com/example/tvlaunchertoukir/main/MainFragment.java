package com.example.tvlaunchertoukir.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import com.example.tvlaunchertoukir.R;
import com.example.tvlaunchertoukir.app.AppDataManage;
import com.example.tvlaunchertoukir.app.AppModel;
import com.example.tvlaunchertoukir.details.DetailsActivity;
import com.example.tvlaunchertoukir.model.FunctionModel;
import com.example.tvlaunchertoukir.model.MediaModel;
import com.example.tvlaunchertoukir.presenter.AppRowPresenter;
import com.example.tvlaunchertoukir.presenter.FunctionPresenter;
import com.example.tvlaunchertoukir.presenter.ImageCardPresenter;

import java.util.ArrayList;

public class MainFragment extends BrowseFragment {

    protected BrowseFragment mBrowseFragment;
    private ArrayObjectAdapter rowsAdapter;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private ListRowPresenter listRowPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        setupUIElements();

        prepareBackgroundManager();

        buildRowsItem();
    }

    private void buildRowsItem() {

        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

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
                }
            }
        });


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
        ArrayObjectAdapter rowAdapter = new ArrayObjectAdapter(new ImageCardPresenter());
        for (MediaModel mediaModel : MediaModel.getVideoModels()){
            rowAdapter.add(mediaModel);
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
        setTitle("WT"); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_DISABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
//        setBrandColor(ContextCompat.getColor(getActivity(), R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.search_opaque));
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }
}
