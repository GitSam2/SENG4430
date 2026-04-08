package org.example.services.NestedDepth;

import java.util.ArrayList;
import java.util.List;
import org.example.picocli.Console;


import org.example.services.Result;

public class LoopMetrics implements Result {
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
        if (depth > 6) {
            flaggedCount++;
            flaggedFiles.add(filename);
        }
    }

    public double average() {
        return loopCount == 0 ? 0 : (double) totalDepth / loopCount;
    }

    @Override
    public String output() 
    {
        String max = flaggedFiles.get(0);
        int length = max.length();
        for(int i=0; i<flaggedFiles.size(); i++)
        {
            if(flaggedFiles.get(i).length() > length ){
                length = flaggedFiles.get(i).length();
            }
        }

        String[] headers =  {"Files that exceeded threshold"};
        int[] width = {headers[0].length()+2};
        String[][] rows = new String[flaggedFiles.size()][headers.length];
        for (int i = 0; i < flaggedFiles.size(); i++) {
            rows[i][0] = flaggedFiles.get(i);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Console.divider('-')));
        sb.append(String.format("\nLOOP DEPTH ANALYSIS \n"));
        sb.append(String.format(Console.divider('-')));
        sb.append(String.format("\nNested Loops That Exceed A Depth Of 6 Are Considered A Code Smell And Are Flagged \n"));
        sb.append(String.format(Console.divider('-')));
        sb.append(String.format("\nNumber of flagged files: " + flaggedCount + "\n"));
        sb.append(String.format("Total number of loops: " + loopCount + "\n"));
        sb.append(String.format("Max loop depth: " + maxDepth + "\n"));
        sb.append(String.format("Average loop depth: " + average() + "\n"));


        if (flaggedCount > 0) {
            Console.error("Some loops exceeded the warning threshold!");
        }
        else
        {
            Console.success("No flagged loops");

        }
        sb.append(String.format(Console.divider('─')));
        sb.append(String.format("\n"));
        sb.append(String.format(Console.table(headers, width, rows)));
        return sb.toString();

    }
}