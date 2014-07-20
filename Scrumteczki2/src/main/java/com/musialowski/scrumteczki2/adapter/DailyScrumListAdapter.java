package com.musialowski.scrumteczki2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.musialowski.scrumteczki2.Listener;
import com.musialowski.scrumteczki2.R;
import com.musialowski.scrumteczki2.ScrumteczkiApp;
import com.musialowski.scrumteczki2.model.Changes;
import com.musialowski.scrumteczki2.model.Task;

/**
 * Created by Tomek on 12.01.14.
 */
public class DailyScrumListAdapter extends ArrayAdapter<Changes> implements Listener {
    private Context context;
    private List<Changes> changesList;
    private ScrumteczkiApp application;

    public DailyScrumListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
        application = (ScrumteczkiApp) context.getApplicationContext();
        changesList = application.getObservableChangesList().getChangesList();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_list_item, parent, false);
        }
        Changes changes = changesList.get(position);
        Task task = changes.getTask();
        TextView taskLabelView = (TextView) convertView.findViewById(R.id.taskLabel);
        TextView taskProduct = (TextView) convertView.findViewById(R.id.taskProduct);
        TextView taskEstimatedTimeView = (TextView) convertView.findViewById(R.id.taskEstimatedTime);
        TextView newEstimatedTime = (TextView) convertView.findViewById(R.id.taskNewEstimatedTime);
        taskLabelView.setText(task.getLabel());
        taskProduct.setText(task.getProduct());
        taskEstimatedTimeView.setText(task.getEstimatedTime());
        newEstimatedTime.setVisibility(View.VISIBLE);
        newEstimatedTime.setText(changes.getNewEstimatedTimeToCompleteTask());
        return convertView;
    }

    @Override
    public int getCount() {
        return changesList.size();
    }

    @Override
    public Changes getItem(int position) {
        return changesList.get(position);
    }

    @Override
    public void update() {
        notifyDataSetChanged();
    }
}
