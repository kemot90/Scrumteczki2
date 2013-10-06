package pl.kemot.scrum.scrumteczki2;

import java.util.Comparator;

/**
 * Created by Tomek on 05.10.13.
 * Komparator używany do sortowania estymat.
 */
public class EstimateComparator implements Comparator<Estimate> {
    @Override
    public int compare(Estimate leftEstimate, Estimate rightEstimate) {
        return leftEstimate.compareTo(rightEstimate);
    }
}
