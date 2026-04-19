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

    @Override // we only override the visit method for ClassOrInterfaceDeclaration as we only
              // care about classes and interfaces
    public void visit(ClassOrInterfaceDeclaration decl, Map<String, String> inheritanceMap) {
        // this is needed for nested/inner classes within a single file to be discovered
        super.visit(decl, inheritanceMap);

        // get details of the visit for the inheritance map
        String className = decl.getNameAsString();

        if (decl.getExtendedTypes().isNonEmpty()) { // checks if there is a parent class
            String parent = decl.getExtendedTypes(0).getNameAsString();
            inheritanceMap.put(className, parent);
        } else {
            // if no parent, then we assume it is at the root "Object"
            inheritanceMap.put(className, "Object");
        }
    }
}
