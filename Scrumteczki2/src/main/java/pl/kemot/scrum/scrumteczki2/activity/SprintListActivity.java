package pl.kemot.scrum.scrumteczki2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import pl.kemot.scrum.scrumteczki2.ObservableSprintList;
import pl.kemot.scrum.scrumteczki2.R;
import pl.kemot.scrum.scrumteczki2.ScrumteczkiApp;
import pl.kemot.scrum.scrumteczki2.adapter.SprintListAdapter;
import pl.kemot.scrum.scrumteczki2.persistence.ScrumFacade;

/**
 * Created by Tomek on 03.01.14.
 */
public class SprintListActivity extends Activity {
    private ScrumFacade scrumFacade;
    private ExpandableListView layoutSprintList;
    private SprintListAdapter sprintListAdapter;
    private ScrumteczkiApp application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprint_list);

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
}
