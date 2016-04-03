/**
 *	NaiveBayesMath contains methods to calculate figures required for the
 *	NaiveBayes class. These methods include calculations for the sum, mean
 *	probability density, and standard deviation.
 *
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		March 30, 2016
 *	
 */
package edu.classifier.ml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.lang.Math;

public class NaiveBayesMath{

	public NaiveBayesMath(){
		// Empty Constructor
	}

	/**
	 *	Sum a list of values
	 *	@param	listOfValues: list of values to sum
	 *	@return	Double sum of all values in input list
	 */
	public static Double sum(Collection<Double> listOfValues){
		Double total=0.0;

		// Loop through the items in the list to sum
		for(Double value:listOfValues){
			total += value;
		}

		return total;
	}

	/**
	 *	Get the PDF for a list of values
	 *	@param	valueToCheck: value whose probability needs to be checked
	 *	@param	listOfValues: list of values to calculate PDF from
	 *	@return	Double probability of all values in input list
	 */
	public static Double pdf(Double valueToCheck, Collection<Double> listOfValues){
		Double stdev   = std(listOfValues);
		Double average = mean(listOfValues);

		Double baseA   = stdev * Math.sqrt(2.0 * Math.PI);
		Double baseB   = Math.pow((valueToCheck - average), 2.0)/(Math.pow(stdev, 2.0) * 2.0);

		Double product = (1.0/baseA) * Math.exp(-baseB);
		return product;
	}

	/**
	 *	Calculate the mean of a list of values
	 *	@param	listOfValues: list of values to calculate the mean for
	 *	@return	mean value of listOfValues
	 */
	public static Double mean(Collection<Double> listOfValues){
		Double listTotal = sum(listOfValues);
		Integer listSize = listOfValues.size();

		Double quotient	 = listTotal/listSize;
		return quotient;
	}

	/**
	 *	Calculate the standard deviation of a list of values
	 *	@param	listOfValues: list of values to calculate the standard deviation for
	 *	@return	standard deviation of listOfValues
	 */
	public static Double std(Collection<Double> listOfValues){
		ArrayList<Double> newListOfValues = new ArrayList<>();

		Double average = mean(listOfValues);

		// Loop through the list of values to calculate their difference from the mean squared
		for(Double value:listOfValues){
			newListOfValues.add(Math.pow((average - value), 2.0));
		}

		Double standardDeviation = Math.sqrt(mean(newListOfValues));
		return standardDeviation;
	}

	public static void main(String[] args){
		ArrayList<Double> test = new ArrayList<Double>();
		test.add(2.0);
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		test.add(8.0);
		test.add(8.0);
		test.add(8.0);
		test.add(11.0);
		test.add(14.0);
		test.add(14.0);
		System.err.println(NaiveBayesMath.sum(test));
		System.err.println(NaiveBayesMath.pdf(8.0, test));
		System.err.println(NaiveBayesMath.mean(test));
		System.err.println(NaiveBayesMath.std(test));
	}
}