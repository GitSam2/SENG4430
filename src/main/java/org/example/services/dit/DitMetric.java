package org.example.services.dit;

import com.github.javaparser.ast.CompilationUnit;
import org.example.services.Metric;
import org.example.services.MetricContext;

import java.util.HashMap;
import java.util.Map;

/*
 * This class is uses for getting the Depth of Inheritance Tree (DIT)
 * Definition: DIT is the maximum length from the node (class) to the root of the tree.
 */
public final class DitMetric implements Metric<DitResult> {
    @Override public String id() { return "DIT"; }

    @Override
    public DitResult compute(MetricContext ctx) {
        // Get inheritance
        Map<String, String> inheritanceMap = new HashMap<>();
        InheritanceCollector collector = new InheritanceCollector();
        for (CompilationUnit cu : ctx.compilationUnits()) {
            cu.accept(collector, inheritanceMap);
        }

        // compute DIT per class
        Map<String, Integer> ditByClass = new HashMap<>();

        for (String className : inheritanceMap.keySet()) {
            int dit = computeDit(className, inheritanceMap);
            ditByClass.put(className, dit);
        }
        return new DitResult(ditByClass, 4, 5);
    }

    private int computeDit(String className, Map<String, String> inheritanceMap) {
        // todo... possible improvements with caching here
        String parent = inheritanceMap.get(className);
        if (parent == null) {
            return 0;
        }
        return 1 + computeDit(parent, inheritanceMap);
    }
}
