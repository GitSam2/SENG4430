package NestedDepthTest;

import static org.junit.jupiter.api.Assertions.*;

import org.example.services.ProjectParser;
import org.example.services.NestedDepth.LoopDepthMetric;
import org.example.services.NestedDepth.LoopMetrics;
import org.example.services.MetricContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;


public class NestedDepthMetricTest 
{
    @Test
    void singleClassExample() {
        Path testInputPath = Path.of("src/test/java/NestedDepthTest/singleClassExample");

        ProjectParser parser = new ProjectParser();
        double flaggedCount = 0;

        try 
        {
            MetricContext ctx = new MetricContext(testInputPath, parser.parseProject(testInputPath));
            LoopDepthMetric metric = new LoopDepthMetric();
            LoopMetrics result = metric.compute(ctx);
            flaggedCount = result.flaggedCount;
            System.out.println("Single Class Test");
            System.out.println("Max depth: " + result.maxDepth);
            System.out.println("Flagged count: " + flaggedCount);

        } catch (IOException e) 
        {
            throw new RuntimeException(e);
        }

        assertEquals(1.0, flaggedCount);
    }

    
    @Test
    void multiClassExample() {
        Path testInputPath = Path.of("src/test/java/NestedDepthTest/multiClassExample");

        ProjectParser parser = new ProjectParser();
        double flaggedCount = 0;

        try 
        {
            MetricContext ctx = new MetricContext(testInputPath, parser.parseProject(testInputPath));
            LoopDepthMetric metric = new LoopDepthMetric();
            LoopMetrics result = metric.compute(ctx);
            flaggedCount = result.flaggedCount;
            System.out.println("Multi Class Test");
            System.out.println("Max depth: " + result.maxDepth);
            System.out.println("Flagged count: " + flaggedCount);

        } catch (IOException e) 
        {
            throw new RuntimeException(e);
        }

        assertEquals(3.0, flaggedCount);
    }

    @Test
    void zeroClassExample() {
        Path testInputPath = Path.of("src/test/java/NestedDepthTest/zeroDepthCLass");

        ProjectParser parser = new ProjectParser();
        double flaggedCount = 0;

        try 
        {
            MetricContext ctx = new MetricContext(testInputPath, parser.parseProject(testInputPath));
            LoopDepthMetric metric = new LoopDepthMetric();
            LoopMetrics result = metric.compute(ctx);
            flaggedCount = result.flaggedCount;
            System.out.println("Zero depth Test");
            System.out.println("Max depth: " + result.maxDepth);
            System.out.println("Flagged count: " + flaggedCount);

        } catch (IOException e) 
        {
            throw new RuntimeException(e);
        }

        assertEquals(0.0, flaggedCount);
    }

    @Test
    void DoubleClassExample() {
        Path testInputPath = Path.of("src/test/java/NestedDepthTest/DoubleLoopCLass");

        ProjectParser parser = new ProjectParser();
        double flaggedCount = 0;

        try 
        {
            MetricContext ctx = new MetricContext(testInputPath, parser.parseProject(testInputPath));
            LoopDepthMetric metric = new LoopDepthMetric();
            LoopMetrics result = metric.compute(ctx);
            flaggedCount = result.flaggedCount;
            System.out.println("Double nested class test");
            System.out.println("Max depth: " + result.maxDepth);
            System.out.println("Flagged count: " + flaggedCount);

        } catch (IOException e) 
        {
            throw new RuntimeException(e);
        }

        assertEquals(2.0, flaggedCount);
    }
}
 
    

