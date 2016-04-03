/** 
 *  @author		Glenn Abastillas
 *  @version	1.0.1
 *  @since		February 16, 2016
 *  
 *	@update		February 20, 2016 <Version to 1.0.1 from 1.0.0> added methods: makeExample(), makeAttributeSet() x 2, makeExampleSet() x 2
 *  
 * 	Factory() is an abstract class that creates Attribute.class objects from textual input from .gla files in the following format:
 *
 *		Example of a String attribute line input:	'@attributeName	attributeType	attributeValues...'
 *		Example of a String example line input:		'#value1 value2 value3 label4'
 *
 * 	Factory implements the following methods:
 *		Attribute makeAttribute(String):					Return an Attribute from an input String.
 *		Example makeExample(AttributeSet, String):			Return an Example from an input String and AttributeSet (required)
 *		AttributeSet makeAttributeSet(Attribute[]):			Return an AttributeSet created from an array of Attributes.
 *		AttributeSet makeAttributeSet(String[]):			Return an AttributeSet created from an array of String attributes.
 *		ExampleSet makeExampleSet(Example[]):				Return an ExampleSet created from an array of Examples
 *		ExampleSet makeExampleSet(AttributeSet, String[]):	Return an ExampleSet created from an array of Examples and AttributeSet (required)
 *
 * Factory is inherited by the following classes: AttributeFactory, ExampleFactory, DataSet
 */

package edu.classifier.dataset;

import java.util.*;
import edu.classifier.dataset.AttributeSet;
import edu.classifier.dataset.ExampleSet;
import edu.classifier.dataset.Attribute;
import edu.classifier.dataset.Example;

public class Factory{

	// Returns an Attribute from a String of attributes
	public static Attribute makeAttribute(String stringAttributeInput){
		String[] splitStringAttributeInput = stringAttributeInput.split("\t");
		int nameIndex = 0;
		int typeIndex = splitStringAttributeInput.length - 2;
		int dataIndex = splitStringAttributeInput.length - 1;

		return new Attribute(splitStringAttributeInput[nameIndex].replace("@", ""), splitStringAttributeInput[typeIndex], splitStringAttributeInput[dataIndex]);
	}

	// Returns an Example using an AttributeSet to process an example String
	public static Example makeExample(AttributeSet attributeSet, String stringExampleInput){
		String[] splitStringExampleInput = stringExampleInput.split(" ");
		Double[] doubleExampleInput 	 = new Double[splitStringExampleInput.length];

		for(int i = 0; i < splitStringExampleInput.length; i++){
			doubleExampleInput[i] = attributeSet.get(i).getValue(splitStringExampleInput[i].replace("#", ""));
			//System.out.println("Double example output || " + attributeSet.get(i).getName() + " || input || " + 
			//					splitStringExampleInput[i] + " || value || " +  attributeSet.get(i).getValue(splitStringExampleInput[i].replace("#", "")) +
			//					" || Attributes || " + attributeSet.get(i).data());
		}
		return new Example(doubleExampleInput);
	}

	// Returns an AttributeSet from an array of Attributes
	public static AttributeSet makeAttributeSet(Attribute[] attributes){
		return new AttributeSet(attributes);
	}

	// Returns an AttributeSet from array of String attributes
	public static AttributeSet makeAttributeSet(String[] attributesAsString){
		AttributeSet attributeSet = new AttributeSet();

		for(String attribute: attributesAsString){
			attributeSet.add(Factory.makeAttribute(attribute));
		}

		return attributeSet;
	}

	// Returns an ExampleSet from an array of Examples
	public static ExampleSet makeExampleSet(Example[] examples){
		return new ExampleSet(examples);
	}

	// Returns an ExampleSet from an array of String examples and an AttributeSet to process the examples
	public static ExampleSet makeExampleSet(AttributeSet attributeSet, String[] examplesAsString){
		ExampleSet exampleSet = new ExampleSet();

		for(String example: examplesAsString){
			exampleSet.add(Factory.makeExample(attributeSet, example));
		}

		return exampleSet;
	}

	public static void main(String[] args){
		String sa1 = "@name	nom	val1 val2 val3";
		String sa2 = "@car	nom	car1 car2 car3";
		String sa3 = "@lang	nom	lan1 lan2 lan3";
		String sa4 = "@child	nom	kid1 kid2 kid3";

		Factory f = new Factory();
		Attribute a0  = f.makeAttribute(sa1);
		Attribute a1  = f.makeAttribute(sa2);
		Attribute a2  = f.makeAttribute(sa3);
		Attribute a3  = f.makeAttribute(sa4);
		AttributeSet as = new AttributeSet(new Attribute[]{a0, a1, a2, a3});
		AttributeSet as2 = f.makeAttributeSet(new String[]{sa1, sa2, sa3, sa4});

		String se1 = "val1 car1 lan1 kid1";
		String se2 = "val3 car2 lan1 kid1";
		String se3 = "val2 car1 lan3 kid2";
		String se4 = "val2 car3 lan2 kid3";

		Example e1 = f.makeExample(as, se1);
		System.out.println("\n\n\n\ne1: " + e1.data() + "\te1 label: " + e1.labels());
		Example e2 = f.makeExample(as, se2);
		Example e3 = f.makeExample(as, se3);
		Example e4 = f.makeExample(as, se4);
		ExampleSet es = f.makeExampleSet(new Example[]{e1, e2, e3, e4});

		System.out.println(a0.getName());
		System.out.println(as2.get(2).getName());
		System.out.println("Majority Label:\t" + es.getMajorityLabel());
		System.out.println("Example 1:\t" + e1.data());
		System.out.println("Example 2:\t" + e2.data());
		System.out.println("Example 3:\t" + e3.data());
		System.out.println("Example 4:\t" + e4.data());
		System.out.println(es.get(1).getValue(3));
	}
}