package org.example.services.dependencies;

import java.util.List;

import org.example.services.Result;
import org.example.utils.Console;

public class DependencyResult implements Result {
    private final List<NodeResult> nodeResults;

    public DependencyResult(List<NodeResult> nodeResults) {
        this.nodeResults = nodeResults;
    }

    public List<NodeResult> nodeResults() {
        return nodeResults;
    }

    @Override
    public String output() {
        String[] headers = {
            "Dependency",
            "Transitive?",
            "CVE?",
            "CVSS Score",
            "Fixed Version"
        };

        int[] widths = {
                Console.terminalWidth() - 42,
                12,
                5,
                11,
                14 
        };

        String[][] rows = new String[nodeResults.size()][headers.length];

        for (int i = 0; i < nodeResults.size(); i++) {
            NodeResult nodeResult = nodeResults.get(i);
            rows[i][0] = nodeResult.id();
            rows[i][1] = nodeResult.isDirectDependency() ? "No" : "Yes";
            if (nodeResult.hasCve()) {
                rows[i][2] = Console.boldRed("Yes");
                rows[i][3] = String.valueOf(nodeResult.severity());
                if (nodeResult.fixedVersion() != null) {
                    rows[i][4] = Console.yellow(nodeResult.fixedVersion());
                } else {
                    rows[i][4] = Console.boldRed("None");
                }
                
            } else {
                rows[i][2] = Console.boldGreen("No");
                rows[i][3] = "N/A";
                rows[i][4] = "N/A";
            }
            
        }

        return Console.table(headers, widths, rows);
    }
}