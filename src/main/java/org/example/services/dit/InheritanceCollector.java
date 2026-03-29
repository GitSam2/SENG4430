package org.example.services.dit;

import java.util.Map;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/*
    * This class extends the VoidVisitorAdapter, getting default
    * implementations of visit methods. This allows us to get details
    * of the visit (in this case, class name and parent class name) 
    * and store it in the inheritance map.
 */
public final class InheritanceCollector
        extends VoidVisitorAdapter<Map<String, String>> {

    @Override
    public void visit(ClassOrInterfaceDeclaration decl, Map<String, String> inheritanceMap) {
        // perform actual visit using cu visit method
        super.visit(decl, inheritanceMap);

        // get details of the visit for the inheritance map
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