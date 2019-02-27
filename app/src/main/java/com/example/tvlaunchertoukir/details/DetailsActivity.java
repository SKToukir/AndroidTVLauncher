package com.example.tvlaunchertoukir.details;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tvlaunchertoukir.R;

public class DetailsActivity extends Activity {

    public static final String KEY_MOVIE = "movie";
    public static final String SHARED_NAME = "item_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}
