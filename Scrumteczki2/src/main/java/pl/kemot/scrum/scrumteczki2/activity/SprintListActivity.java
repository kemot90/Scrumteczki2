package pl.kemot.scrum.scrumteczki2.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.File;

import pl.kemot.scrum.scrumteczki2.ObservableSprintList;
import pl.kemot.scrum.scrumteczki2.R;
import pl.kemot.scrum.scrumteczki2.ScrumteczkiApp;
import pl.kemot.scrum.scrumteczki2.adapter.SprintListAdapter;
import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.persistence.ScrumFacade;
import pl.kemot.scrum.scrumteczki2.service.ExcelTaskListReaderService;

/**
 * Created by Tomek on 03.01.14.
 */
public class SprintListActivity extends Activity {
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
                    Toast.makeText(this, "Wybrano plik: " + excelFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    Sprint sprint = ExcelTaskListReaderService.loadTasksFromExcelWorkbook(excelFile);
                    scrumFacade.saveLoadedSprint(sprint);
                    application.getObservableSprintList().addSprint(sprint);
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
}
