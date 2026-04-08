package org.example.services.wmc;

import java.util.ArrayList;
import java.util.List;

import org.example.picocli.Console;


import org.example.services.Result;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class WmcResult implements Result {

    private final List<Integer> wmcValues;
    private final List<ClassOrInterfaceDeclaration> wmcClassList;

    public WmcResult(List<Integer> wmcValues, List<ClassOrInterfaceDeclaration> wmcClassList) {
        this.wmcValues = wmcValues;
        this.wmcClassList = wmcClassList;
    }

    public double getMeanWMC() {

        if (wmcValues.isEmpty()) {
            return 0;
        }

        int sum = 0;

        for (int value : wmcValues) {
            sum += value;
        }

        return (double) sum / wmcValues.size();
    }

    public List<String> getClasses(List<ClassOrInterfaceDeclaration> wmcClassList)
    {
        List<String> classString = new ArrayList();
        for(ClassOrInterfaceDeclaration cls : wmcClassList)
        {
            classString.add(cls.getNameAsString());
        }
        return classString;
    } 

    @Override
    public String output() 
    {
        StringBuilder sb = new StringBuilder();
        List<String[]> rowsList = new ArrayList<>();
        for(int i = 0; i< getClasses(wmcClassList).size(); i++)
        {
            rowsList.add(new String[] {getClasses(wmcClassList).get(i), String.valueOf(wmcValues.get(i))});
        }
        String[][] rows = rowsList.toArray(new String[0][]);
        int maxClassLength = "class".length();
        for (String[] row : rows){
            maxClassLength = Math.max(maxClassLength, row[0].length());
        }
        String[] headers = { "Class", "WMC" };
        int[] widths = { maxClassLength + 2, 10};
        sb.append(String.format(Console.table(headers, widths, rows)));

        sb.append(String.format(("Average WMC: "+ getMeanWMC())));    
        return sb.toString();
    }
}
