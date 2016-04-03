/**
 *	Attribute objects contain information for a specified attribute in a data
 *	set. This class inherits properties and methods from the DataObject class
 *	including methods to: (1) get/set name, (2) get/set type, (3) get the value
 *	of a String label, (4) get all the attribute properties, (5) get all of the
 *	labels, which are String representations of the data.
 *
 * 	@author		Glenn Abastillas
 * 	@version	1.1.0
 * 	@since		February 9, 2016
 *
 *	update	February 10, 2016	added getLabel(int) method; returns String
 * 
 * 	Attribute() inherits from the DataObject() class, which provides basic functionality to access and change its data members.
 * 	Methods inherited from DataObject include: 
 *		String getName(): 		get the data object's name
 *		void setName(String): 	set the data object's name
 *		int getType(): 			get the data object's type
 *		void setType(int):		set the data object's type
 *		ArrayList values(): 	get the data object's values
 *		ArrayList labels(): 	get the data object's labels
 * 
 * 	Attribute() also has the following methods:
 * 		getValue(String):	get the attribute's value corresponding to label parameter
 *		getLabel(int):		get the the label value corresponding to the int index input (added February 10, 2016)
 *		getLabel(Double):	get the the label value corresponding to the Double index input
 *		getIndex():			get the index for this attribute when part of an AttributeSet
 *		setIndex(int):		set the index for this attribute when part of an AttributeSet
 *		iterator():			iterate over the values in the data variable (values are the keys of the TreeMap)
 * 
 *	Attribute is inherited by the following classes: DataObjectSet (comment added February 11, 2016)
 */

package edu.classifier.dataset;

import edu.templates.data.DataObject;
import java.util.*;

public class Attribute extends DataObject{

	/**
	 * Constructor with no parameters
	 */
	public Attribute(){
		this("none", "nominal", new String[]{"none"});
	}

	/**
	 * Constructor with name specified
	 * @param	name: Attribute name
	 */
	public Attribute(String name){
		this(name, "nominal", new String[]{"None"});
	}

	/**
	 * Constructor with name and type specified
	 * @param	name: Attribute name 
	 * @param	type: Attribute type 
	 */
	public Attribute(String name, String type){
		this(name, type, new String[]{"None"});
	}

	/**
	 * Constructor with name and type specified
	 * @param	name: Attribute name 
	 * @param	type: Attribute type 
	 * @param	labels: Attribute's labels if nominal
	 */
	public Attribute(String name, String type, String labels){
		this(name, type, labels.split(" "));
	}

	/**
	 * Constructor with name and type specified
	 * @param	name: Attribute name 
	 * @param	type: Attribute type 
	 * @param	labels: Attribute's labels if nominal as an array
	 */
	public Attribute(String name, String type, String[] labels){
		this.setName(name);
		this.setType(type);

		for(int i = 0; i < labels.length; i++){
			this.data.put(labels[i].toLowerCase(), (double)i);
		}
	}

	/**
	 *	Get value for String input for this attribute
	 *	@param	String	label	label to be converted into a numerical value for this attribute
	 *	@return Double	value	numerical value of label
	 */
	public Double getValue(String label){
		Double value = 999.99;
		for(Map.Entry<String, Double> e: this.data.entrySet()){
			if(label.toLowerCase().equals(e.getKey())){
				value = e.getValue();
				break;
			}
		}
		if(value.equals(999.99)){
			value = Double.parseDouble(label);
		}
		return value;
	}

	/** 
	 *	Get label for integer value input for this attribute. If no label is found, 'NA' is returned.
	 *	@param	int		value	integer value to be converted into a label for this attribute
	 *	@return	String	label	label of integer value input. If value is not found in this Attribute's data, 'NA' is returned.
	 */
	public String getLabel(int value){
		return this.getLabel((double) value);
	}

	/** 
	 *	Get label for Double value input for this attribute
	 *	@param	Double	value	double value to be converted into a label for this attribute
	 *	@return	String	label	label of integer value input. If value is not found in this Attribute's data, 'NA' is returned.
	 */
	public String getLabel(Double value){
		String label = "NA";
		for(Map.Entry<String, Double> e: this.data.entrySet()){
			//System.out.println("Attribute.java\tKey:\t" + e.getKey() + "\tValue:\t" + value);
			if(value.equals(e.getValue())){
				label = e.getKey();
				break;
			}
		}
		return label;
	}

	/** 
	 * Get the index for this attribute (only when part of a set); Default is 0. 
	 * This variable is inherited from the DataObject class.
	 * @return	index of an instance of this Attribute
	 */
	public int getIndex(){
		return this.index;
	}

	/**
	 *	Get this object's iterator
	 *	@return	an Iterator for this Attribute's HashMap
	 */
	public Iterator<Double> iterator(){
		return this.data().iterator();
	}

	/**
	 *	Set the index for this Attribute
	 *	@param	index: integer referring to this Attribute's location in an AttributeSet
	 */
	public void setIndex(int index){
		this.index = index;
	}

	/**
	 *	Print a String representation of this Attribute to the screen
	 */
	public void asString(){
		String output = "@" + this.name + " ";

		if(this.type == 0){
			output += "nominal ";
		} else if(this.type == 1){
			output += "numeric ";
		}

		for(Map.Entry<String, Double> entry : this.data.entrySet()){
			output += entry.getKey() + " ";
		}

		System.out.println(output);
	}

	// MAIN METHOD
	public static void main(String[] args){
		String[] testString = new String[]{"this", "is", "a", "test"};
		Attribute attribute = new Attribute("People", "nominal", testString);
		ArrayList<String> testList = new ArrayList<String>();

		System.out.println(attribute.labels());

		for(int i = 0; i < testString.length; i++){
			System.out.println(testString[i]);
		}

		for(int i=0; i<10; i++){
			testList.add(String.format("The int is:\t%s", i));
		}

		System.out.println("Hello World");
		System.out.println(attribute.getName());
		System.out.println(testList.size());

		for(int i = 0; i < 10; i++){
			System.out.println(String.format("\t%s", testList.get(i)));
		}

		System.out.println(attribute.data());
		System.out.println(attribute.labels());
		System.out.println("This is a test\t" + attribute.getLabel(1.0));
		System.out.println("This is a test\t" + attribute.getValue("this"));
	
		for(Double value: attribute){
			System.out.println("Iterator " + value);
		}

	}

}