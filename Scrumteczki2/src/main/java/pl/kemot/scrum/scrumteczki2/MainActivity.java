package pl.kemot.scrum.scrumteczki2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import pl.kemot.scrum.scrumteczki2.model.StandardEstimatesModel;

public class MainActivity extends Activity {

    private Estimate[] estimates;
    private List<Estimate> estimatesList;
    private Estimate optimisticEstimate;
    private Estimate pessimisticEstimate;
    private int optimisticPosition;
    private int pessimisticPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.d("TM:MainActivity", "onCreate()");

        estimates = StandardEstimatesModel.getEstimates();
        estimatesList = Arrays.asList(estimates);

        initializeEstimates();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private AdapterView.OnClickListener createEstimateClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EstimateDialogFragment(v).show(getFragmentManager(), "Wybierz estymatę");
            }
        };
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("TM:MainActivity", "onSaveInstanceState()");
        outState.putInt("optimistic", optimisticPosition);
        outState.putInt("pessimistic", pessimisticPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("TM:MainActivity", "onRestoreInstanceState()");
        optimisticPosition = savedInstanceState.getInt("optimistic", 0);
        pessimisticPosition = savedInstanceState.getInt("pessimistic", 0);
        initializeEstimates();
    }

    private void initializeEstimates() {
        optimisticEstimate = estimates[optimisticPosition];
        pessimisticEstimate = estimates[pessimisticPosition];

        TextView optimistic = (TextView) findViewById(R.id.optimistic);
        TextView pessimistic = (TextView) findViewById(R.id.pessimistic);

        optimistic.setOnClickListener(createEstimateClickEvent());
        pessimistic.setOnClickListener(createEstimateClickEvent());

        optimistic.setText(this.optimisticEstimate.getLabel());
        pessimistic.setText(this.pessimisticEstimate.getLabel());
    }

    private class EstimateDialogFragment extends DialogFragment {
        private String[] labels;
        private int estimatePosition;
        private final TextView optimistic;
        private final TextView pessimistic;
        private final View callingView;

        public EstimateDialogFragment(View callingView) {
            this.optimistic = (TextView) findViewById(R.id.optimistic);
            this.pessimistic = (TextView) findViewById(R.id.pessimistic);
            this.callingView = callingView;
            if (callingView.getId() == R.id.optimistic) {
                estimatePosition = optimisticPosition;
            } else {
                estimatePosition = pessimisticPosition;
            }
            init();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
            View estimatePickerLayout = inflater.inflate(R.layout.estimate_picker, null);

            final NumberPicker picker = (NumberPicker) estimatePickerLayout.findViewById(R.id.estimateNumberPicker);
            picker.setMinValue(0);
            picker.setMaxValue(estimates.length - 1);
            picker.setDisplayedValues(labels);
            picker.setValue(estimatePosition);

            return dialogBuilder
                    .setView(estimatePickerLayout)
                    .setPositiveButton("Wybierz", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int pickedValue = picker.getValue();
                            Estimate estimate = estimates[pickedValue];
                            if (callingView.getId() == R.id.optimistic) {
                                if (pessimisticEstimate.compareTo(estimate) < 0) {
                                    optimisticEstimate = pessimisticEstimate;
                                    pessimisticEstimate = estimate;
                                } else {
                                    optimisticEstimate = estimate;
                                }
                            }
                            if (callingView.getId() == R.id.pessimistic) {
                                if (optimisticEstimate.compareTo(estimate) > 0) {
                                    pessimisticEstimate = optimisticEstimate;
                                    optimisticEstimate = estimate;
                                } else {
                                    pessimisticEstimate = estimate;
                                }
                            }
                            optimisticPosition = estimatesList.indexOf(optimisticEstimate);
                            pessimisticPosition = estimatesList.indexOf(pessimisticEstimate);
                            optimistic.setText(optimisticEstimate.getLabel());
                            pessimistic.setText(pessimisticEstimate.getLabel());
                        }
                    })
                    .create();

        }

        private void init() {
            labels = (String[]) Array.newInstance(String.class, estimates.length);
            for (int i = 0; i < estimates.length; i++) {
                labels[i] = estimates[i].getLabel();
            }
        }
    }
}
