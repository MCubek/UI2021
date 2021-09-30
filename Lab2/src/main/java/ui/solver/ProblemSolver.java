package ui.solver;

import ui.IProblem;
import ui.clause.Clause;
import ui.clause.Literal;

import java.util.*;

/**
 * Implementation of ProblemSolver
 *
 * @author MatejCubek
 * @project UILab2
 * @created 07/04/2021
 */
public class ProblemSolver implements IProblemSolver {
    private final IProblem problem;

    public ProblemSolver(IProblem problem) {
        this.problem = Objects.requireNonNull(problem);
    }

    @Override
    public String solveAndReturnSolution() {
        List<Clause> clauses = problem.getClauses();

        //SoS stack
        Deque<Clause> toVisit = new LinkedList<>();
        for (int i = 0; i < problem.getQueryClauseNumber(); i++) {
            toVisit.push(clauses.get(clauses.size() - 1 - i));
        }

        Clause first;

        while ((first = toVisit.poll()) != null) {
            //noinspection ForLoopReplaceableByForEach
            for (int j = 0; j < clauses.size(); j++) {
                Clause second = clauses.get(j);
                if (first == second) continue;
                if (first.isUnimportant() || second.isUnimportant()) continue;

                Clause result = resolveClause(first, second);
                if (result == null) continue;

                addClauseIfValid(result, toVisit);

                if (result.containsNil()) return generateFoundResult(problem.getQuery());
            }
        }
        return generateNotFoundResult(problem.getQuery());
    }

    /**
     * Method adding new clause to list of clauses if valid.
     *
     * @param clause  new clause to add
     * @param toVisit SoS stack
     */
    private void addClauseIfValid(Clause clause, Deque<Clause> toVisit) {
        if (problem.getClauses().contains(clause)) return;

        //Skip unimportant clause
        if (clause.isUnimportant()) return;

        checkAndRemoveRedundant(clause, toVisit);

        problem.addClause(clause);
        toVisit.addLast(clause);
    }

    /**
     * Method that checks all clauses in problem against clause in parameter and removes redundant clauses.
     *
     * @param clause  clause to check redundant against
     * @param toVisit SoS stack
     */
    private void checkAndRemoveRedundant(Clause clause, Deque<Clause> toVisit) {
        List<Clause> toDelete = new ArrayList<>();
        for (Clause tempClause : problem.getClauses()) {
            if (! tempClause.isProtectedClause() && tempClause.getSize() >= clause.getSize()
                && tempClause.containsAllLiteralsFrom(clause))
                toDelete.add(tempClause);
        }
        toDelete.forEach(problem::removeClause);
        toDelete.forEach(toVisit::remove);
    }

    private String generateNotFoundResult(Clause query) {
        return String.format("[CONCLUSION]: %s is unknown", query.toString().toLowerCase());
    }

    private String generateFoundResult(Clause query) {
        return String.format("[CONCLUSION]: %s is true", query.toString().toLowerCase());
    }

    /**
     * Method that resolves two clauses and returns resolvent.
     * If clauses resolve into NILL that clause is returned.
     *
     * @param first  first clause
     * @param second second clause
     * @return resolvent clause
     */
    private Clause resolveClause(Clause first, Clause second) {
        Literal removable = first.getAnyNegatedLiteralFromClause(second);

        if (removable == null) return null;

        Clause clause = new Clause(first, second);
        for (Literal l : first.getClauseElements()) {
            if (l.getName().equals(removable.getName())) continue;

            clause.addClauseElement(l);
        }
        for (Literal l : second.getClauseElements()) {
            if (l.getName().equals(removable.getName())) continue;

            clause.addClauseElement(l);
        }

        if (clause.getSize() == 0) clause.addClauseElement(Literal.NIL_LITERAL);

        return clause;
    }

}
