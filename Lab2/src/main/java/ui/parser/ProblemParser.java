package ui.parser;

import ui.IProblem;
import ui.clause.Clause;
import ui.clause.ClauseNumberCombination;
import ui.clause.Literal;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Parser of problem file and repository of calculations.
 * Class implements {@link IProblem} interface with methods for
 * solving problem.
 *
 * @author MatejCubek
 * @project UILab2
 * @created 07/04/2021
 */
public class ProblemParser implements IProblem {
    private final LinkedList<Clause> clauses;
    /**
     * Clause for which problem is solved.
     */
    private Clause queryClause;
    /**
     * Index of last clause before calculation.
     */
    private int globalTopSize;
    /**
     * Number of query clauses decomposed after negation.
     */
    private int queryOnTopSize = 0;

    public ProblemParser(Path inputClausesPath) throws IOException {
        clauses = new LinkedList<>();
        parseClauses(inputClausesPath);
    }

    /**
     * Private helper method for parsing clauses from input file.
     *
     * @param inputClausesPath path containing input file
     * @throws IOException thrown when problem occurs while parsing input file
     */
    private void parseClauses(Path inputClausesPath) throws IOException {
        try (BufferedReader buf = Files.newBufferedReader(inputClausesPath)) {
            String line;
            while ((line = buf.readLine()) != null) {
                if (line.startsWith("#")) continue;

                clauses.add(parseClause(line));
            }
            globalTopSize = clauses.size();
        }
    }

    /**
     * Public static method for parsing predifined line into a clause.
     *
     * @param line line in predifined format
     * @return new clause from parsed line
     */
    public Clause parseClause(String line) {
        String[] lineArray = line.split(" ");
        Clause clause = new Clause();

        for (var element : lineArray) {
            if (element.equalsIgnoreCase("v")) continue;

            boolean isNegated = element.startsWith("~");

            Literal literal = new Literal(element.replace("~", ""), isNegated);
            clause.addClauseElement(literal);
        }
        return clause;
    }

    @Override
    public void parseAndAddClause(String line) {
        addClause(parseClause(line));
    }

    @Override
    public void parseAndRemoveClause(String line) {
        removeClause(parseClause(line));
    }

    @Override
    public List<Clause> getClauses() {
        return clauses;
    }

    @Override
    public void addClause(Clause clause) {
        clauses.add(clause);
    }

    @Override
    public void removeExcessClauses() {
        while (clauses.size() != globalTopSize - queryOnTopSize) {
            clauses.removeLast();
        }
    }

    @Override
    public void removeClause(Clause clause) {
        clauses.remove(clause);
    }

    @Override
    public void negateFinish() {
        queryClause = clauses.removeLast();
        queryOnTopSize = 0;
        for (Literal literal : queryClause.getClauseElements()) {
            queryOnTopSize++;
            Literal newLiteral = literal.negateAndCopy();
            clauses.add(new Clause(newLiteral));
        }
        globalTopSize = clauses.size();
    }

    @Override
    public Clause getQuery() {
        return queryClause;
    }

    @Override
    public int getQueryClauseNumber() {
        return queryOnTopSize;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < globalTopSize; i++) {
            Clause clause = clauses.get(i);

            sb.append(i + 1).append(". ").append(clause.toString()).append("\n");
        }
        sb.append("===============\n");
        appendRemainingClauses(sb);
         return sb.toString();
    }

    private int getStartingClauseIndex(Clause clause) {
        int index = clauses.indexOf(clause);
        if (index < 0 || index >= globalTopSize) return - 1;
        return index+1;
    }

    private void appendRemainingClauses(StringBuilder sb) {
        if (! clauses.getLast().containsNil()) return;

        AtomicInteger integer = new AtomicInteger(globalTopSize+1);
        recursiveClauseAppend(sb, integer, clauses.getLast());
        sb.append("===============\n");
    }

    private int recursiveClauseAppend(StringBuilder sb, AtomicInteger row, Clause clause) {
        int startingIndex = getStartingClauseIndex(clause);
        if (startingIndex != - 1) return startingIndex;

        ClauseNumberCombination combination = clause.getClauseNumberCombination();
        if (combination == null) return - 1;
        Clause first = combination.getFirst();
        Clause second = combination.getSecond();

        int firstIndex = recursiveClauseAppend(sb, row, first);
        int secondIndex = recursiveClauseAppend(sb, row, second);

        int myIndex = row.getAndIncrement();
        sb.append(myIndex).append(". ");
        sb.append(clause).append(String.format(" (%d,%d)\n", firstIndex, secondIndex));
        return myIndex;
    }
}
