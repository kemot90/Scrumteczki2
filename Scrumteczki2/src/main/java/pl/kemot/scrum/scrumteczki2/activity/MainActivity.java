package pl.kemot.scrum.scrumteczki2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pl.kemot.scrum.scrumteczki2.R;

/**
 * Created by Tomek on 23.11.13.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClickStartEstimateActivity(View view) {
        Intent estimateActivityIntent = new Intent(MainActivity.this, EstimateActivity.class);
        startActivity(estimateActivityIntent);
    }

    public void onClickStartSprintListActivity(View view) {
        Intent estimateActivityIntent = new Intent(MainActivity.this, SprintListActivity.class);
        startActivity(estimateActivityIntent);
    }
}
