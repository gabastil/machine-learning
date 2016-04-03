/** 
 *	This Example object holds values from a data set constructed by a Factory.
 *	This object inherits methods from the DataObject class, which includes the
 *	following methods: 
 *	
 *  @author		Glenn Abastillas
 *  @version	1.0.1
 *  @since		February 9, 2016
 *  
 *  update	February 10, 2016	added comments to getLabel() method and edited documentation to update new method names (e.g., getName())
 *  
 * 	Example() inherits from the DataObject() class, which provides basic functionality to access and change its data members.
 * 	Methods inherited from DataObject include: 
 *		String getName(): 		get the data object's name (deprecate)
 *		void setName(String):	set the data object's name (deprecate)
 *		int getType(): 			get the data object's type (deprecate)
 *		void setType(int): 		set the data object's type (deprecate)
 *		ArrayList values(): 	get the data object's values
 *		ArrayList labels(): 	get the data object's labels
 * 
 * 	Example() also has the following methods:
 * 		getValue():			get the attribute's value corresponding to label parameter
 *		getLabel():			set the attribute's label corresponding to Double value parameter
 *		getIndex():			get the index for this attribute when part of an AttributeSet
 *		setIndex(int):		set the index for this attribute when part of an AttributeSet
 *		iterator():			iterate over the values in the data variable (values are the keys of the TreeMap)
 *
 *	Example is inherited by the following classes: ExampleSet
 */

package edu.classifier.dataset;

import edu.templates.data.DataObject;
import java.util.*;

public class Example extends DataObject{

	protected Double classLabel;

	// Constructor #1: Array of Double values required
	public Example(Double[] values){
		for(int i = 0; i < values.length-1; i++){
			//System.out.println("values[i]: " + values[i] + ";\tInteger.toString: " + Integer.toString(i));
			this.data.put(Integer.toString(i), values[i]);
		}

		//System.out.println("size " + this.data.size());
		this.classLabel = values[values.length-1];
	}

	/** 
	 *	Get example's class label
	 *	@return	Double	classLabel	label of this example's classification.
	 */
	public Double getLabel(){
		return this.classLabel;
	}

	public void asString(AttributeSet attributeSet){
		String output = "#";
		int index = 0;
		
		for(Map.Entry<String, Double> entry : this.data.entrySet()){
			//Double entryValue = ;
			//System.out.println("Index:\t" + index);
			//System.out.println("Value:\t" + entry.getValue());
			//System.out.println("Label:\t" + attributeSet.get(index).getLabel(entry.getValue()));
			output += attributeSet.get(index).getLabel(entry.getValue()) + " ";
			index++;
		}

		output += attributeSet.get(index).getLabel(this.classLabel);
		System.out.println(output);
	}

	public static void main(String[] args){
		Example e = new Example(new Double[]{1.0,2.0,3.0,1.0});
		System.out.println(e.getLabel());
		System.out.println(e.getValue(0));
		System.out.println(e.getValue(1));
		System.out.println(e.getValue(2));
		System.out.println("Less than zero \t" + e.getValue(-1));
		System.out.println("Less than zero \t" + e.getValue(-2));
		System.out.println("Less than zero \t" + e.getValue(-3));

		for(Double value:e){
			System.out.println("Iterator " + value);
		}
	}
}