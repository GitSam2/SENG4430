package org.example;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.DependencyNode;

public class TreePrinter {

    public static void print(DependencyTree tree) {
        System.out.println("=== " + tree.model().getArtifactId() + " ===");
        printNode(tree.root(), "");
    }

    private static void printNode(DependencyNode node, String indent) {
        System.out.println(indent + format(node));
        for (DependencyNode child : node.getChildren()) {
            printNode(child, indent + "  ");
        }
    }

    private static String format(DependencyNode node) {
        Artifact a = node.getArtifact();
        return a.getGroupId() + ":" + a.getArtifactId() + ":" + a.getVersion();
    }
}

