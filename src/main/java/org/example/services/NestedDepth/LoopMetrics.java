package org.example.services.NestedDepth;

public class LoopMetrics {
    public int maxDepth = 0;
    public int minDepth = Integer.MAX_VALUE;
    public int totalDepth = 0;
    public int loopCount = 0;
    public int flaggedCount = 0;

    void record(int depth) {
        loopCount++;
        totalDepth += depth;
        maxDepth = Math.max(maxDepth, depth);
        minDepth = Math.min(minDepth, depth);
        if (depth > 6) flaggedCount++;
    }

    public double average() {
        return loopCount == 0 ? 0 : (double) totalDepth / loopCount;
    }
}