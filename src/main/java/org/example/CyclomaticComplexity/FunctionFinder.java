package org.example.CyclomaticComplexity;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class FunctionFinder extends GenericListVisitorAdapter<MethodDeclaration, Void> {
    @Override
    public List<MethodDeclaration> visit(MethodDeclaration n, Void args) {
        super.visit(n, args);
        List<MethodDeclaration> methods = super.visit(n, args);
        if (n.getBody().isPresent()) {
            methods.add(n);
        }

        return methods;
    }
}
