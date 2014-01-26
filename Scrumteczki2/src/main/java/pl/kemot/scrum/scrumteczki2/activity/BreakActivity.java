package pl.kemot.scrum.scrumteczki2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import pl.kemot.scrum.scrumteczki2.R;

/**
 * Created by Tomek on 14.01.14.
 */
public class BreakActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.break_estimate);
    }
    public void onClickBackToEstimate(View view) {
        onBackPressed();
    }
}
