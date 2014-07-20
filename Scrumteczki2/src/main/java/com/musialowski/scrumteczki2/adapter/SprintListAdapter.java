package com.musialowski.scrumteczki2.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.musialowski.scrumteczki2.Listener;
import com.musialowski.scrumteczki2.R;
import com.musialowski.scrumteczki2.ScrumteczkiApp;
import com.musialowski.scrumteczki2.model.Changes;
import com.musialowski.scrumteczki2.model.Sprint;
import com.musialowski.scrumteczki2.model.Task;
import com.musialowski.scrumteczki2.persistence.ScrumFacade;

/**
 * Created by Tomek on 03.01.14.
 */
public class SprintListAdapter extends BaseExpandableListAdapter implements Listener {
    private Context context;
    private ScrumteczkiApp application;
    private List<Sprint> sprintList = new LinkedList<>();
    private ScrumFacade scrumFacade;

    public SprintListAdapter(Context context) {
        if (context == null) {
            throw new NullPointerException("Kontekst nie może być null!");
        }
        this.context = context;
        application = (ScrumteczkiApp) context.getApplicationContext();
        sprintList = application.getObservableSprintList().getSprintList();
        scrumFacade = new ScrumFacade(context);
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
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete);
        Sprint sprint = sprintList.get(groupPosition);
        sprintNameView.setText("Sprint " + (getGroupCount() - groupPosition));
        sprintFileNameView.setText(sprint.getName());
        sprintAddDateView.setText(sprintDateFormat.format(sprint.getAddDate()));
        delete.setFocusable(false);
        delete.setTag(sprint);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sprint sprintToDelete = (Sprint) view.getTag();
                new DeleteSprintConfirmationDialogFragment(sprintToDelete)
                        .show(((Activity) context).getFragmentManager(), "Zatwierdź usunięcie!");
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_list_item, parent, false);
        }
        TextView taskLabelView = (TextView) convertView.findViewById(R.id.taskLabel);
        TextView taskProduct = (TextView) convertView.findViewById(R.id.taskProduct);
        TextView taskEstimatedTimeView = (TextView) convertView.findViewById(R.id.taskEstimatedTime);
        TextView newEstimatedTime = (TextView) convertView.findViewById(R.id.taskNewEstimatedTime);
        ImageButton restore = (ImageButton) convertView.findViewById(R.id.restore);
        Task task = sprintList.get(groupPosition).getTasks().get(childPosition);
        taskLabelView.setText(task.getLabel());
        taskProduct.setText(task.getProduct());
        taskEstimatedTimeView.setText(task.getEstimatedTime());
        Changes changes = application.getObservableChangesList().findChangesByTaskId(task.getId());
        if (changes != null) {
            newEstimatedTime.setVisibility(View.VISIBLE);
            newEstimatedTime.setText(changes.getNewEstimatedTimeToCompleteTask());
            restore.setVisibility(View.VISIBLE);
            restore.setFocusable(false);
            restore.setTag(changes);
            restore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Changes changesToRemove = (Changes) view.getTag();
                    scrumFacade.removeChanges(changesToRemove);
                }
            });
        } else {
            newEstimatedTime.setVisibility(View.GONE);
            restore.setVisibility(View.GONE);
        }
        convertView.setTag(task);
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
    private class DeleteSprintConfirmationDialogFragment extends DialogFragment {
        private Sprint sprintToDelete;
        private DeleteSprintConfirmationDialogFragment() {
            super();
        }
        public DeleteSprintConfirmationDialogFragment(Sprint sprint) {
            this();
            sprintToDelete = sprint;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            setRetainInstance(true);
            return dialogBuilder
                    .setTitle("Potwierdzenie")
                    .setMessage("Czy jesteś pewien, że chcesz usunąć Sprint oraz wszystkie związane z nim zadania i zmiany?")
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            scrumFacade.deleteSprint(sprintToDelete);
                        }
                    })
                    .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();
        }
    }
}
