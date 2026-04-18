package org.example.services.NestedDepth;

import org.example.services.Metric;
import org.example.services.MetricContext;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public final class LoopDepthMetric implements Metric<LoopMetrics> {

    public static int fileCount = 0;
    @Override
    public String id() {
        return "loop-depth";
    }

    @Override
    public LoopMetrics compute(MetricContext ctx) {

        LoopMetrics metrics = new LoopMetrics();

        for (CompilationUnit cu : ctx.compilationUnits()) {
            String fileName = cu.getStorage().map(storage -> storage.getFileName()).orElse("Unkown file");
            fileCount++;
            VoidVisitorAdapter<Integer> visitor = new VoidVisitorAdapter<>() {

                @Override
                public void visit(ForStmt n, Integer depth) {
                    int newDepth = depth + 1;
                    boolean isFlagged = (newDepth >= 3 && depth < 3);
                    metrics.record(newDepth, fileName, isFlagged);
                    super.visit(n, newDepth);
                }

                @Override
                public void visit(ForEachStmt n, Integer depth) {
                    int newDepth = depth + 1;
                    boolean isFlagged = (newDepth >= 3 && depth < 3);
                    metrics.record(newDepth, fileName, isFlagged);
                    super.visit(n, newDepth);
                }

                @Override
                public void visit(WhileStmt n, Integer depth) {
                    int newDepth = depth + 1;
                    boolean isFlagged = (newDepth >= 3 && depth < 3);
                    metrics.record(newDepth, fileName, isFlagged);
                    super.visit(n, newDepth);
                }

                @Override
                public void visit(DoStmt n, Integer depth) {
                    int newDepth = depth + 1;
                    boolean isFlagged = (newDepth >= 3 && depth < 3);
                    metrics.record(newDepth, fileName, isFlagged);
                    super.visit(n, newDepth);
                }
            };

            visitor.visit(cu, 0);
        }

        return metrics;
    }
}