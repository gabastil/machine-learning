/**
 *	IBk is an instance based machine learner that calculates the distance
 *	between two data points assigning the closest data points similar class
 *	labels. 
 *	
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		March 21, 2016
 *	
 */
package edu.classifier.ml;

import java.util.*;
import java.util.Collections;

import edu.classifier.dataset.DataSet;
import edu.classifier.dataset.Example;
import edu.classifier.dataset.ExampleSet;
import edu.classifier.ml.Distance;
import edu.classifier.ml.Kernel;

public class IBk{

	private ExampleSet trainSet = new ExampleSet();
	
	public IBk(){
		// Empty constructor
	}

	/**
	 *	Constructor with one parameter
	 *	@param	trainSet: ExampleSet object with training examples
	 */
	public IBk(ExampleSet trainSet){
		this.trainSet = trainSet;
	}

	/**
	 *	Train the IBk classifier
	 *	@param	trainSet: ExampleSet object with training examples
	 */
	public void train(ExampleSet trainSet){
		this.trainSet = trainSet;
	}

	/**
	 *	Loop through an ExampleSet and classify each example
	 *	@param	testSet: ExampleSet object containing test Example objects
	 *	@return	ArrayList containing classifications or labels of test set Example objects
	 */
	public ArrayList<Double> test(ExampleSet testSet){
		ArrayList<Double> exampleSetClassifications = new ArrayList<Double>();

		// Loop through the testSet, classify each Example object and store result
		for(Example e : testSet){
			exampleSetClassifications.add(this.classify(e));
		}
		return exampleSetClassifications;
	}

	/**
	 *	Classify one example using this class's trainSet
	 *	@param	example: Example object to be classified
	 *	@return	class label as a Double
	 */
	public Double classify(Example example){

		Double currentDistance;
		Kernel kernel = new Kernel();

		// Loop through Example objects in the ExampleSet
		for(int i = 0; i < this.trainSet.size(); i++){

			// Calculate the distance to each Example in trainSet
			currentDistance = Distance.getEuclideanDistance(example, this.trainSet.get(i));
			//currentDistance = Distance.getChebyshevDistance(this.trainSet.get(i), example);
			//currentDistance = Distance.getManhattanDistance(this.trainSet.get(i), example);
			//currentDistance = Distance.getHammingDistance(example, this.trainSet.get(i));

			kernel.add(currentDistance, this.trainSet.get(i).getLabel());
		}

		return kernel.getMajorityLabel();
	}

	public static void main(String[] args){
		String fileName = "/Users/ducrix/Documents/Research/Java/classifier/resources/data/ml/test_cars.gla";
		fileName = "/Users/ducrix/Documents/Research/Java/classifier/resources/data/ml/test_books.gla";
		DataSet ds = new DataSet(fileName);
		IBk ibk = new IBk();
		ArrayList<ExampleSet> trainTestSet = ds.getTrainTestSet();
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

		//System.out.println("Train Set: " + trainTestSet.get(1).get(0).data() + "\tTest Set: " + trainTestSet.get(0).get(0).data());
	}
}