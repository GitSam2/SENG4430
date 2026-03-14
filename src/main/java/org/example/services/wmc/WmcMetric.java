package org.example.services.wmc;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.example.services.MetricContext;

import java.util.ArrayList;
import java.util.List;

public class WmcMetric {

    public WmcResult compute(MetricContext ctx) {

        List<Integer> wmcValues = new ArrayList<>();

        for (CompilationUnit cu : ctx.getCompilationUnits()) {

            List<ClassOrInterfaceDeclaration> classes =
                    cu.findAll(ClassOrInterfaceDeclaration.class);

            for (ClassOrInterfaceDeclaration cls : classes) {

                int methodCount = cls.getMethods().size();

                System.out.println("Class: " + cls.getNameAsString()
                        + " | WMC: " + methodCount);

                wmcValues.add(methodCount);
            }
        }

        return new WmcResult(wmcValues);
    }

}
