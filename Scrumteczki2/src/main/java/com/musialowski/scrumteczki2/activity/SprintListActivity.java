package com.musialowski.scrumteczki2.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.File;

import com.musialowski.scrumteczki2.ObservableSprintList;
import com.musialowski.scrumteczki2.R;
import com.musialowski.scrumteczki2.ScrumteczkiApp;
import com.musialowski.scrumteczki2.adapter.SprintListAdapter;
import com.musialowski.scrumteczki2.persistence.ScrumFacade;
import com.musialowski.scrumteczki2.service.LoadSprintsFromFileTask;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        try {
            startActivityForResult(intent, SELECT_EXCEL_FILE);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(
                    this,
                    "Na urządzeniu nie znaleziono żadnego menedżera plików pozwalającego wykonać tę operację!",
                    Toast.LENGTH_LONG)
                    .show();
        }
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


}
