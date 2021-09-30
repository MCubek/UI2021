package ui;

import ui.algorithm.IMAlgorithm;
import ui.algorithm.decisiontree.ID3;
import ui.util.csv.CSVDataset;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

/**
 * Main entrypoint to third lab
 *
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public class Solution {
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("2 or 3 arguments required!");
            return;
        }

        Path trainingPath;
        Path testPath;
        try {
            trainingPath = Path.of(args[0]);
            testPath = Path.of(args[1]);
        } catch (InvalidPathException pathException) {
            System.out.println("Error while parsing file path.");
            return;
        }

        int limit = - 1;
        if (args.length == 3) {
            limit = Integer.parseInt(args[2]);
        }

        Dataset trainingSet;
        Dataset testSet;
        try {
            trainingSet = new CSVDataset(trainingPath);
            testSet = new CSVDataset(testPath);
        } catch (IllegalArgumentException exception) {
            System.out.println("Error while parsing file.");
            return;
        }

        IMAlgorithm model;

        if (limit == - 1) {
            model = new ID3();
        } else {
            model = new ID3(limit);
        }
        model.fit(trainingSet);
        model.predict(testSet);
    }
}
