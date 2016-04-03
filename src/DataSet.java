/** 
 *	The DataSet is a collection of attributes and examples specified in a data
 *	site file (e.g., *.gla). This class uses other classes from this package
 *	such as Attribute, AttributeSet, Example, ExampleSet, and Factory. The
 *	class has three data members: (1) data set name, (2) AttributeSet and
 *	(3) ExampleSet. The AttributeSet contains the possible attributes for
 *	this data set and can be accessed using methods in this class or accessed
 *	directory by retrieving the entire set. The same goes for ExampleSet. The
 *	Factory class is used to construct Attributes and Examples as well
 *	as the AttributeSet and ExampleSet respectively.
 *	
 *  @author		Glenn Abastillas
 *  @version	1.0.0
 *  @since		February 10, 2016 -- completed on February 28, 2016
 *  
 */

package edu.classifier.dataset;

import edu.classifier.dataset.Attribute;
import edu.classifier.dataset.AttributeSet;
import edu.classifier.dataset.Example;
import edu.classifier.dataset.ExampleSet;
import edu.classifier.dataset.Factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.IndexOutOfBoundsException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.*;
import java.util.Random;

public class DataSet{

	private String dataSetName 		  = "data set";
	private AttributeSet attributeSet = new AttributeSet();
	private ExampleSet exampleSet 	  = new ExampleSet();
	private Long randomNumberSeed 	  = 7L;

	public DataSet(){
		// Empty constructor
	}

	/**
	 *	Constructor with a String parameter to the data set file
	 *	@param	filename: pathway to text file containing dataset information
	 */
	public DataSet(String dataSetFile){
		this.load(dataSetFile);
	}

	/**
	 *	Print formatted data set to screen.
	 */
	public void asString(){
		System.out.println(this.dataSetName + "\n\r");	//	Print data set name/title

		for(Attribute attribute : this.attributeSet){	// 	Print attributes
			attribute.asString();
		}	

		System.out.println();

		for(Example example : this.exampleSet){
			example.asString(attributeSet);
		}
	}

	/**
	 *	Loads data in specified file into memory and extracts any attributes 
	 *	(indicated by '@' in the document) and examples (indicated by '#' in the
	 *	document) Attributes and Examples are then constructed into set objects 
	 *	(e.g., AttributeSet) by a Factory object.
	 *	@param	filename: pathway to text file containing dataset information
	 */
	public void load(String fileName){
		ArrayList<String> inputFile = this.open(fileName);

		this.dataSetName = inputFile.get(0);

		// Create Attribute or Example objects from the information in the specified file and add them to their appropriate set object (e.g., Attribute --> AttributeSet)
		for(String line : inputFile){
			if(line.substring(0,1).equals("@")){
				attributeSet.add(Factory.makeAttribute(line));
			}

			if(line.substring(0,1).equals("#")){
				exampleSet.add(Factory.makeExample(attributeSet, line));
			}
		}
	}

	/**
	 *	Get this data set's attributes
	 *	@return	AttributeSet object containing this data set's attributes
	 */
	public AttributeSet getAttributeSet(){
		return this.attributeSet;
	}

	/**
	 *	Return an attribute from this data set specified by an Integer index
	 *	@param	index: attribute index
	 *	@return	Attribute indicated by index
	 */
	public Attribute getAttribute(Integer index){
		return this.attributeSet.get(index);
	}

	/**
	 *	Return this data set's examples
	 *	@return	ExampleSet object containing this data set's examples
	 */
	public ExampleSet getExampleSet(){
		return this.exampleSet;
	}

	/**
	 *	Return an example from this data set specified by an Integer index
	 *	@param	index: Example object's index
	 *	@return	Example indicated by index
	 */
	public Example getExample(Integer index){
		return this.exampleSet.get(index);
	}

	/**
	 *	Return the name of this data set. Default name is 'data set'
	 *	@return	String	dataSetName	name of this data set
	 */
	public String getName(){
		return this.dataSetName;
	}

	/**
	 *	Get a subset of this data set's ExampleSet
	 *	Private method used by getTrainSet(), getTestSet(), getTrainTestSet()
	 *	and getValidationSet()
	 *	@param	percentageOfExamplesForSet: decimal number indicating percentage
	 *	@return	ExampleSet of randomly selected Example objects
	 */
	private ExampleSet getSubset(Double percentageOfExamplesForSet){

		ArrayList<Integer> stratumCounts	= new ArrayList<>();
		ArrayList<Double>  stratumClass		= new ArrayList<>(exampleSet.getLabels());

		ExampleSet exampleSetSubsetOutput	= new ExampleSet();
		Random randomNumberGenerator 		= new Random();

		// Loop through the class labels to get the number of examples needed for each in this subset
		for(Double classLabel : stratumClass){
			stratumCounts.add(Math.round((float) (percentageOfExamplesForSet * Collections.frequency(exampleSet.getAllLabels(), classLabel))));
		}

		//-x System.out.println("Get Subset: " + percentageOfExamplesForSet);
		//-x System.out.println("Stratum Counts: " + stratumCounts);

		// Loop through the class labels to sample examples for each
		for(int i=0; i<stratumClass.size();i++){

			ExampleSet stratumExamples = exampleSet.getExamplesWithLabel(stratumClass.get(i));
			ArrayList<Integer> exampleIndices  = new ArrayList<>();

			Integer stratumSize = stratumCounts.get(i);

			//-x System.err.println("In for loop: " + stratumSize);

			// Fabricate random numbers pertaining to the indices of examples of this stratum
			while(stratumSize > 0){

				int randomIndex = randomNumberGenerator.nextInt(stratumExamples.size());

				// If this random number has not already been used, add it and its associated example to the list
				if(!exampleIndices.contains(randomIndex)){
					exampleIndices.add(randomIndex);
					exampleSetSubsetOutput.add(stratumExamples.get(randomIndex));
					stratumSize--;
				}
				//-x System.err.println("In while loop: " + randomIndex + "\t" + exampleIndices);
			}
		}
		return exampleSetSubsetOutput;
	}

	/**
	 *	Get a simple test set (40% of examples)
	 *	@return a set of examples containing 40% of all examples not already in trainSet
	 */
	public ExampleSet getTestSet(){
		return this.getSubset(0.4);
	}

	/**
	 *	Get a simple train set making up 60% of the examples
	 *	@return	a set of examples containing 60% of all examples
	 */
	public ExampleSet getTrainSet(){
		return this.getSubset(0.6);
	}

	/**
	 *	Get a training set as a portion of the exampleSet of this Data Set
	 *	@param	percentageOfExamplesForSet: decimal number indicating percentage
	 *	@return	a set of examples randomly selected
	 */
	public ExampleSet getTrainSet(Double percentForTrainingSet){
		return this.getSubset(percentForTrainingSet);
	}

	/**
	 *	Get both training and testing sets with 60% training and 40% test set
	 *	@param	percentageOfExamplesForSet: decimal number indicating percentage
	 *	@return	list containing both training and testing sets
	 */
	public ArrayList<ExampleSet> getTrainTestSet(){
		return this.getTrainTestSet(0.6);
	}

	/**
	 *	Get both training and testing sets according to a percentage
	 *	@param	percentageOfExamplesForSet: decimal number indicating percentage
	 *	@return	list containing both training and testing sets
	 */
	public ArrayList<ExampleSet> getTrainTestSet(Double percentForTrainingSet){
		ArrayList<ExampleSet> trainTestSet = new ArrayList<ExampleSet>();
		ExampleSet trainSet = this.getTrainSet(percentForTrainingSet);
		ExampleSet testSet 	= new ExampleSet();

		// Loop through the example set to create a test set
		// from Example objects not already in trainSet
		for(Example example : this.exampleSet){
			if(!trainSet.contains(example)){
				testSet.add(example);
			}
		}

		trainTestSet.add(trainSet);
		trainTestSet.add(testSet);

		return trainTestSet;
	}

	/**
	 *	Return an ArrayList<ExampleSet> contain training, validation, and testing sets according to a percentage for training and validation sets
	 *	Testing set is the remainder after the training and testing sets are created
	 *	@param	percentageOfExamplesForTrainSet: decimal number between 0.0 and 1.0 corresponding to the percentage of the exampleSet to extract for the training set (e.g., 0.2 = 20%)
	 *	@param	percentageOfExamplesForValidationSet: decimal number between 0.0 and 1.0 corresponding to the percentage of the exampleSet to extract for the validation set (e.g., 0.2 = 20%)
	 *	@return	trainValidationTestSet: list containing training, validation, and testing set
	 */
	public ArrayList<ExampleSet> getValidationSet(Double percentForTrainingSet, Double percentForValidationSet) throws IndexOutOfBoundsException{

		// STEP 1: Check to see if the percentage for the trainSet and validationSet add to 1.0 or greater - this means there are no Examples in the testSet.
		if((percentForTrainingSet + percentForValidationSet) >= 1.0){
			System.out.println("Percentages for Train Set and Validation Set are equal to or greater than 100%. No Examples left for Test Set. ");
			throw new IndexOutOfBoundsException();
		}

		Integer numberOfExamplesForValidationSet 	 = Math.round((float) (percentForValidationSet * (exampleSet.size())));
		ArrayList<ExampleSet> trainValidationTestSet = new ArrayList<ExampleSet>();

		ExampleSet trainSet 		= this.getTrainSet(percentForTrainingSet);
		ExampleSet validationSet 	= new ExampleSet();
		ExampleSet testSet 			= new ExampleSet();

		// STEP 2: Get Examples for validationSet and testSet
		for(Example example : this.exampleSet){
			if(numberOfExamplesForValidationSet > 0 && !trainSet.contains(example)){
				validationSet.add(example);
				numberOfExamplesForValidationSet--;
				
			} else if(numberOfExamplesForValidationSet == 0 && !trainSet.contains(example)){
				testSet.add(example);
			}
		}

		// STEP 3: Add trainSet, validationSet, and testSet to ArrayList<ExampleSet>
		trainValidationTestSet.add(trainSet);
		trainValidationTestSet.add(validationSet);
		trainValidationTestSet.add(testSet);

		return trainValidationTestSet;
	}

	/**
	 *	Read in a text file and return an ArrayList<String>
	 *	containing non-empty lines from the input document
	 *	@param	fileName: file name location
	 *	@return	list of lines containing text from fileName
	 */
	private ArrayList<String> open(String fileName){
		ArrayList<String> openedFileArrayList = new ArrayList<String>();
		String 			  bufferedReaderLine  = null;
		
		try{
			FileReader 	   fileReader 	  = new FileReader(new File(fileName));
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((bufferedReaderLine = bufferedReader.readLine()) != null){
				if(bufferedReaderLine.replace(" ", "").length() > 0){
					openedFileArrayList.add(bufferedReaderLine);
				}
			}
		} catch(FileNotFoundException e){
			System.out.println("[FileNotFoundException] Unable to open file " + fileName);
		} catch(IOException e){
			System.out.println("[IOException] Unable to open file " + fileName);
		} 

		return openedFileArrayList;
	}

	/**
	 *	Set this DataSet's exampleSet to a new ExampleSet object
	 *	@param	exampleSet: new ExampleSet object
	 */
	public void setExampleSet(ExampleSet exampleSet){
		this.exampleSet = exampleSet;
	}

	/**
	 *	Return the number of examples in this data set
	 *	@return	Integer size of ExampleSet
	 */
	public Integer size(){
		return this.size(1);
	}
 	
 	/**
	 *	Return the number of attributes (=0) or examples (=1)
	 *	@param	setIndex: index referring to size of the AttributeSet
	 *	@return	sizeOf: size of AttributeSet or ExampleSet
	 */
	public Integer size(int setIndex) throws IndexOutOfBoundsException{
		
		Integer sizeOf;

		// Return AttributeSet object size if setIndex == 0
		if(setIndex == 0){
			return attributeSet.size();
		} else if(setIndex == 1){
			return exampleSet.size();
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 *	MAIN METHOD
	 *	Runs if class run as script. --
	 */
	public static void main(String[] args){
		Attribute a1 = new Attribute("cats", "nom", "tiger lion jaguar cat");
		Attribute a2 = new Attribute("dogs", "nom", "wolf pitbull collie lab");
		Attribute a3 = new Attribute("size", "num", "0.0");
		AttributeSet as1 = new AttributeSet();

		as1.add(a1); as1.add(a2); as1.add(a3);

		System.out.println("Attribute 0 name:\t" + as1.get(0).getName() + "\tType:\t" + as1.get(0).getType());
		System.out.println("Attribute 1 name:\t" + as1.get(1).getName() + "\tType:\t" + as1.get(1).getType());
		System.out.println("Attribute 2 name:\t" + as1.get(2).getName() + "\tType:\t" + as1.get(2).getType());

		//String fileName = "/Users/ducrix/Documents/Research/Java/classifier/resources/data1.txt";
		String fileName = "/Users/ducrix/Documents/Research/Java/classifier/resources/data/ml/test_genders.gla";

		DataSet ds = new DataSet();
		DataSet ds2 = new DataSet(fileName);

		System.out.println(ds2.getAttributeSet());
		//ds2.setAttributeSet(as1);
		System.out.println(ds2.getAttributeSet());
		System.out.println(ds2.getAttributeSet());

		//System.out.println(ds.open(fileName));
		ds.load(fileName);
		ds.asString();
		ds.getTrainSet();

		ExampleSet trainSet = ds.getTrainSet(.45);
		System.out.println("Size of train set:\t" + ds.getTrainSet().size());
		System.out.println("Size of train set:\t" + ds.getExampleSet().size());
		System.out.println("Size of train set 2:\t" + trainSet.size());
		System.out.println("Train Set Example 0 label: " + trainSet.get(0).getLabel());
		System.out.println("Train Set Example 1 label: " + trainSet.get(1).getLabel());
		System.out.println("Train Set Example 2 label: " + trainSet.get(2).getLabel());
		System.out.println("Train Set Example 3 label: " + trainSet.get(3).getLabel());

		ArrayList<ExampleSet> tts = ds.getTrainTestSet(.8);
		ArrayList<ExampleSet> tvt = ds.getValidationSet(0.3, 0.2);

		System.out.println("Train set size:\t" + tts.get(0).size());
		System.out.println("Test set size:\t" + tts.get(1).size());

		System.out.println("Train set size:\t" + tvt.get(0).size());
		System.out.println("Valid set size:\t" + tvt.get(1).size());
		System.out.println("Test set size:\t" + tvt.get(2).size());
	}

}