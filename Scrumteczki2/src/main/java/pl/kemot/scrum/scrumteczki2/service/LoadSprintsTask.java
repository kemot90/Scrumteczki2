package pl.kemot.scrum.scrumteczki2.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import pl.kemot.scrum.scrumteczki2.adapter.SprintListAdapter;
import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.persistence.ScrumFacade;

/**
 * Created by Tomek on 03.01.14.
 */
public class LoadSprintsTask extends AsyncTask<Void, Void, List<Sprint>> {
    private final Context context;
    private final SprintListAdapter adapter;
    public LoadSprintsTask(Context context, SprintListAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }
    @Override
    protected List<Sprint> doInBackground(Void... noArguments) {
        final ScrumFacade scrumFacade = new ScrumFacade(context);
        return scrumFacade.loadAllSprintsFromDataBase();
    }

    @Override
    protected void onPostExecute(List<Sprint> sprints) {
        adapter.setGroups(sprints);
    }
}
