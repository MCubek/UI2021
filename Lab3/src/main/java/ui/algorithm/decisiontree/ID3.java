package ui.algorithm.decisiontree;

import ui.Dataset;
import ui.algorithm.IMAlgorithm;
import ui.util.csv.Record;
import ui.util.node.ITreeNodeVisitor;
import ui.util.node.InnerNode;
import ui.util.node.LeafNode;
import ui.util.node.TreeNode;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class representing a decision tree algorithm implementation.
 * Algorithm is the ID3 implementation.
 *
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public class ID3 implements IMAlgorithm {
    private TreeNode<String> root;
    private Dataset teachDataset;
    private final int limit;

    public ID3() {
        this(- 1);
    }

    public ID3(int limit) {
        this.limit = limit;
    }

    @Override
    public void fit(Dataset dataset) {
        this.teachDataset = dataset;
        root = id3(dataset, null, null, limit);
        MyBranchVisitor branchVisitor = new MyBranchVisitor();
        root.visit(branchVisitor);
        System.out.print(branchVisitor);
    }

    @Override
    public void predict(Dataset dataset) {
        if (root == null) throw new IllegalStateException("Model not trained");

        //Confusion matrix init
        List<String> classLabels = dataset.getDistinctSortedClassLabels();

        int[][] confusionMatrix = new int[classLabels.size()][classLabels.size()];

        int correctPredictions = 0;
        List<String> predictions = new ArrayList<>();
        for (var row : dataset) {
            String prediction = classify(row);
            predictions.add(prediction);

            int rowNum = classLabels.indexOf(row.getClassLabelValue());
            int colNum = classLabels.indexOf(prediction);

            confusionMatrix[rowNum][colNum]++;

            if (row.getClassLabelValue().equals(prediction)) correctPredictions++;
        }
        //Predictions
        System.out.println("[PREDICTIONS]: " + String.join(" ", predictions));
        //Accuracy
        System.out.printf("[ACCURACY]: %s\n", formatDecimal(1.0 * correctPredictions / dataset.rowNumber(), 5));
        //Print confusion matrix
        System.out.println("[CONFUSION_MATRIX]:");
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < confusionMatrix.length; i++) {
            List<String> tempList = new ArrayList<>();
            for (int j = 0; j < confusionMatrix[0].length; j++) {
                tempList.add(String.valueOf(confusionMatrix[i][j]));
            }
            System.out.println(String.join(" ", tempList));
        }
    }

    private static String formatDecimal(double v, int decimalDigits) {
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        format.setMaximumFractionDigits(decimalDigits);
        format.setMinimumFractionDigits(decimalDigits);
        return format.format(v);
    }

    /**
     * Helper method for classifying.
     *
     * @param row row to classify
     * @return result of classification
     */
    private String classify(Record row) {
        TreeNode<String> node = root;

        outer:
        while (! (node instanceof LeafNode)) {
            InnerNode<String> innerNode = (InnerNode<String>) node;
            String attribute = row.getValueForColumnLabel(innerNode.getColumnTitle());

            for (TreeNode<String> child : innerNode.getChildren()) {
                if (child.getColumnValue().equals(attribute)) {
                    node = child;
                    continue outer;
                }
            }
            //Value not found
            List<String> mostCommonOthers = teachDataset.getSortedColumnValues(innerNode.getColumnTitle());
            for (String other : mostCommonOthers) {
                for (TreeNode<String> child : innerNode.getChildren()) {
                    if (child.getColumnValue().equals(other)) {
                        node = child;
                        continue outer;
                    }
                }
            }
        }
        return ((LeafNode<String>) node).getClassField();
    }

    /**
     * Recursive algorithm ID3 implementation.
     *
     * @param dataset     current node dataset
     * @param parent      current node's parent
     * @param columnValue column value to save into node
     * @param limit       current limit for stopping
     * @return caluclated node
     */
    private TreeNode<String> id3(Dataset dataset, TreeNode<String> parent, String columnValue, int limit) {
        if (dataset.areAllAttributesUsed()) {
            if (parent == null) return new LeafNode<>(columnValue, dataset, dataset.getMajorityClassLabel());
            return new LeafNode<>(columnValue, dataset, parent.getDataset().getMajorityClassLabel());
        } else if (limit == 0) {
            return new LeafNode<>(columnValue, dataset, dataset.getMajorityClassLabel());
        } else if (dataset.isClassified()) {
            return new LeafNode<>(columnValue, dataset, dataset.getMajorityClassLabel());
        } else {
            String nextColumn = findNextColumn(dataset);

            InnerNode<String> node = new InnerNode<>(columnValue, dataset, nextColumn);

            for (String value : teachDataset.getColumnValues(nextColumn)) {
                Dataset set = dataset.splitByColumnValue(nextColumn, value);

                node.addChild(id3(set, node, value, limit - 1));
            }
            return node;
        }
    }

    /**
     * Helper method for finging next column to expand.
     *
     * @param dataset current dataset
     * @return next column title to expand.
     */
    private static String findNextColumn(Dataset dataset) {
        Map<String, Double> informationGainMap = new HashMap<>();
        for (String column : dataset.getColumnTitles()) {
            double informationGain = calculateInformationGain(dataset, column);
            informationGainMap.put(column, informationGain);
        }
        System.out.println(informationGainMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .map(val -> String.format("IG(%s)=%s", val.getKey(), formatDecimal(val.getValue(), 4)))
                .collect(Collectors.joining("  ")));

        return informationGainMap.entrySet().stream()
                .max(Map.Entry.<String, Double>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                .map(Map.Entry::getKey).orElse("");
    }

    /**
     * Method for calculating information gain for column in argument on dataset in argument.
     *
     * @param dataset dataset on which information gain is calculated
     * @param column  column to calculate information gain for
     * @return information gain
     */
    private static double calculateInformationGain(Dataset dataset, String column) {
        double subsetEntropy = 0;

        for (Dataset subDataset : dataset.splitByColumnToList(column)) {
            subsetEntropy += 1.0 * subDataset.rowNumber() / dataset.rowNumber() * subDataset.entropy();
        }

        return dataset.entropy() - subsetEntropy;
    }

    /**
     * Visitor design pattern implementation for printing tree.
     */
    private static class MyBranchVisitor implements ITreeNodeVisitor<String> {
        private String state = "";
        private int number = 1;
        StringBuilder stringBuilder = new StringBuilder();

        @Override
        public String toString() {
            return stringBuilder.toString();
        }

        public MyBranchVisitor() {
            stringBuilder.append("[BRANCHES]:\n");
        }

        @Override
        public void visitInnerNode(InnerNode<String> node) {
            String addition = "";
            if (node.getColumnValue() != null)
                addition += node.getColumnValue() + " ";

            addition += String.format("%d:%s=", number, node.getColumnTitle());

            state += addition;
            number++;
            node.getChildren().forEach(treeNode -> treeNode.visit(this));
            number--;
            state = state.substring(0, state.length() - addition.length());
        }

        @Override
        public void visitLeafNode(LeafNode<String> node) {
            stringBuilder.append(state);
            if (node.getColumnValue() != null)
                stringBuilder.append(node.getColumnValue()).append(" ");
            stringBuilder.append(node.getClassField()).append("\n");
        }
    }

}
