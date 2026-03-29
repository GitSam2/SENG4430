package org.example.services.wmc;

import java.util.List;

import org.example.services.Result;

public class WmcResult implements Result {

    private final List<Integer> wmcValues;

    public WmcResult(List<Integer> wmcValues) {
        this.wmcValues = wmcValues;
    }

    public double getMeanWMC() {

        if (wmcValues.isEmpty()) {
            return 0;
        }

        int sum = 0;

        for (int value : wmcValues) {
            sum += value;
        }

        return (double) sum / wmcValues.size();
    }

    @Override
    public String output() {
        return "Mean WMC: " + getMeanWMC();
    }
}
