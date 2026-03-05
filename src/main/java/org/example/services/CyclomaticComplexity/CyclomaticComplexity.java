package org.example.services.CyclomaticComplexity;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

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
}
