package org.example.services.wmc;

import java.util.ArrayList;
import java.util.List;

import org.example.services.Metric;
import org.example.services.MetricContext;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class WmcMetric implements Metric<WmcResult> {

    @Override
    public String id() {
        return "wmc";
    }

    public WmcResult compute(MetricContext ctx) {

        List<Integer> wmcValues = new ArrayList<>();

        for (CompilationUnit cu : ctx.compilationUnits()) {

            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);

            for (ClassOrInterfaceDeclaration cls : classes) {

                int methodCount = cls.getMethods().size();

               /*  System.out.println("Class: " + cls.getNameAsString()
                        + " | WMC: " + methodCount); */
                
                wmcValues.add(methodCount);
            }
        }

        return new WmcResult(wmcValues);
    }
    public List<String> computeStrings(MetricContext ctx){
        List<String> resultStrings = new ArrayList<>();
        for (CompilationUnit cu : ctx.compilationUnits()) {
            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
            for (ClassOrInterfaceDeclaration cls : classes) {
                int methodCount = cls.getMethods().size();
                String line = "Class: " + cls.getNameAsString() + "| WMC: " + methodCount;
                resultStrings.add(line);
            }
        }
        return resultStrings;
        
    }

}
