package ui.algorithm;

import ui.Dataset;

/**
 * Machine learning algorithm interface
 *
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public interface IMAlgorithm {

    /**
     * Method fits the dataset and creates a model
     * @param dataset dataset to fit to model
     */
    void fit(Dataset dataset);

    /**
     * Method predicts values from dataset with model
     *
     * @param dataset dataset with values from which to predict
     */
    void predict(Dataset dataset);
}
