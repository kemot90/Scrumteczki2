package pl.kemot.scrum.scrumteczki2.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import pl.kemot.scrum.scrumteczki2.ScrumteczkiApp;
import pl.kemot.scrum.scrumteczki2.model.Changes;
import pl.kemot.scrum.scrumteczki2.persistence.ScrumFacade;

/**
 * Created by Tomek on 12.01.14.
 */
public class LoadChangesTask extends AsyncTask<Void, Void, List<Changes>> {
    private final Context context;
    private final ScrumteczkiApp application;
    public LoadChangesTask(Context context) {
        this.context = context;
        application = (ScrumteczkiApp) context.getApplicationContext();
    }
    @Override
    protected List<Changes> doInBackground(Void... noArguments) {
        Log.d("TM", "TASK(doInBackground): Rozpoczęcie pobierania danych o zmianach w tle.");
        final ScrumFacade scrumFacade = new ScrumFacade(context);
        return scrumFacade.loadAllChangesFromDataBase();
    }

    @Override
    protected void onPostExecute(List<Changes> changes) {
        Log.d("TM", "TASK(onPostExecute): Dodanie wczytanych elementów zmian do listy obserwowalnej.");
        application.getObservableChangesList().addAll(changes);
    }
}
