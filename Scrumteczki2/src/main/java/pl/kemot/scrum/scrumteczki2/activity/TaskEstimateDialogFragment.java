package pl.kemot.scrum.scrumteczki2.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import pl.kemot.scrum.scrumteczki2.R;
import pl.kemot.scrum.scrumteczki2.ScrumteczkiApp;
import pl.kemot.scrum.scrumteczki2.model.Changes;
import pl.kemot.scrum.scrumteczki2.model.EstimatedTime;
import pl.kemot.scrum.scrumteczki2.model.Task;
import pl.kemot.scrum.scrumteczki2.persistence.ScrumFacade;

public class TaskEstimateDialogFragment extends DialogFragment {
    private NumberPicker hoursPicker;
    private NumberPicker minutesPicker;
    private NumberPicker secondsPicker;
    private Parcelable data;
    private View callingView;
    private Task task;
    private EstimatedTime estimatedTime;
    private Context context;
    private ScrumteczkiApp application;
    private ScrumFacade scrumFacade;

    public TaskEstimateDialogFragment() {

    }
    public TaskEstimateDialogFragment(View callingView) {
        this.callingView = callingView;
        task = (Task) callingView.getTag();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("TM", "Zapisywanie obiektów odpowiedzialnych za stan dialogu zmiany estymaty. " +
                "Estymata przechwycona z NumberPickerów: " + hoursPicker.getValue() +
                ":" + minutesPicker.getValue() + ":00");
        EstimatedTime newEstimatedTime = new EstimatedTime(
                hoursPicker.getValue(),
                minutesPicker.getValue(),
                (short) 0);
        outState.putSerializable("newEstimatedTime", newEstimatedTime);
        outState.putSerializable("task", task);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        application = (ScrumteczkiApp) context.getApplicationContext();
        scrumFacade = new ScrumFacade(context);
        if (savedInstanceState != null) {
            Log.d("TM", "Wczytanie poprzedniego stanu estymaty z Bundla!");
            estimatedTime = (EstimatedTime) savedInstanceState.getSerializable("newEstimatedTime");
            Log.d("TM", "Estymata wczytana z Bundla: " + estimatedTime);
            task = (Task) savedInstanceState.getSerializable("task");
            return;
        }
        if (application.getObservableChangesList().containsTask(task)) {
            Changes changes = application.getObservableChangesList().getChangesByTask(task);
            String estimetedTimeAsString = changes.getNewEstimatedTimeToCompleteTask();
            estimatedTime = new EstimatedTime(estimetedTimeAsString);
        } else {
            estimatedTime = new EstimatedTime(task.getEstimatedTime());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Log.d("TM", "Tworzenie dialogu zmiany estymaty!");
        Log.d("TM", "Do onCreateDialog trafia estymata " + estimatedTime);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View estimateChangerLayout = inflater.inflate(R.layout.estimate_changer, null);

        hoursPicker = (NumberPicker) estimateChangerLayout.findViewById(R.id.hours);
        minutesPicker = (NumberPicker) estimateChangerLayout.findViewById(R.id.minutes);
        secondsPicker = (NumberPicker) estimateChangerLayout.findViewById(R.id.seconds);
        hoursPicker.setWrapSelectorWheel(false);
        minutesPicker.setWrapSelectorWheel(false);
        secondsPicker.setWrapSelectorWheel(false);
        hoursPicker.setOrientation(LinearLayout.VERTICAL);
        Button zeroHoursButton = (Button) estimateChangerLayout.findViewById(R.id.zeroHours);
        Button zeroMinutesButton = (Button) estimateChangerLayout.findViewById(R.id.zeroMinutes);
        ImageButton zeroAllButton = (ImageButton) estimateChangerLayout.findViewById(R.id.zeroAll);
        zeroHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoursPicker.setValue(0);
            }
        });
        zeroMinutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minutesPicker.setValue(0);
            }
        });
        zeroAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoursPicker.setValue(0);
                minutesPicker.setValue(0);
            }
        });
        initPicker(hoursPicker, 1200, estimatedTime.getHours());
        initPicker(minutesPicker, 59, estimatedTime.getMinutes());
        initPicker(secondsPicker, 0, 0);
        return dialogBuilder
                .setView(estimateChangerLayout)
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EstimatedTime estimatedTime = new EstimatedTime(
                                hoursPicker.getValue(),
                                minutesPicker.getValue(),
                                (short) 0);
                        scrumFacade.addToDailyScrum(task, estimatedTime);
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onDestroyView() {
            /*
             * Workaround -
             */
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

    private void initPicker(NumberPicker picker, int maxValue, int currentValue) {
        picker.setMinValue(0);
        picker.setMaxValue(maxValue);
        picker.setValue(currentValue);
        picker.setWrapSelectorWheel(false);
    }
}