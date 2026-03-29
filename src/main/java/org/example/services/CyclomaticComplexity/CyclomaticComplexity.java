package org.example.services.CyclomaticComplexity;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.services.Metric;
import org.example.services.MetricContext;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class CyclomaticComplexity implements Metric<CyclomaticComplexityResult> {

    @Override
    public String id() {
        return "cc";
    }

    @Override
    public CyclomaticComplexityResult compute(MetricContext ctx) {
        CyclomaticComplexityResult result = calculateCyclomaticComplexity(ctx);
        return result;
    }

    private CyclomaticComplexityResult calculateCyclomaticComplexity(MetricContext ctx) {
        int warning = 10;
        int severe = 20;
        List<CyclomaticComplexityFileResult> fileResults = new java.util.ArrayList<>();

        for (CompilationUnit cu : ctx.compilationUnits()) {
            String filename = cu.getStorage()
                    .map(CompilationUnit.Storage::getFileName)
                    .orElse("Unknown File");
            double averageScore = 0.;
            int highestScore = 0;
            String highestScoreString = "";

            CyclomaticComplexityFileResult.Severity severity = CyclomaticComplexityFileResult.Severity.ERROR;

            List<MethodDeclaration> list = cu.accept(new FunctionFinder(), null);
            for (MethodDeclaration method : list) {
                AtomicInteger atomicInteger = new AtomicInteger(0);
                method.accept(new ComplexityVisitor(), atomicInteger);

                if (atomicInteger.get() > highestScore) {
                    highestScore = atomicInteger.get();
                    highestScoreString = method.getNameAsString();
                }

                averageScore += atomicInteger.get();
            }

            averageScore /= list.size();

            if (highestScore >= warning && highestScore < severe) {
                severity = CyclomaticComplexityFileResult.Severity.WARNING;
            } else if (highestScore < warning) {
                severity = CyclomaticComplexityFileResult.Severity.INFO;
            }

            fileResults.add(new CyclomaticComplexityFileResult(
                    filename,
                    averageScore,
                    highestScore,
                    highestScoreString,
                    severity));
        }

        return new CyclomaticComplexityResult(fileResults);
    }
}
