package org.example.CyclomaticComplexity;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.concurrent.atomic.AtomicInteger;

public class ComplexityVisitor extends VoidVisitorAdapter<AtomicInteger> {
    @Override
    public void visit(MethodDeclaration n, AtomicInteger complexity) {
        complexity.set(1);
        super.visit(n, complexity);
    }

    private void increment(AtomicInteger complexity) {
        complexity.incrementAndGet();
    }

    @Override public void visit(IfStmt n, AtomicInteger complexity) { increment(complexity); super.visit(n, complexity); }
    @Override public void visit(ForStmt n, AtomicInteger complexity) { increment(complexity); super.visit(n, complexity); }
    @Override public void visit(ForEachStmt n, AtomicInteger complexity) { increment(complexity); super.visit(n, complexity); }
    @Override public void visit(WhileStmt n, AtomicInteger complexity) { increment(complexity); super.visit(n, complexity); }
    @Override public void visit(DoStmt n, AtomicInteger complexity) { increment(complexity); super.visit(n, complexity); }
    @Override public void visit(SwitchEntry n, AtomicInteger complexity) {
        if (n.getLabels().isNonEmpty()) increment(complexity);
        super.visit(n, complexity);
    }
    @Override public void visit(CatchClause n, AtomicInteger complexity) { increment(complexity); super.visit(n, complexity); }
    @Override public void visit(ConditionalExpr n, AtomicInteger complexity) { increment(complexity); super.visit(n, complexity); }

    // Logical Operators: && and ||
    @Override public void visit(BinaryExpr n, AtomicInteger complexity) {
        if (n.getOperator() == BinaryExpr.Operator.AND || n.getOperator() == BinaryExpr.Operator.OR) {
            increment(complexity);
        }
        super.visit(n, complexity);
    }
}
