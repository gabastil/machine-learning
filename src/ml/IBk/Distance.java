/**
 *	Distance contains static methods to calculate the distance between two
 *	points. All methods require input to be arrays of Doubles. This class is
 *	particularly useful for the IBk, a.k.a. the K-Distance, classifier.
 *	
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		March 21, 2016
 *	
 */
package edu.classifier.ml;

import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.lang.Math;

import edu.classifier.dataset.*;

public class Distance{

	/**
	 *	Calculate the distance between two vectors
	 *	@param	vector1: array of values
	 *	@param	vector2: array of values
	 *	@param	power: power to raise the differences by in the for loop
	 *	@return	sum: Double distance value
	 */
	public static Double get(Example vector1, Example vector2, Integer power){
		Double sum=0.0;

		// Loop through the vectors and calculate the Euclidean distance
		for(int i=0; i < vector1.size(); i++){
			sum += Math.pow((vector1.getValue(i)-vector2.getValue(i)), power);
		}
		sum = Math.sqrt(sum);
		return sum;
	}

	/**
	 *	Calculate the Euclidean distance between two vectors
	 *	@param	vector1: array of values
	 *	@param	vector2: array of values
	 *	@return	sum: Double distance value
	 */
	public static Double getEuclideanDistance(Example vector1, Example vector2){
		return Distance.get(vector1, vector2, 2);
	}

	/**
	 *	Calculate the Manhattan distance between two vectors
	 *	@param	vector1: array of values
	 *	@param	vector2: array of values
	 *	@return	sum: Double distance value
	 */
	public static Double getManhattanDistance(Example vector1, Example vector2){
		Double sum=0.0;

		// Loop through the vectors' values and calculate Manhattan Distance
		for(int i=0; i < vector1.size(); i++){
			sum += Math.abs(vector1.getValue(i) - vector2.getValue(i));
		}

		return sum;
	}

	/**
	 *	Calculate the Chebyshev distance between two vectors
	 *	@param	vector1: array of values
	 *	@param	vector2: array of values
	 *	@return	Double of the max value calculated
	 */
	public static Double getChebyshevDistance(Example vector1, Example vector2){
		ArrayList<Double> sums = new ArrayList<Double>();

		for(int i=0; i < vector1.size(); i++){
			sums.add(Math.abs(vector1.getValue(i)-vector2.getValue(i)));
		}

		return Collections.max(sums);
	}

	/**
	 *	Calculate the Hamming distance between two vectors
	 *	@param	vector1: array of values
	 *	@param	vector2: array of values
	 *	@return	Double of the number of mismatches
	 */
	public static Double getHammingDistance(Example vector1, Example vector2){
		Double sum=0.0;

		for(int i=0; i < vector1.size(); i++){
			if(!vector1.getValue(i).equals(vector2.getValue(i))){
				sum += 1;
			}
		}
		return sum;
	}

	public static void main(String[] args){
		Distance d = new Distance();
		Example a = new Example(new Double[]{0.0, 3.0, 0.0});
		Example b = new Example(new Double[]{4.0, 0.0, 0.0});

		System.out.println("Results Euclidean Distance: " + d.getEuclideanDistance(a, b));
		System.out.println("Results Manhattan Distance: " + d.getManhattanDistance(a, b));
		System.out.println("Results Chebyshev Distance: " + d.getChebyshevDistance(a, b));
		System.out.println("Results Hamming Distance: " + d.getHammingDistance(a, b));
		//System.out.println("Results: " + d.get(a, b, 2));
	}
}