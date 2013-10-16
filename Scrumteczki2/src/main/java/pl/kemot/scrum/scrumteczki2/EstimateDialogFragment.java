package pl.kemot.scrum.scrumteczki2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.lang.reflect.Array;

import pl.kemot.scrum.scrumteczki2.model.StandardEstimatesModel;

/**
 * Created by Tomek on 16.10.13.
 */
public class EstimateDialogFragment extends DialogFragment {
    private Context context;
    private String[] labels;
    private Estimate[] estimates;

    public EstimateDialogFragment() {
        context = getActivity();
        init();
    }

    public EstimateDialogFragment(Context context) {
        this.context = context;
        init();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View estimatePickerLayout = inflater.inflate(R.layout.estimate_picker, null);
        NumberPicker picker = (NumberPicker) estimatePickerLayout.findViewById(R.id.estimateNumberPicker);
        picker.setMinValue(0);
        picker.setMaxValue(estimates.length - 1);
        picker.setDisplayedValues(labels);
        return dialogBuilder
                .setView(estimatePickerLayout)
                .create();

    }

    private void init() {
        estimates = StandardEstimatesModel.getEstimates();
        labels = (String[]) Array.newInstance(String.class, estimates.length);
        for (int i = 0; i < estimates.length; i++) {
            labels[i] = estimates[i].getLabel();
        }
    }
}
