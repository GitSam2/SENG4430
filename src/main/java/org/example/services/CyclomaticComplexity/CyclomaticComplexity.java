package org.example.services.CyclomaticComplexity;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclomaticComplexity {

    public void caclCyclomaticComplexity(List<ParseResult<CompilationUnit>> parsedFiles){

        parsedFiles.forEach(result -> {
            if (result.isSuccessful() && result.getResult().isPresent()) {
                CompilationUnit cU = result.getResult().get();

                List<MethodDeclaration> list = cU.accept(new FunctionFinder(), null);
                for (MethodDeclaration method : list){
                    AtomicInteger atomicInteger = new AtomicInteger(0);
                    System.out.println(method);
                    method.accept(new ComplexityVisitor(), atomicInteger);
                    System.out.println(" Score: " + atomicInteger.get());
                }
            }
        });
//        return atomicInteger.get();
    }

    public List<CyclomaticComplexityResult> calculateCyclomaticComplexity(List<ParseResult<CompilationUnit>> parsedFiles, int warning, int severe){
        List<CyclomaticComplexityResult> cyclomaticComplexityList = new ArrayList<>();

        parsedFiles.forEach(result -> {
            if (result.isSuccessful() && result.getResult().isPresent()) {
                CompilationUnit cU = result.getResult().get();

                String filename = cU.getStorage()
                        .map(CompilationUnit.Storage::getFileName)
                        .orElse("Unknown File");

                double averageScore = 0.;
                int highestScore = 0;
                String highestScoreString = "";
                CyclomaticComplexityResult.Severity severity = CyclomaticComplexityResult.Severity.ERROR;

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
                    severity = CyclomaticComplexityResult.Severity.WARNING;
                } else if (highestScore < warning) {
                    severity = CyclomaticComplexityResult.Severity.INFO;
                }

                CyclomaticComplexityResult unitResult = new CyclomaticComplexityResult(
                        filename,
                        averageScore,
                        highestScore,
                        highestScoreString,
                        severity
                        );

                cyclomaticComplexityList.add(unitResult);
            }
        });

        return cyclomaticComplexityList;
    }
}
