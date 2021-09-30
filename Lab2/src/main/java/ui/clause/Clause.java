package ui.clause;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clause class representing a clause.
 * Clause can be protected and then it won't be deleted after a problem solve.
 *
 * @author MatejCubek
 * @project UILab2
 * @created 07/04/2021
 */
public class Clause {
    private final List<Literal> clauseElements;
    /**
     * Combination of clause this clause is made from or <code>null</code> if it is not generated.
     */
    private ClauseNumberCombination clauseNumberCombination;
    /**
     * If clause is protected it won't be deleted in optimization.
     */
    private boolean protectedClause;

    public Clause(Clause index1, Clause index2) {
        this();
        clauseNumberCombination = new ClauseNumberCombination(index1, index2);
        protectedClause = false;
    }

    public Clause() {
        clauseElements = new ArrayList<>();
        protectedClause = true;
    }

    public Clause(Literal... literals) {
        this();
        Collections.addAll(clauseElements, literals);
    }

    public ClauseNumberCombination getClauseNumberCombination() {
        return clauseNumberCombination;
    }

    public boolean isProtectedClause() {
        return protectedClause;
    }

    public void addClauseElement(Literal element) {
        clauseElements.add(element);
    }

    public int getSize() {
        return clauseElements.size();
    }

    public List<Literal> getClauseElements() {
        return clauseElements;
    }

    /**
     * Method for checking if clause only contains one element, the NIL Literal.
     *
     * @return true if clause is NIL Literal
     */
    public boolean containsNil() {
        return clauseElements.size() == 1 && clauseElements.get(0).equals(Literal.NIL_LITERAL);
    }

    /**
     * Method for checking if clause contains negated literal from parameter.
     *
     * @param literal literal wich negation is searched for.
     * @return <code>true</code> if clause contains negated literal else <code>false</code>
     */
    public boolean containsNegatedLiteral(Literal literal) {
        for (var element : clauseElements) {
            if (literal.equalsNegated(element)) return true;
        }
        return false;
    }

    /**
     * Method for checking if this clause contains any negated literal from another clause.
     *
     * @param clause other clause in which negated literals are searched for
     * @return literal if found else <code>null</code>
     */
    public Literal getAnyNegatedLiteralFromClause(Clause clause) {
        for (var element : clauseElements) {
            if (clause.containsNegatedLiteral(element)) return element;
        }
        return null;
    }

    /**
     * Method for checking if clause matches other clause in all literals.
     * Negated literals do not count.
     *
     * @param clause other clause to check literals
     * @return <code>true</code> if literals match else <code>false</code>
     */
    public boolean containsAllLiteralsFrom(Clause clause) {
        for (Literal l : clause.clauseElements) {
            if (! clauseElements.contains(l)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return clauseElements.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" v "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clause clause = (Clause) o;

        return clauseElements.equals(clause.clauseElements);
    }

    @Override
    public int hashCode() {
        return clauseElements.hashCode();
    }

    /**
     * Method that checks if clause is unimportant.
     * Clause is unimportant if it is valid.
     *
     * @return true if clause is unimportant else false
     */
    public boolean isUnimportant() {
        for (Literal first : clauseElements) {
            for (Literal second : clauseElements) {
                if (first == second) continue;
                if (first.equalsNegated(second)) return true;
            }
        }
        return false;
    }
}
