package com.musialowski.scrumteczki2.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import com.musialowski.scrumteczki2.ScrumteczkiApp;
import com.musialowski.scrumteczki2.model.Sprint;
import com.musialowski.scrumteczki2.persistence.ScrumFacade;

/**
 * Created by Tomek on 03.01.14.
 */
public class LoadSprintsTask extends AsyncTask<Void, Void, List<Sprint>> {
    private final Context context;
    private final ScrumteczkiApp application;
    public LoadSprintsTask(Context context) {
        this.context = context;
        application = (ScrumteczkiApp) context.getApplicationContext();
    }
    @Override
    protected List<Sprint> doInBackground(Void... noArguments) {
        Log.d("TM", "TASK(doInBackground): Rozpoczęcie pobierania danych o sprintach w tle.");
        final ScrumFacade scrumFacade = new ScrumFacade(context);
        return scrumFacade.loadAllSprintsFromDataBase();
    }

    @Override
    protected void onPostExecute(List<Sprint> sprints) {
        Log.d("TM", "TASK(onPostExecute): Dodanie wczytanych elementów do listy obserwowalnej.");
        application.getObservableSprintList().addAll(sprints);
    }
}
