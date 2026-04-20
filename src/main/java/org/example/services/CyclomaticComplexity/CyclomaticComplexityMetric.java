package org.example.services.CyclomaticComplexity;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.example.services.Metric;
import org.example.services.MetricContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclomaticComplexityMetric implements Metric<CyclomaticComplexityResult> {
    private final int warning;
    private final int severe;

    public CyclomaticComplexityMetric() {
        this.warning = 10;
        this.severe = 15;
    }

    @Override
    public String id() { return "cc";}

    @Override
    public CyclomaticComplexityResult compute(MetricContext ctx) {
        List<CCContext> cyclomaticComplexityList = new ArrayList<>();

        ctx.compilationUnits().forEach(cU -> {
            String filename = cU.getStorage()
                    .map(CompilationUnit.Storage::getFileName)
                    .orElse("Unknown File");

            Double averageScore = 0.;
            int highestScore = 0;
            String highestScoreString = "";
            CCContext.Severity severity = CCContext.Severity.ERROR;

            List<MethodDeclaration> list = cU.accept(new FunctionFinder(), null);
            for (MethodDeclaration method : list){
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
                severity = CCContext.Severity.WARNING;
            } else if (highestScore < warning) {
                severity = CCContext.Severity.INFO;
            }

            CCContext unitResult = new CCContext(
                    filename,
                    averageScore,
                    highestScore,
                    highestScoreString,
                    severity
            );
            cyclomaticComplexityList.add(unitResult);

        });
        return new CyclomaticComplexityResult(cyclomaticComplexityList);
    }

}
