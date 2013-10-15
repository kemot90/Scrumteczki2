package pl.kemot.scrum.scrumteczki2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

import pl.kemot.scrum.scrumteczki2.model.StandardEstimatesModel;

public class MainActivity extends Activity {

    private TextView optimistic;
    private TextView pessimistic;
    private Spinner leftEstimate;
    private Spinner rightEstimate;
    public static AlertDialog estimateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        EstimateAdapter estimateAdapter = new EstimateAdapter(
                MainActivity.this,
                R.layout.main,
                StandardEstimatesModel.getEstimates());

        leftEstimate = (Spinner) findViewById(R.id.estimateLeft);
        rightEstimate = (Spinner) findViewById(R.id.estimateRight);
        optimistic = (TextView) findViewById(R.id.optimistic);
        pessimistic = (TextView) findViewById(R.id.pessimistic);

        leftEstimate.setAdapter(estimateAdapter);
        rightEstimate.setAdapter(estimateAdapter);

        leftEstimate.setOnItemSelectedListener(getSelectEvent());
        rightEstimate.setOnItemSelectedListener(getSelectEvent());

        Spinner estimateSpinner = new Spinner(MainActivity.this);
        estimateSpinner.setAdapter(estimateAdapter);
        estimateSpinner.setOnItemSelectedListener(getSelectEvent());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        estimateDialog = dialogBuilder.setView(estimateSpinner).create();

        optimistic.setOnClickListener(createEstimateClickEvent());
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

    private AdapterView.OnClickListener createEstimateClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.estimateDialog.show();
            }
        };
    }
}
