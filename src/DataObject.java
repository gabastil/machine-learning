/**	
 *	@author		Glenn Abastillas 
 *	@version	1.0.0
 *	@since		February 9, 2016
 *	
 *	@update		Feburary 16, 2016: 	Updated the getValues(int) method to correctly return the value at the given index. Removed the -1 operation on the input because
 *				the ArrayList is already -1 in size as the classification label is stored in a different variable. (Will be formatted later)
 *	
 *	DataObject provides universal data and methods that are used by edu.classifier.Attribute and edu.classifier.Example. 
 *	These data pertain to the names, types, and lists of data for each class:
 *	
 *	name   = name of object
 *	type   = type of attribute (e.g., 0 = nominal, 1 = numeric)
 *	values = contains list of possible values as for Attribute or sampled value for Example
 *	labels = contains String value for Double Attribute value in 'values'
 *
 *	DataObject is inherited by the following classes: Attribute, Example (comment added February 11, 2016)
 */

package edu.templates.data;
import java.util.*;

public class DataObject implements Iterable<Double>{
	
	protected String name;		// This variable will be used by edu.classifier.Attribute and edu.classifier.Example
	protected int    type;		// This variable will be used by edu.classifier.Attribute
	protected int 	 index = 0; // This variable will be used to indicate the index of this object

	protected TreeMap<String, Double> data = new TreeMap<String, Double>();	// This variable will be used by edu.classifier.Attribute and edu.classifier.Example

	protected static final String[] nominal = new String[]{"nominal", "nom", "c", "0"};	// This variable is used to check the kind of data an attribute receives.
	protected static final String[] numeric = new String[]{"numeric", "num", "n", "1"};	// This variable is used to check the kind of data an attribute receives.
	
	// Set name to string input
	public void setName(String name){
		this.name = name;
	}

	// Set type to 0 or 1 depending on string input
	public void setType(String type){
		type = type.toLowerCase();

		for(int i = 0; i < this.nominal.length; i++){
			if(type.equals(nominal[i])){
				this.type = 0;
				break;
			} else if(type.equals(numeric[i])){
				this.type = 1;
				break;
			}
		}
	}

	// Return name of this object
	public String getName(){
		return this.name;
	}

	// Return type of this object
	public int getType(){
		return this.type;
	}

	/**
	 *	Return the value of data at the index specified
	 *	@param	index: integer referring to the location of the value in data
	 */
	public Double getValue(int index){
		ArrayList<Double> values = this.data();

		if(index > values.size()){

			// If the index is greater than the size of the ArrayList, return the last object in the list
			return values.get(values.size());

		} else if(index < 0 && (index + values.size()) > 0.0){

			// If the number of values minus index value are greater than zero, return that index (e.g., -2 + 3 = index of 1)
			return values.get(values.size()+index);

		} else if(index < 0 && (index + values.size()) <= 0.0){

			// If the number of values minus index value are less than or equal to zero, return the zeroeth index
			return values.get(0);

		} else {

			// Return 
			return values.get(index);
		}
	}

	// Return ArrayList<Double> of values for this object
	public ArrayList<Double> data(){
		ArrayList<Double> values = new ArrayList<Double>();
		for(Map.Entry<String, Double> entry: this.data.entrySet()){
			//System.out.println(entry.getValue());
			values.add(entry.getValue());
		}
		//System.out.println("THese are the values " + values);
		return values;
	}

	// Return ArrayList<String> of labels for this object
	public ArrayList<String> labels(){
		ArrayList<String> labels = new ArrayList<String>();

		for(Map.Entry<String, Double> entry: this.data.entrySet()){
			//System.out.println(entry.getValue());
			labels.add(entry.getKey());
		}
		return labels;
	}

	// Iterator for the values in TreeMap data, allow foreach iteration
	@Override
	public Iterator<Double> iterator(){
		return this.data().iterator();
	}

	/**
	 *	Get the size of the data ArrayList
	 *	@return	integer of the size of elements in data
	 */
	public int size(){
		return this.data().size();
	}

	public static void main(String[] args){
		// Empty main;
	}

}