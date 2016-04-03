/**
 *	
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		March 30, 2016
 *	
 */

package edu.classifier.ml;

import java.util.*;
import java.util.Collections;

import edu.classifier.dataset.DataSet;
import edu.classifier.dataset.Example;
import edu.classifier.dataset.ExampleSet;
import edu.classifier.dataset.AttributeSet;
import edu.classifier.ml.NaiveBayesMath;

public class Perceptron{

	HashMap<Integer, Double> weights = new HashMap<>();

	public Perceptron(){
		// Empty constructor
	}

	/**
	 *	Constructor with one parameter
	 *	@param	trainSet: ExampleSet object with training examples
	 */
	public Perceptron(ExampleSet trainSet){
		this.train(trainSet);
	}

	/**
	 *	Intialize the weights HashMap
	 *	@param	example: Example object
	 *	@param	initialValue: value to initalize vector with
	 */
	private void initializeWeights(Example example, Double initialValue){

		// Loop through the Example object's attributes to size the weights HashMap
		for(int i=0; i<example.size(); i++){
			weights.put(i, initialValue);
		}
	}

	/**
	 *	Get a sign to see if classification was correct
	 *	@param	sum: dot product of weight vector and example vector
	 */
	private Double sign(Integer sum, Double threshold){
		if(sum > threshold){
			return 1.0;
		} else {
			return 0.0;
		}
	}

	/**
	 *	Get the dot product of the weight vector and example vector
	 *	@param	example: Example object
	 *	@return	Double value resulting from a dot product of the vectors
	 */
	private Double dot(Example example){
		Double indexicalProduct;
		Double sumOfProducts;

		// Loop through the example and weight vectors
		for(int i=0; i < example.size(); i++){
			indexicalProduct = weights.get(i) * example.getValue(i);
			sumOfProducts += indexicalProduct;
		}

		return sumOfProducts;
	}

	/**
	 *	Update the weights vector
	 *	@param	example: Example object
	 *	@param	correction: correction rate
	 */
	private void updateWeights(Example example, Double correction){
		Double updatedWeight;

		// Loop through the example values and weights to update them
		for(int i=0; i < example.size(); i++){
			updatedWeight = weights.get(i) + (example.getValue(i) * correction);
			weights.put(i, updatedWeight);
		}
	}

	/**
	 *	Train the Perceptron classifier
	 *	@param	trainSet: ExampleSet object with training examples
	 */
	public void train(ExampleSet trainSet){

		boolean converged = false;
		Double learningRate = 0.1;

		// Initialize the weights vector
		initializeWeights(trainSet.get(0), 0.0);

		while(converged == false){
			
			converged = true;

			// Loop through the examples in 
			for(Example example:trainSet){
				Double dotProduct = dot(example);
				Double resultSign = sign(dotProduct, 0.5);

				Double error = example.getLabel() - resultSign;
				Double correction = error * learningRate;

				updateWeights(example, correction);

			}
		}
	}

	/**
	 *	Loop through an ExampleSet and classify each example
	 *	@param	testSet: ExampleSet object containing test Example objects
	 *	@return	ArrayList containing classifications or labels of test set Example objects
	 */
	public ArrayList<Double> test(ExampleSet testSet){
		// Insert code
	}

	/**
	 *	Classify one example using this class's trainSet
	 *	@param	example: Example object to be classified
	 *	@return	class label as a Double
	 */
	public Double classify(Example example){
		// Insert code
	}

	public static void main(String[] args){
		String fileName;

		fileName = "/Users/ducrix/Documents/Research/Java/classifier/resources/data/ml/test_books.gla";
		//fileName = "/Users/ducrix/Documents/Research/Java/classifier/resources/data/ml/test_weather.gla";
		fileName = "/Users/ducrix/Documents/Research/Java/classifier/resources/data/ml/test_words.gla";
		//fileName = "/Users/ducrix/Documents/Research/Java/classifier/resources/data/ml/test_genders.gla";
		//fileName = "/Users/ducrix/Documents/Research/Java/classifier/resources/data/ml/test_cars.gla";

		DataSet ds = new DataSet(fileName);
		ArrayList<ExampleSet> trainTestSet = ds.getTrainTestSet();
		NaiveBayes nb = new NaiveBayes();
		nb.train(ds.getAttributeSet(), trainTestSet.get(0));
		System.err.println("Classify(): " + nb.classify(trainTestSet.get(1).get(0)));
		System.err.println("Classify(): " + nb.test(trainTestSet.get(1)));
		System.err.println("Actual Class: " + trainTestSet.get(1).getAllLabels());

		/*
		IBk ibk = new IBk();
		ibk.train(trainTestSet.get(0));
		System.out.println("Classification for " + trainTestSet.get(1).get(0).data() + " " + trainTestSet.get(1).get(0).getLabel()+ " " + ibk.classify(trainTestSet.get(1).get(0)));
		

		ArrayList<Double> resultsA = ibk.test(trainTestSet.get(1));
		ArrayList<Double> resultsB = trainTestSet.get(1).getAllLabels();
		Double sum=0.0;
		System.err.println("Test Results: " + resultsA);
		System.err.println("Actual Results: " + resultsB);
		System.err.println("Training Examples: " + trainTestSet.get(0).getAllLabels());

		for(int i=0; i< resultsA.size(); i++){
			sum+=(resultsA.get(i).equals(resultsB.get(i)))?1.0:0.0;
		}
		System.err.println("Average: " + (sum/resultsA.size()));
		*/
		//System.out.println("Train Set: " + trainTestSet.get(1).get(0).data() + "\tTest Set: " + trainTestSet.get(0).get(0).data());
	}
}