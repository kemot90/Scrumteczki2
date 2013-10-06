package pl.kemot.scrum.scrumteczki2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        List<Estimate> estimatesList = new LinkedList<Estimate>();
        estimatesList.add(Estimate.createInstanceByHours(0f));
        estimatesList.add(Estimate.createInstanceByHours(0.5f));
        estimatesList.add(Estimate.createInstanceByHours(2f));
        estimatesList.add(Estimate.createInstanceByHours(1f));
        estimatesList.add(Estimate.createInstanceByDays((short) 3));

        Estimate[] estimates = (Estimate[]) Array.newInstance(Estimate.class, estimatesList.size());
        for (int i = 0; i < estimatesList.size(); i++) {
            estimates[i] = estimatesList.get(i);
        }

        EstimateAdapter estimateAdapter = new EstimateAdapter(MainActivity.this, R.layout.main, estimates);
        estimateAdapter.sort(new EstimateComparator());

        Spinner estimateLeft = (Spinner) findViewById(R.id.estimateLeft);
        estimateLeft.setAdapter(estimateAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
