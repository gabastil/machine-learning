/**
 *	Kernel is a container that contains three values and their associated class
 *	label. If the Kernel is filled to capacity (=3) and another value and assoc
 *	iated label is added to it, the highest value and associated label is taken
 *	out and replaced by the new value and associated label.
 *
 *	If there is a majority label present in the Kernel, it may be accessed by
 *	the getMajority() method. If there is no majority label, the label with the
 *	lowest distance score (i.e., closest to the unclassified example) is 
 *	returned.
 *
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		March 21, 2016
 *	
 */
package edu.classifier.ml;

import java.util.Collections;
import java.util.ArrayList;

public class Kernel{

	ArrayList<Double> value = new ArrayList<Double>();	// value holds the results of distance calculations between the unclassified example and training examples
	ArrayList<Double> label = new ArrayList<Double>();	// label holds the labels of the training examples closest in distance to the unclassified example

	public Kernel(){
		// Empty Constructor
	}

	/**
	 *	Add a value to the kernel
	 *	@param	value: double value representing class labels
	 *	@param	classLabel: class label associated with value
	 */
	public void add(Double value, Double label){

		// If the number of items in this.value and this.label is greater than 3
		// remove the highest value and associated label and add the new one
		if(this.value.size() >= 3){
			Double highestValue = Collections.max(this.value);
			Integer highestValueIndex = this.value.indexOf(highestValue);
			Double highestValueLabel = this.label.get(highestValueIndex);

			// Remove the values associated labels at the highestValueIndex
			// Add the specified value associated label to this.value and this.label
			if(highestValue > value){
				this.value.remove((double) highestValue);
				this.label.remove(highestValueLabel);
				this.value.add(highestValueIndex, value);
				this.label.add(highestValueIndex, label);
			}

		} else {

			// Add the specified value associated label to this.value and this.label
			this.value.add(value);
			this.label.add(label);
		}
	}

	/**
	 *	Get the majority class label in this kernel
	 *	@return	majorityValue: double representing class label
	 */
	public Double getMajorityLabel(){

		Double countLabel1 = (double) Collections.frequency(this.label, this.label.get(0));
		Double countLabel2 = (double) Collections.frequency(this.label, this.label.get(1));
		
		// Return first label if its count is 2/3
		if(countLabel1.equals(2.0)){
			return this.label.get(0);

		// Return second label if its count is 2/3
		} else if(countLabel2.equals(2.0)){
			return this.label.get(1);

		// Else return label with lowest associated distance value
		} else {
			Double lowestValue = Collections.min(this.value);
			Integer lowestValueIndex = this.value.indexOf(lowestValue);
			return this.label.get(lowestValueIndex);
		}
	}

	public static void main(String[] args){
		Kernel kernel = new Kernel();
		kernel.add(2.0, 7.0);
		kernel.add(2.0, 3.0);
		kernel.add(3.0, 5.0);
		kernel.add(1.0, 7.0);
		System.out.println(kernel.getMajorityLabel());
		System.out.println(kernel.value);
		System.out.println(kernel.label);
		kernel.add(0.3, 9.0);
		kernel.add(0.4, 11.0);
		kernel.add(0.5, 13.0);
		kernel.add(0.6, 15.0);
		System.out.println(kernel.getMajorityLabel());
		System.out.println(kernel.value);
		System.out.println(kernel.label);
}
	}