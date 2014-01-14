package pl.kemot.scrum.scrumteczki2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import pl.kemot.scrum.scrumteczki2.R;
import pl.kemot.scrum.scrumteczki2.model.Estimate;

/**
 * Created by Tomek on 05.10.13.
 */
public class EstimateAdapter extends ArrayAdapter<Estimate> {
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        TextView labelText = (TextView) convertView.findViewById(R.id.spinner_item_text);
        String label = estimates[position].getLabel();
        labelText.setText(label);

        return convertView;
    }

    @Override
    public Estimate getItem(int position) {
        return estimates[position];
    }
}
