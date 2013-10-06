package pl.kemot.scrum.scrumteczki2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Tomek on 05.10.13.
 */
public class EstimateAdapter extends ArrayAdapter<Estimate> { //TODO: zrobić koszerny adapter z Layout Inflaterem
    private Estimate[] estimates;
    private Context context;

    public EstimateAdapter(Context context, int resource, Estimate[] estimates) {
        super(context, resource, estimates);
        this.estimates = estimates;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView labelText = new TextView(context);
        String label = estimates[position].getLabel();
        labelText.setText(label);

        return labelText;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView labelText = new TextView(context);
        String label = estimates[position].getLabel();
        labelText.setText(label);

        return labelText;
    }

    @Override
    public Estimate getItem(int position) {
        return estimates[position];
    }
}
