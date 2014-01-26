package pl.kemot.scrum.scrumteczki2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import pl.kemot.scrum.scrumteczki2.R;

/**
 * Created by Tomek on 14.01.14.
 */
public class UnknownEstimateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.unknown_estimate);
    }
    public void onClickBackToEstimate(View view) {
        onBackPressed();
    }
}
