/**
 *	NaiveBayes is a probabilistic classifier that calculates the probability
 *	of a data value appearing by calculating it's probability given the a 
 *	particular class label.
 *	
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		March 27, 2016
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

public class NaiveBayes{

	/**	Structure of this trainSet: 
	 *	key 	== Double 		class label
	 *	value	== ArrayList	{<class count> , HashMap<Attribute Index, Count of Attribute>}
	 */
	private HashMap<Double, HashMap<Integer, HashMap<Double, Double>>> trainSet = new HashMap<>();
	private HashMap<Double, Double> trainSetLabelCounts = new HashMap<>();
	private ArrayList<Integer> trainSetAttributeTypes = new ArrayList<>();
	
	public NaiveBayes(){
		// Empty constructor
	}

	/**
	 *	Constructor with one parameter
	 *	@param	trainSet: ExampleSet object with training examples
	 */
	public NaiveBayes(AttributeSet attributeSet, ExampleSet trainSet){
		this.train(attributeSet, trainSet);
	}

	/**
	 *	Train the NaiveBayes classifier
	 *	@param	trainSet: ExampleSet object with training examples
	 */
	public void train(AttributeSet attributeSet, ExampleSet trainSet){
		ArrayList<Double> trainSetLabels = new ArrayList<>(trainSet.getLabels());

		// Loop through the trainSetLabels and get counts for the attributes given that label
		for(Double trainSetLabel : trainSetLabels){
			this.trainSet.put(trainSetLabel, this.getCounts(trainSet.getExamplesWithLabel(trainSetLabel)));
			this.trainSetLabelCounts.put(trainSetLabel, (double) trainSet.getExamplesWithLabel(trainSetLabel).size());
		}

		trainSetAttributeTypes = attributeSet.getTypes();
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
		ArrayList<Double> logProbabilities = new ArrayList<>();

		// Loop through all class labels and get log probabilities
		for(Double classLabel : trainSetLabelCounts.keySet()){
			logProbabilities.add(getProbability(classLabel, example));
		}

		return (double) logProbabilities.indexOf(Collections.min(logProbabilities));
	}

	/**
	 *	Count the data values in the specified ExampleSet input
	 *	@param	trainSetStratum:
	 *	@return	List with two items: (1) class label counts and (2) HashMap of Attribute counts
	 */
	protected HashMap<Integer, HashMap<Double, Double>> getCounts(ExampleSet exampleSet){
		HashMap<Integer, HashMap<Double, Double>> exampleSetCounts = new HashMap<>();

		// Loop through all the attributes
		for(int i=0; i<exampleSet.get(0).size(); i++){

			// Make a HashMap mapping the attribute value to its frequency
			HashMap<Double, Double> featureCounts = new HashMap<>();

			// Get all the attribute values for this attribute - list and set
			ArrayList<Double> featureValues  = exampleSet.getFeatureValues(i);
			HashSet<Double> featureValuesSet = new HashSet<>(featureValues);

			// Loop through the attribute values for this attribute
			for(Double value : featureValuesSet){

				// Count the occurrence of each attribute value
				featureCounts.put(value, (double) Collections.frequency(featureValues, value));
			}

			// Map this attribute index to a list of the attributes
			exampleSetCounts.put(i, featureCounts);
		}

		return exampleSetCounts;
	}

	/**
	 *	Get a list of numeric values from a HashMap
	 *	@param	entrySet: key-value HashMap
	 *	@return	List of numeric values
	 */
	public ArrayList<Double> getListOfNumericValues(HashMap<Double, Double> entrySet){
		ArrayList<Double> attributeValueCounts = new ArrayList<>();

		// Loop through the entrySet(s) for this numeric attribute
		for(Double key : entrySet.keySet()){
			Double value = entrySet.get(key);

			// If the count for this numeric value is more than 1, add each one
			if(!value.equals(1.0)){

				// Loop through the number of times this numeric value appears
				for(double i=0.0; i < value; i++){
					attributeValueCounts.add(key);
				}

			// Else, add the numeric value once
			} else {
				attributeValueCounts.add(key);
			}
		}
		return attributeValueCounts;
	}


	/**
	 *	Get frequencies for a specified attribute and attribute value
	 *	@param	attributeIndex: attribute's index
	 *	@param	attributeValue: attribute's value at specified index
	 *	@return	Double frequency
	 */
	public Double getProbability(Double classLabel, Example example){
		Double classProbability  = getProbabilityForClass(classLabel);
		Double outputProbability = -Math.log10(classProbability);

		// Loop through the example's feature values and calculate their probabilities
		for(int i=0; i<example.size(); i++){
			Double attributeProbability = getProbabilityForAttribute(classLabel, i, example.getValue(i));
			
			// Calculate the total output probability; if 0.0 or NaN, assign 0.0
			if(attributeProbability.equals(0.0) || Double.isNaN(attributeProbability)){
				outputProbability += 0.0;
			} else {
				outputProbability += -Math.log10(attributeProbability);
			}
		}
		return outputProbability;
	}

	/**
	 *	Get specified class probability
	 *	@param	classLabel: class whose probability to calculate
	 *	@return	class probability
	 */
	public Double getProbabilityForClass(Double classLabel){
		Double classCount = this.trainSetLabelCounts.get(classLabel);
		Double totalCount = NaiveBayesMath.sum(this.trainSetLabelCounts.values());
		return classCount/totalCount;
	}

	/**
	 *	Get specified attribute probability
	 *	@param	classLabel: class whose probability to calculate
	 *	@param	attributeIndex: feature value index, a.k.a attribute index
	 *	@param	attributeValue: value present at feature value index
	 *	@return	class probability
	 */
	public Double getProbabilityForAttribute(Double classLabel, Integer attributeIndex, Double attributeValue){
		
		// If the Attribute at this index is nominal calculate probability
		if(trainSetAttributeTypes.get(attributeIndex).equals(0)){
			Double attributeValueCount = trainSet.get(classLabel).get(attributeIndex).get(attributeValue);
			Double attributeValueTotal = NaiveBayesMath.sum(trainSet.get(classLabel).get(attributeIndex).values());

			if(attributeValueCount == null){
				attributeValueCount = 1.0;
			}

			return attributeValueCount/attributeValueTotal;

		// If the Attribute at this index is numeric calculate probability density function
		} else {
			ArrayList<Double> attributeValueCounts = getListOfNumericValues(trainSet.get(classLabel).get(attributeIndex));

			return NaiveBayesMath.pdf(attributeValue, attributeValueCounts);
		}
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