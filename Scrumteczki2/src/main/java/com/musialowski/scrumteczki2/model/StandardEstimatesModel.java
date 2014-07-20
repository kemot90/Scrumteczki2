package com.musialowski.scrumteczki2.model;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.musialowski.scrumteczki2.EstimateComparator;

/**
 * Created by Tomek on 14.10.13.
 */
public class StandardEstimatesModel {
    private static Estimate[] estimates;
    private static boolean isInitialized = false;
    private static void init() {
        List<Estimate> estimatesList = new LinkedList<>();
        estimatesList.add(Estimate.createInstanceByHours(0f));
        estimatesList.add(Estimate.createInstanceByHours(0.5f));
        estimatesList.add(Estimate.createInstanceByHours(1f));
        estimatesList.add(Estimate.createInstanceByHours(2f));
        estimatesList.add(Estimate.createInstanceByHours(3f));
        estimatesList.add(Estimate.createInstanceByHours(5f));
        estimatesList.add(Estimate.createInstanceByHours(8f));
        estimatesList.add(Estimate.createInstanceByHours(12f));
        estimatesList.add(Estimate.createInstanceByHours(16f));
        estimatesList.add(Estimate.createInstanceByDays((short) 3));
        estimatesList.add(Estimate.createInstanceByDays((short) 5));
        estimatesList.add(Estimate.createInstanceByDays((short) 10));
        estimatesList.add(Estimate.createInstanceByDays((short) 20));
        estimatesList.add(Estimate.createInstanceByDays((short) 30));
        estimatesList.add(Estimate.createInstanceByDays((short) 40));
        estimatesList.add(Estimate.createInstanceByDays((short) 50));
        estimatesList.add(Estimate.createInstanceInfinity());

        Collections.sort(estimatesList, new EstimateComparator());

        estimates = (Estimate[]) Array.newInstance(Estimate.class, estimatesList.size());
        for (int i = 0; i < estimatesList.size(); i++) {
            estimates[i] = estimatesList.get(i);
        }

        isInitialized = true;
    }

    public static Estimate[] getEstimates() {
        if (!isInitialized) {
            init();
        }

        return estimates;
    }
}
