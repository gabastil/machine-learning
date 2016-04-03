/**	
 *	AttributeFactory creates Attribute objects from attributes read into from a
 *	data set file (e.g., *.gla). Attribute objects can be made from a String of 
 *	an attribute from the data set file (e.g., "@attribute type val1 val2 val3")
 *	Also, AttributeFactory builds AttributeSets from two types of input: (1) an
 *	array of Attributes or (2) an array of Strings representing the attributes.
 *	
 *	The objects produced by this factory are used to convert textual examples
 *	from the dataset input to arrays of Doubles.
 *	
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		February 16, 2016
 *	 
 *	AttributeFactory() is an class that creates Attribute.class objects from textual input from .gla files in the following format:
 *
 *	Example of a String line input:		'@attributeName	attributeType	attributeValues...'
 *
 *	AttributeFactory implements the following methods:
 *	Attribute makeAttribute(String):	String input gets converted into an Attribute object.
 *
 *	AttributeFactory is inherited by the following classes: DataSet
 *
 */
package edu.classifier.dataset;

import edu.classifier.dataset.Attribute;
import edu.classifier.dataset.AttributeSet;

public class AttributeFactory{

	/**
	 *	Create an Attribute object from String inputs of attributes from a data set
	 *	@param	attributeAsString: String representation of an attribute from a data set
	 *	@return	Attribute object
	 */
	public static Attribute makeAttribute(String attributeAsString){
		String[] attributeAsSplitString = attributeAsString.split("\t");
		return new Attribute(attributeAsSplitString[0].replace("@", ""), attributeAsSplitString[1], attributeAsSplitString[2]);
	}

	/**
	 *	Create an AttributeSet object from an array of Attribute objects
	 *	@param	attributes: an array of Attribute objects
	 *	@return	AttributeSet object
	 */
	public static AttributeSet makeAttributeSet(Attribute[] attributes){
		return new AttributeSet(attributes);
	}

	/**
	 *	Create an AttributeSet object from an array of String attributes
	 *	@param	attributesAsString: an array of attributes as Strings
	 *	@return	AttributeSet object
	 */
	public static AttributeSet makeAttributeSet(String[] attributesAsString){
		AttributeSet attributeSet = new AttributeSet();

		for(String attribute: attributesAsString){
			attributeSet.add(AttributeFactory.makeAttribute(attribute));
		}

		return attributeSet;
	}


	public static void main(String[] args){
		AttributeFactory f = new AttributeFactory();
		Attribute a1  = f.makeAttribute("@name	nom	val1 val2 val3");
		Attribute a2  = f.makeAttribute("@name	nom	val1 val2 val3");
		Attribute a3  = f.makeAttribute("@name	nom	val1 val2 val3");
		System.out.println(a1.getName());
		System.out.println(a1.getIndex());
		System.out.println(a1.getLabel(2));
		AttributeSet as = AttributeFactory.makeAttributeSet(new Attribute[]{a1, a2, a3});
	}
}