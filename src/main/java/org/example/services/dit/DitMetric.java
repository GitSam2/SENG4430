package org.example.services.dit;

import java.util.HashMap;
import java.util.Map;

import org.example.services.Metric;
import org.example.services.MetricContext;

import com.github.javaparser.ast.CompilationUnit;

/*
 * This class is uses for getting the Depth of Inheritance Tree (DIT)
 * Definition: DIT is the maximum length from the node (class) to the root of the tree.
 */
public final class DitMetric implements Metric<DitResult> {
    @Override
    public String id() {
        return "Depth of Inheritance Tree";
    }

    @Override
    public DitResult compute(MetricContext ctx) {
        // Get inheritance
        Map<String, String> inheritanceMap = new HashMap<>(); // key is class name and value is parent class name
        InheritanceCollector collector = new InheritanceCollector(); // visitor pattern
                                                                     // https://refactoring.guru/design-patterns/visitor
        for (CompilationUnit cu : ctx.compilationUnits()) {
            cu.accept(collector, inheritanceMap); // visits each class and puts name and parent in inheritance map
        }

        // compute DIT per class
        Map<String, Integer> ditByClass = new HashMap<>();
        Map<String, Integer> cache = new HashMap<>();

        for (String className : inheritanceMap.keySet()) {
            int dit = computeDit(className, inheritanceMap, cache);
            ditByClass.put(className, dit);
        }
        return new DitResult(ditByClass, 4, 5);
    }

    private int computeDit(String className, Map<String, String> inheritanceMap, Map<String, Integer> cache) {
        int dit;
        // check if DIT for this class has already been computed, return if it has been
        if (cache.containsKey(className)) {
            return cache.get(className);
        }

        String parent = inheritanceMap.get(className);

        // if parent is null then we are at the root
        if (parent == null) {
            dit = 0;
        } else { // otherwise, DIT is 1 + DIT of parent
            dit = 1 + computeDit(parent, inheritanceMap, cache);
        }

        // store DIT in cache to stop recomputing
        cache.put(className, dit);
        return dit;
    }
}
