package org.example.services.NestedDepth;

import java.util.ArrayList;
import java.util.*;

public class LoopMetrics {
    public int maxDepth = 0;
    public int minDepth = Integer.MAX_VALUE;
    public int totalDepth = 0;
    public int loopCount = 0;
    public int flaggedCount = 0;
    public List<String> flaggedFiles = new ArrayList<>();

    void record(int depth, String filename) {
        loopCount++;
        totalDepth += depth;
        maxDepth = Math.max(maxDepth, depth);
        minDepth = Math.min(minDepth, depth);
        if (depth > 6){flaggedCount++; flaggedFiles.add(filename);}
    }

    public double average() {
        return loopCount == 0 ? 0 : (double) totalDepth / loopCount;
    }
}