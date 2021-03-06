package com.musialowski.scrumteczki2;

import java.util.Comparator;

import com.musialowski.scrumteczki2.model.Estimate;

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
