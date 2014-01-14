package pl.kemot.scrum.scrumteczki2.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import java.io.File;

import pl.kemot.scrum.scrumteczki2.ObservableSprintList;
import pl.kemot.scrum.scrumteczki2.R;
import pl.kemot.scrum.scrumteczki2.ScrumteczkiApp;
import pl.kemot.scrum.scrumteczki2.adapter.SprintListAdapter;
import pl.kemot.scrum.scrumteczki2.model.Changes;
import pl.kemot.scrum.scrumteczki2.model.EstimatedTime;
import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.model.Task;
import pl.kemot.scrum.scrumteczki2.persistence.ScrumFacade;
import pl.kemot.scrum.scrumteczki2.service.ExcelTaskListReaderService;
import pl.kemot.scrum.scrumteczki2.service.LoadSprintsFromFileTask;

/**
 * Created by Tomek on 03.01.14.
 */
public class SprintListActivity extends Activity implements ExpandableListView.OnChildClickListener {
    private ScrumFacade scrumFacade;
    private ExpandableListView layoutSprintList;
    private SprintListAdapter sprintListAdapter;
    private ScrumteczkiApp application;
    private static final int SELECT_EXCEL_FILE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprint_list);

        scrumFacade = new ScrumFacade(this);
        application = (ScrumteczkiApp) getApplication();
        layoutSprintList = (ExpandableListView) findViewById(R.id.sprintList);
        ObservableSprintList observableSprintList = application.getObservableSprintList();
        sprintListAdapter = new SprintListAdapter(this);
        layoutSprintList.setOnChildClickListener(this);
        observableSprintList.addListener(sprintListAdapter);
        layoutSprintList.setAdapter(sprintListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ObservableSprintList observableSprintList = application.getObservableSprintList();
        observableSprintList.removeListener(sprintListAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_EXCEL_FILE) {
                Uri selectedFileUri = data.getData();
                File excelFile = getFileFromUriIfExcel(selectedFileUri);
                if (excelFile != null) {
                    LoadSprintsFromFileTask loadSprintsFromFileTask = new LoadSprintsFromFileTask(this, selectedFileUri);
                    loadSprintsFromFileTask.execute();
                } else {
                    Toast wrongFile = Toast.makeText(this, "Wybrany plik nie jest plikiem arkusza Excel!", Toast.LENGTH_LONG);
                    wrongFile.show();
                }
            }
        }
    }

    public void onClickSelectFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, SELECT_EXCEL_FILE);
    }

    private File getFileFromUriIfExcel(Uri uri) {
        String path = uri.getPath();
        String extension = path.substring(path.lastIndexOf(".") + 1, path.length());
        if (extension.matches("xls[x]?")) {
            return new File(path);
        } else {
            return null;
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        new TaskEstimateDialogFragment(v).show(getFragmentManager(), "Zmień estymatę");
        return true;
    }

    private class TaskEstimateDialogFragment extends DialogFragment {
        private NumberPicker hoursPicker;
        private NumberPicker minutesPicker;
        private NumberPicker secondsPicker;
        private final View callingView;
        private Task task;
        private EstimatedTime estimatedTime;
        public TaskEstimateDialogFragment(View callingView) {
            this.callingView = callingView;
            task = (Task) callingView.getTag();
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
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SprintListActivity.this);
            View estimateChangerLayout = inflater.inflate(R.layout.estimate_changer, null);

            hoursPicker = (NumberPicker) estimateChangerLayout.findViewById(R.id.hours);
            minutesPicker = (NumberPicker) estimateChangerLayout.findViewById(R.id.minutes);
            secondsPicker = (NumberPicker) estimateChangerLayout.findViewById(R.id.seconds);
            Button zeroHoursButton = (Button) estimateChangerLayout.findViewById(R.id.zeroHours);
            Button zeroMinutesButton = (Button) estimateChangerLayout.findViewById(R.id.zeroMinutes);
            Button zeroAllButton = (Button) estimateChangerLayout.findViewById(R.id.zeroAll);
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

        private void initPicker(NumberPicker picker, int maxValue, int currentValue) {
            picker.setMinValue(0);
            picker.setMaxValue(maxValue);
            picker.setValue(currentValue);
            picker.setWrapSelectorWheel(false);
        }
    }
}
