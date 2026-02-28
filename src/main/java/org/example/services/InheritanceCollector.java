package org.example.services;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Map;

public final class InheritanceCollector
        extends VoidVisitorAdapter<Map<String, String>> {

    @Override
    public void visit(ClassOrInterfaceDeclaration decl, Map<String, String> inheritanceMap) {
        super.visit(decl, inheritanceMap);

        String className = decl.getNameAsString();

        if (decl.getExtendedTypes().isNonEmpty()) {
            // Java only allows one superclass
            String parent = decl.getExtendedTypes(0).getNameAsString();
            inheritanceMap.put(className, parent);
        } else {
            // implicit Object
            inheritanceMap.put(className, "Object");
        }
    }
}