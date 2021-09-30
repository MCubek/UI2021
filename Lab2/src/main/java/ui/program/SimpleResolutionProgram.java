package ui.program;

import ui.IProblem;
import ui.solver.IProblemSolver;

/**
 * Problem 1, resolution problem
 *
 * @author MatejCubek
 * @project UILab2
 * @created 07/04/2021
 */
public class SimpleResolutionProgram implements IProgram {
    private final IProblem problem;
    private final IProblemSolver solver;

    public SimpleResolutionProgram(IProblem problem, IProblemSolver solver) {
        this.problem = problem;
        this.solver = solver;
    }

    @Override
    public void execute() {
        problem.negateFinish();

        String result = "";
        String solverResult = solver.solveAndReturnSolution();
        result += problem.toString();
        result += solverResult;
        System.out.println(result);

        problem.removeExcessClauses();
    }
}
