package ui.program;

import ui.IProblem;
import ui.solver.IProblemSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Problem 2 in lab.
 * Used for cooking problem.
 *
 * @author MatejCubek
 * @project UILab2
 * @created 07/04/2021
 */
public class InteractiveResolutionProgram implements IProgram {
    private final IProblem problem;
    private final IProblemSolver solver;
    private final Path inputPath;

    public InteractiveResolutionProgram(IProblem problem, IProblemSolver solver, Path inputsPath) {
        this.problem = problem;
        this.solver = solver;
        this.inputPath = inputsPath;
    }

    @Override
    public void execute() {
        try (BufferedReader br = Files.newBufferedReader(inputPath)) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue;
                Matcher matcher = Pattern.compile("([?+-])").matcher(line);
                if (! matcher.find()) continue;
                String operator = matcher.group(1);

                String clause = line.replaceAll("[?+-]", "").trim();

                switch (operator) {
                    case "+" -> problem.parseAndAddClause(clause);
                    case "-" -> problem.parseAndRemoveClause(clause);
                    case "?" -> handleQuery(clause);
                }
            }

        } catch (IOException e) {
            System.err.println("ERROR While executing.");
            e.printStackTrace();
        }
    }

    /**
     * Helper method handles query.
     *
     * @param clause clause for query
     */
    private void handleQuery(String clause) {
        problem.parseAndAddClause(clause);
        problem.negateFinish();
        solveAndPrintCurrentResult();
        problem.removeExcessClauses();
    }

    private void solveAndPrintCurrentResult() {
        String result = "";
        String solverResult = solver.solveAndReturnSolution();
        result += problem.toString();
        result += solverResult + "\n";
        System.out.println(result);
    }
}
