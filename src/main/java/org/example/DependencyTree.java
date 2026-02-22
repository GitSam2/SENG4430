package org.example;

import org.apache.maven.model.Model;
import org.eclipse.aether.graph.DependencyNode;

/// Model object for representing the tree of dependencies from a maven project.
/// This is needed for maven projects can have many modules and therefore many trees.
public record DependencyTree (Model model, DependencyNode root) {}