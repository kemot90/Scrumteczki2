package com.musialowski.scrumteczki2.service;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import com.musialowski.scrumteczki2.ScrumteczkiApp;
import com.musialowski.scrumteczki2.model.Sprint;
import com.musialowski.scrumteczki2.persistence.ScrumFacade;

/**
 * Created by Tomek on 05.01.14.
 */
public class LoadSprintsFromFileTask extends AsyncTask<Void, Void, Sprint> {
    private Context context;
    private ScrumteczkiApp application;
    private Uri excelFileUri;
    private ScrumFacade scrumFacade;
    public LoadSprintsFromFileTask(Context context, Uri fileUri) {
        this.context = context;
        application = (ScrumteczkiApp) context.getApplicationContext();
        excelFileUri = fileUri;
        scrumFacade = new ScrumFacade(context);
    }
    @Override
    protected Sprint doInBackground(Void... voids) {
        Sprint sprint = new Sprint();
        try {
            sprint = ExcelTaskListReaderService.loadTasksFromExcelWorkbook(excelFileUri);
        } catch (OfficeXmlFileException ex) {
            return null;
        }
        return sprint;
    }

    @Override
    protected void onPostExecute(Sprint sprint) {
        if (sprint != null) {
            scrumFacade.saveLoadedSprint(sprint);
            application.getObservableSprintList().addSprint(sprint);
            Toast.makeText(context, "Wczytano listę zadań z pliku.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Nie udało się wczytać listy zadań z pliku.", Toast.LENGTH_LONG).show();
        }
    }
}
