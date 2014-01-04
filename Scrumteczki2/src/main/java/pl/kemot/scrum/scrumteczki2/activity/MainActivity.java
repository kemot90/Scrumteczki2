package pl.kemot.scrum.scrumteczki2.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import pl.kemot.scrum.scrumteczki2.R;
import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.model.Task;
import pl.kemot.scrum.scrumteczki2.persistence.ScrumFacade;
import pl.kemot.scrum.scrumteczki2.service.ExcelTaskListReaderService;
import pl.kemot.scrum.scrumteczki2.service.LoadSprintsTask;

/**
 * Created by Tomek on 23.11.13.
 */
public class MainActivity extends Activity {
    private static final int SELECT_EXCEL_FILE = 1;
    private String selectedFilePath;
    private ScrumFacade scrumFacade;
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

    public void onClickSelectFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, SELECT_EXCEL_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_EXCEL_FILE) {
                Uri selectedFileUri = data.getData();
                File excelFile = getFileFromUriIfExcel(selectedFileUri);
                if (excelFile != null) {
                    Toast.makeText(this, "Wybrano plik: " + excelFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    TextView test = (TextView) findViewById(R.id.test_output);
                    Sprint sprint = ExcelTaskListReaderService.loadTasksFromExcelWorkbook(excelFile);
                    String text = "";
                    for (Task task : sprint.getTasks()) {
                        text += task.getLabel() + " - " + task.getEstimatedTime() + "\n";
                    }
                    scrumFacade.saveLoadedSprint(sprint);
                } else {
                    Toast wrongFile = Toast.makeText(this, "Wybrany plik nie jest plikiem arkusza Excel!", Toast.LENGTH_LONG);
                    wrongFile.show();
                }
            }
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
}
