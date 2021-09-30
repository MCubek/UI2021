package ui;

import ui.clause.Clause;

import java.util.List;

/**
 * Interface used for solving problem.
 *
 * @author MatejCubek
 * @project UILab2
 * @created 07/04/2021
 */
public interface IProblem {
    /**
     * Get list of all clauses in problem.
     *
     * @return list of all clauses in problem.
     */
    List<Clause> getClauses();

    /**
     * Method parses line in predifined format and add clause to
     * list of clauses.
     * Clause added this way will be protected and won't be removed after
     * problem is solved.
     *
     * @param line line to parse and add in predifined format.
     */
    void parseAndAddClause(String line);

    /**
     * Method parses line in predifined format and removes clause
     * in list of clauses that matches it.
     * Clause removed this way will be protected and won't be removed after
     * problem is solved.
     *
     * @param line line to parse in predifined format and removed.
     */
    void parseAndRemoveClause(String line);

    /**
     * Method negates query.
     */
    void negateFinish();

    /**
     * Method adds clause to clauses.
     * Clauses added this way won't be protected.
     *
     * @param clause clause to add
     */
    void addClause(Clause clause);

    /**
     * Method removes clause from clauses.
     *
     * @param clause clause to add
     */
    void removeClause(Clause clause);

    /**
     * Method removes all unused clauses after problem solved.
     */
    void removeExcessClauses();

    /**
     * Method gets query clause for which problem is solved.
     *
     * @return query.
     */
    Clause getQuery();

    /**
     * Get number of clauses that are searched.
     *
     * @return number of clauses
     */
    int getQueryClauseNumber();
}
