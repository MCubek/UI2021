package ui;

import ui.parser.ProblemParser;
import ui.program.IProgram;
import ui.program.InteractiveResolutionProgram;
import ui.program.SimpleResolutionProgram;
import ui.solver.IProblemSolver;
import ui.solver.ProblemSolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main program.
 *
 * @author MatejCubek
 * @project UILab2
 * @created 06/04/2021
 */
public class Solution {
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) throw new IllegalArgumentException();

        String programIdentification = args[0];
        Path clauses = Paths.get(args[1]);

        Path inputs = null;
        if (args.length == 3) inputs = Paths.get(args[2]);

        IProblem problem;
        IProblemSolver solver;
        try {
            problem = new ProblemParser(clauses);
            solver = new ProblemSolver(problem);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        IProgram program = switch (programIdentification) {
            case "cooking" -> new InteractiveResolutionProgram(problem, solver, inputs);
            case "resolution" -> new SimpleResolutionProgram(problem, solver);
            default -> throw new IllegalStateException("Unexpected value: " + programIdentification);
        };

        program.execute();
    }
}
