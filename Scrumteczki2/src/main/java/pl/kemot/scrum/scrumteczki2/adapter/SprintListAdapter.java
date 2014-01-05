package pl.kemot.scrum.scrumteczki2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import pl.kemot.scrum.scrumteczki2.Listener;
import pl.kemot.scrum.scrumteczki2.R;
import pl.kemot.scrum.scrumteczki2.ScrumteczkiApp;
import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.model.Task;

/**
 * Created by Tomek on 03.01.14.
 */
public class SprintListAdapter extends BaseExpandableListAdapter implements Listener {
    private Context context;
    private ScrumteczkiApp application;
    private List<Sprint> sprintList = new LinkedList<>();

    public SprintListAdapter(Context context) {
        if (context == null) {
            throw new NullPointerException("Kontekst nie może być null!");
        }
        this.context = context;
        application = (ScrumteczkiApp) context.getApplicationContext();
        sprintList = application.getObservableSprintList().getSprintList();
    }
    @Override
    public int getGroupCount() {
        return sprintList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sprintList.get(groupPosition).getTasks().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sprintList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sprintList.get(groupPosition).getTasks().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return sprintList.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return sprintList.get(groupPosition).getTasks().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        DateFormat sprintDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sprint_list_group, parent, false);
        }
        TextView sprintNameView = (TextView) convertView.findViewById(R.id.sprintName);
        TextView sprintFileNameView = (TextView) convertView.findViewById(R.id.sprintFileName);
        TextView sprintAddDateView = (TextView) convertView.findViewById(R.id.sprintAddDate);
        Sprint sprint = sprintList.get(groupPosition);
        sprintNameView.setText("Sprint " + (getGroupCount() - groupPosition));
        sprintFileNameView.setText(sprint.getName());
        sprintAddDateView.setText(sprintDateFormat.format(sprint.getAddDate()));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_list_item, parent, false);
        }
        TextView taskLabelView = (TextView) convertView.findViewById(R.id.taskLabel);
        TextView taskEstimatedTimeView = (TextView) convertView.findViewById(R.id.taskEstimatedTime);
        Task task = sprintList.get(groupPosition).getTasks().get(childPosition);
        taskLabelView.setText(task.getLabel());
        taskEstimatedTimeView.setText(task.getEstimatedTime());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    @Override
    public void update() {
        Log.d("TM", "ADAPTER(update): lista danych dla widoku listy została uaktualniona.");
        this.sprintList = application.getObservableSprintList().getSprintList();
        notifyDataSetChanged();
    }
}
