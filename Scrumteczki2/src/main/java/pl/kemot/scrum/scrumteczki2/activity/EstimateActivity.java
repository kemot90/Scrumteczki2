package pl.kemot.scrum.scrumteczki2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import pl.kemot.scrum.scrumteczki2.R;
import pl.kemot.scrum.scrumteczki2.adapter.EstimateAdapter;
import pl.kemot.scrum.scrumteczki2.model.Estimate;
import pl.kemot.scrum.scrumteczki2.model.StandardEstimatesModel;

public class EstimateActivity extends Activity {

    private Spinner leftEstimate;
    private Spinner rightEstimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.estimate);

        EstimateAdapter estimateAdapter = new EstimateAdapter(
                EstimateActivity.this,
                R.layout.estimate,
                StandardEstimatesModel.getEstimates());

        leftEstimate = (Spinner) findViewById(R.id.estimateLeft);
        rightEstimate = (Spinner) findViewById(R.id.estimateRight);

        leftEstimate.setAdapter(estimateAdapter);
        rightEstimate.setAdapter(estimateAdapter);

        leftEstimate.setOnItemSelectedListener(getSelectEvent());
        rightEstimate.setOnItemSelectedListener(getSelectEvent());
    }

    public void onClickStartBreakActivity(View view) {
        Intent breakActivityIntent = new Intent(EstimateActivity.this, BreakActivity.class);
        startActivity(breakActivityIntent);
    }

    public void onClickStartUnknownEstimateActivity(View view) {
        Intent breakActivityIntent = new Intent(EstimateActivity.this, UnknownEstimateActivity.class);
        startActivity(breakActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private AdapterView.OnItemSelectedListener getSelectEvent() {
        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                try {
                    TextView optimistic = (TextView) findViewById(R.id.optimistic);
                    TextView pessimistic = (TextView) findViewById(R.id.pessimistic);

                    final Spinner leftSpinner = (Spinner) findViewById(R.id.estimateLeft);
                    final Spinner rightSpinner = (Spinner) findViewById(R.id.estimateRight);

                    Estimate leftEstimate = (Estimate) leftSpinner.getSelectedItem();
                    Estimate rightEstimate = (Estimate) rightSpinner.getSelectedItem();

                    if (leftEstimate.getEstimateTimeInMinutes() > rightEstimate.getEstimateTimeInMinutes()) {
                        optimistic.setText(rightEstimate.getLabel());
                        pessimistic.setText(leftEstimate.getLabel());
                    } else {
                        optimistic.setText(leftEstimate.getLabel());
                        pessimistic.setText(rightEstimate.getLabel());
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        };
    }
}
