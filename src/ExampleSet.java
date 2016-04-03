/** 
 *	ExampleSet is an object that holds an ArrayList of Example objects.
 *	This object inherits methods from the abstract class DataObjectSet including
 *	the following classes: (1) add, (2) remove, (3) get, and (4) size. This 
 *	class overrides the iterable() method from DataObjectSet. This allows the
 *	user to iterable over the ExampleSet's data ArrayList.
 *
 *	This class allows users to get all the Example objects stored inside of it.
 *	Other methods allow the user to view certain properties or values of the
 *	Example objects stored in it such as: (1) contains: see if this ExampleSet
 *	contains a certain Example, (2) getLabels: see a set of all the labels or
 *	classes in this ExampleSet, (3) getAllLabels: see each Example objects'
 *	label, (4) getMajorityLabel: see the most frequent label or class among all
 *	the Example objects, and (5) isHomogenous: check to see if all examples in 
 *	this ExampleSet are of the same class.
 *	
 *  @author		Glenn Abastillas
 *  @version	1.0.0
 *  @since		February 10, 2016
 *  
 * ExampleSet is inherited by the following classes: DataSet (comment added February 14, 2016) 
 */
package edu.classifier.dataset;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import edu.templates.data.DataObjectSet;
import edu.classifier.dataset.Example;

public class ExampleSet extends DataObjectSet<Example>{

	public ExampleSet(){
		// Empty constructor
	}

	/**
	 *	Constructor for ExampleSet taking in an array of Example objects
	 *	@param	examples: array of Example objects
	 */
	public ExampleSet(Example[] examples){
		for(Example a: examples){
			this.data.add(a);
		}
	}

	/**
	 *	Get the iterator for ExampleSet
	 *	@return	iterator for this class
	 */
	public Iterator<Example> iterator(){
		return this.data.iterator();
	}

	/**
	 *	Return true if specified in this ExampleSet
	 *	@param	example: example to be checked against examples in this set
	 *	@return	contained: variable holding true or false depending on the presence of the specified example	
	 */
	public boolean contains(Example example){
		boolean contained = (this.data.contains(example)) ? true : false;
		return contained;
	}

	/**
	 *	Get a subset of examples from this class with the same label
	 *	@param	classLabel: label of examples to return
	 *	@return	homogenous list of examples with respect to their class labels
	 */
	public ExampleSet getExamplesWithLabel(Double classLabel){
		
		ExampleSet homogenousExamples = new ExampleSet();
		
		// Loop through the data to get examples with the same class labels
		for(Example example : this.data){
			if(example.getLabel().equals(classLabel)){
				homogenousExamples.add(example);
			}
		}

		return homogenousExamples;
	}

	/**
	 *	Get an array of values for a particular feature
	 *	@param	featureIndex: integer index associated with feature
	 *	@return	List of doubles for a feature
	 */
	public ArrayList<Double> getFeatureValues(Integer index){
		ArrayList<Double> featureValues = new ArrayList<>();

		// Loop through each Example object and get the value at the specified index
		for(Example example:data){
			featureValues.add(example.getValue(index));
		}

		return featureValues;
	}

	/**
	 *	Get a set of labels
	 *	@return	a HashSet of all the labels in data
	 */
	public Set<Double> getLabels(){
		return new HashSet<Double>(this.getAllLabels());
	}

	/**
	 *	Get a list of each Example object's class label
	 *	@return	an ArrayList of all the labels in data
	 */
	public ArrayList<Double> getAllLabels(){
		
		ArrayList<Double> labels = new ArrayList<Double>(this.data.size());

		for(Example e: this.data){
			labels.add(e.getLabel());
		}
		return labels;
	}

	/**
	 * Get the most occurring label/class in this data set
	 * @return majorityLabel: double representing the most common label/class 
	 */
	public Double getMajorityLabel(){
		Integer mostCount 	 = 0;		// tracks highest count
		Integer loopCount 	 = 0;		// tracks current loop count
		Double majorityLabel = 0.0; 	// tracks majority label based on its count
		ArrayList<Double> allLabels  = this.getAllLabels();	// list of all classification labels

		for(Double d: this.getLabels()){
			
			loopCount = Collections.frequency(allLabels, d);

			// If this label is more frequent than the mostCount (i.e., the latest count),
			// assign mostCount loopCount's value.
			if(loopCount > mostCount){
				mostCount = loopCount;
				majorityLabel = d;
			}
		}
		return majorityLabel;
	}

	/**
	 *	Check the homogeneity of the labels/classes of the Example objects in this set
	 *	@return	boolean: true if this ExampleSet is homogenous (i.e., only one label/class)
	 */
	public boolean isHomogenous(){
		return (this.getLabels().size() > 1) ? false : true;
	}

	/**
	 *	Get this class's data
	 *	@return	List of this class's data
	 */
	public ArrayList<Example> data(){
		return this.data;
	}

	public static void main(String[] args){
		ExampleSet as = new ExampleSet();
		Example a = new Example(new Double[]{1.0, 4.0, 3.0, 2.0});
		Example e = new Example(new Double[]{1.0, 4.0, 2.0, 1.0});

		System.out.println(a.labels());
		as.add(a);
		//as.add(e);

		System.out.println(as.size());
		//System.out.println(as.get(3).getLabel());


		Example a1 = new Example(new Double[]{1.0, 4.0, 3.0, 3.0, 3.0});
		Example a2 = new Example(new Double[]{1.0, 1.0, 3.0, 2.0, 0.0});
		Example a3 = new Example(new Double[]{4.0, 5.0, 3.0, 3.0, 1.0});
		Example a4 = new Example(new Double[]{1.0, 4.0, 3.0, 2.0, 2.0});
		Example a6 = new Example(new Double[]{1.0, 2.0, 3.0, 1.0, 1.0});
		Example a7 = new Example(new Double[]{5.0, 2.0, 6.0, 1.0, 1.0});
		Example a8 = new Example(new Double[]{1.0, 7.0, 3.0, 7.0, 3.0});
		Example[] a5 = {a, a1, a2, a3, a4, a6, a7, a8};
		ExampleSet exampleSetTest = new ExampleSet(a5);

		for(Example aaa: exampleSetTest){
			//System.out.println(aaa.getName());
			System.out.println(aaa.getLabel());
			System.out.println("Value " + aaa.getValue(1));
		}

		System.out.println("Size " + as.size());
		for(int i = 0; i<exampleSetTest.size(); i++){
			System.out.println(exampleSetTest.getAllLabels().get(i));
		}

		Set<Double> setOfLabels = new HashSet<Double>(exampleSetTest.getAllLabels());
		System.out.println(setOfLabels);
		System.out.println(exampleSetTest.getLabels());
		System.out.println("Get all labels " + exampleSetTest.getAllLabels());
		System.out.println("Majority label " + exampleSetTest.getMajorityLabel());
		System.out.println("Get Feature Values " + exampleSetTest.getFeatureValues(1));
		System.out.println("Is homogenous? " + exampleSetTest.isHomogenous());

		Example[] a9 = {e, a7, a6};
		ExampleSet testSet2 = new ExampleSet(a9);
		System.out.println("Is homogenous2 " + testSet2.isHomogenous());

	}
}