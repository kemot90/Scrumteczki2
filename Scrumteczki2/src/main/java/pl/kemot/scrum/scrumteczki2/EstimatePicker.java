package pl.kemot.scrum.scrumteczki2;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.Spinner;

/**
 * Created by tmusialowski on 15.10.13.
 */
public class EstimatePicker {
    private Activity activity;
    private AlertDialog estimateDialog;

    public EstimatePicker(Activity activity) {
        this.activity = activity;
        Spinner estimateSpinner = (Spinner) activity.findViewById(R.id.estimateLeft);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        estimateDialog = dialogBuilder.setView(estimateSpinner).create();
    }
}
