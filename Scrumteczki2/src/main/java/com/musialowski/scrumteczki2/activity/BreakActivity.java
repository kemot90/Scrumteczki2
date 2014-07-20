package com.musialowski.scrumteczki2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.musialowski.scrumteczki2.R;

/**
 * Created by Tomek on 14.01.14.
 */
public class BreakActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.break_estimate);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    public void onClickBackToEstimate(View view) {
        onBackPressed();
    }
}
