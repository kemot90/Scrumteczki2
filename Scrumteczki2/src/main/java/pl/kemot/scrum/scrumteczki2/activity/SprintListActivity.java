package pl.kemot.scrum.scrumteczki2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.List;

import pl.kemot.scrum.scrumteczki2.R;
import pl.kemot.scrum.scrumteczki2.adapter.SprintListAdapter;
import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.persistence.ScrumFacade;

/**
 * Created by Tomek on 03.01.14.
 */
public class SprintListActivity extends Activity {
    private ScrumFacade scrumFacade;
    private ExpandableListView layoutSprintList;
    private ExpandableListAdapter sprintListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprint_list);

        scrumFacade = new ScrumFacade(this);
        layoutSprintList = (ExpandableListView) findViewById(R.id.sprintList);
        List<Sprint> sprintList = scrumFacade.loadAllSprintsFromDataBase();
        sprintListAdapter = new SprintListAdapter(this);
        layoutSprintList.setAdapter(sprintListAdapter);
    }
}
