package com.musialowski.scrumteczki2.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.musialowski.scrumteczki2.R;
import com.musialowski.scrumteczki2.ScrumteczkiApp;
import com.musialowski.scrumteczki2.adapter.DailyScrumListAdapter;
import com.musialowski.scrumteczki2.persistence.ScrumFacade;

/**
 * Created by Tomek on 12.01.14.
 */
public class DailyScrumActivity extends Activity {
    private ScrumFacade scrumFacade;
    private ScrumteczkiApp application;
    private DailyScrumListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.daily_scrum_list);
        application = (ScrumteczkiApp) getApplicationContext();
        scrumFacade = new ScrumFacade(this);
        adapter = new DailyScrumListAdapter(this, R.layout.task_list_item);
        ListView dailyScrumListView = (ListView) findViewById(R.id.dailyScrumTaskList);
        dailyScrumListView.setAdapter(adapter);
        application.getObservableChangesList().addListener(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        application.getObservableChangesList().removeListener(adapter);
    }

    public void onClickMergeChanges(View view) {
        new MergeConfirmationDialogFragment().show(getFragmentManager(), "Zatwierdź zmiany");
    }

    private class MergeConfirmationDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DailyScrumActivity.this);
            setRetainInstance(true);
            return dialogBuilder
                    .setTitle("Potwierdzenie")
                    .setMessage("Czy jesteś pewien, że chcesz zastosować zmiany w estymowanych czasach zadań?")
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            scrumFacade.mergeChanges();
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
