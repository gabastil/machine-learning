/** 
 * ExampleFactory creates Example objects from examples read in from a data set
 * file (e.g., *.gla). This class can create Example objects from two types of 
 * data input: (1) Array of Doubles corresponding to each of the example's 
 * values representing their nominal or numerical attribute in the data set's 
 * AttributeSet or (2) a String of the example with a supporting AttributeSet 
 * to conver the string into an array of Doubles for Example construction. 
 * Example objects require arrays of Doubles for construction, which is why 
 * there is a base method that does just that as well as a method for converting
 * String examples into arrays of Doubles.
 *
 *  @author		Glenn Abastillas
 *  @version	1.0.0
 *  @since		February 16, 2016
 *  
 * 	ExampleFactory() is an class that creates Example.class objects from textual input from .gla files in the following format:
 *
 *		Example of a String line input:		'value1 value2 value3 classLabel'
 *
 * 	ExampleFactory implements the following methods:
 *		Example makeExample(String):	String input gets converted into an Example object.
 *
 * 	ExampleFactory is inherited by the following classes: DataSet
 *
 */

package edu.classifier.dataset;

import edu.classifier.dataset.Example;
import edu.classifier.dataset.ExampleSet;

public class ExampleFactory{

	/**
	 * Create an Example object from an array of doubles as input
	 * @param	exampleAsArrayOfDoubles: array containing double values corresponding to examples' values
	 * @return	Example object
	 */
	public static Example makeExample(Double[] exampleAsArrayOfDoubles){
		// Return a newly constructed Example object, which requires an array of Doubles
		return new Example(exampleAsArrayOfDoubles);
	}

	/**
	 * Create an Example object from a string input and a specified AttributeSet
	 * @param	attributeSet: AttributeSet object containing Attributes
	 * @param	exampleAsString: String representation of the example from a data set
	 * @return	Example object
	 */
	public static Example makeExample(AttributeSet attributeSet, String exampleAsString){
		String[] exampleAsSplitString = exampleAsString.replace("#", "").split(" ");
		Double[] doubleExampleInput   = new Double[exampleAsSplitString.length];

		for(int i = 0; i < exampleAsSplitString.length; i++){
			doubleExampleInput[i] = attributeSet.get(i).getValue(exampleAsSplitString[i]);
		}
		return new Example(doubleExampleInput);
	}

	/**
	 * Create an ExampleSet object from an array of Example objects
	 * @param	examples: an array of Example objects
	 * @return	ExampleSet object
	 */
	public static ExampleSet makeExampleSet(Example[] examples){
		return new ExampleSet(examples);
	}

	/**
	 * Create an ExampleSet object from an array of Strings of examples and an AttributeSet
	 * @param	attributeSet: an AttributeSet object used to convert examples' values
	 * @param	examplesAsString: an array of Strings of examples
	 * @return	ExampleSet object
	 */
	public static ExampleSet makeExampleSet(AttributeSet attributeSet, String[] examplesAsString){
		ExampleSet exampleSet = new ExampleSet();

		// Loop through the String examples to create individual Example objects to add to the ExampleSet
		for(String example: examplesAsString){
			exampleSet.add(ExampleFactory.makeExample(attributeSet, example));
		}

		return exampleSet;
	}

	public static void main(String[] args){
		ExampleFactory f = new ExampleFactory();
		Example a1  = f.makeExample(new Double[]{0.0,1.0,2.0,3.0});
		Example a2  = f.makeExample(new Double[]{0.0,1.0,2.0,3.0});
		Example a3  = f.makeExample(new Double[]{0.0,1.0,2.0,3.0});
		System.out.println(a1.getValue(2));
		ExampleSet es = ExampleFactory.makeExampleSet(new Example[]{a1, a2, a3});
	}
}