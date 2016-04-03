/**	
 *	AttributeSet is a collection of Attribute objects. This class allows the
 *	user to access the attributes of a data set, convert example strings, and
 *	check attribute values in Example objects for machine learning, and get the
 *	types of the Attribute objects in it.
 *	
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		February 10, 2016
 *	
 *	AttributeSet() inherits from the abstract DataObjectSet<K> class, which provides basic functionality to access and change its data members.
 *	Methods inherited from DataObjectSet<K> include: 
 *			void add(K):			add K element to set
 *			void remove(int):		remove K element at int index
 *			K get(int):				get K element at int index
 *			Integer size():			get size of object's ArrayList
 *			Iterator<K> iterator():	get iterator for this object's ArrayList (overridden)
 *	
 *	AttributeSet() also has the following methods:	 										 	(comment added February 14, 2016)
 *			int getIndex(Attribute):	get the index of the Attribute specified	 			(comment added February 14, 2016)
 *			int getType(int):			get the type of the Attribute specified by its index	(comment added February 14, 2016)
 *	
 *	AttributeSet is inherited by the following classes: DataSet (comment added February 14, 2016) 
 */

package edu.classifier.dataset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;

import edu.templates.data.DataObjectSet;
import edu.classifier.dataset.Attribute;
import edu.classifier.dataset.Example;

public class AttributeSet extends DataObjectSet<Attribute>{

	public AttributeSet(){
		// Empty Constructor
	}

	/**
	 *	Constructor for AttributeSet with an array of Attributes as input
	 *	@param	attributes: an array of Attribute objects
	 */
	public AttributeSet(Attribute[] attributes){
		for(Attribute a: attributes){
			this.data.add(a);
		}
	}

	/**
	 *	Returns an iterator for AttributeSet allowing foreach iteration over this object.
	 *	@return	Iterator<K>	iterator from AttributeSet's ArrayList object to allow for iteration over this object.
	 */
	public Iterator<Attribute> iterator(){
		return this.data.iterator();
	}

	/**
	 *	Get the index of an Attribute object
	 *	@param	Attribute object to check
	 *	@return	Integer index of the Attribute object
	 */
	public int getIndex(Attribute attribute) throws NoSuchElementException{
		System.err.println("In getIndex() method:\t" + attribute.getName());
		if(!this.data.contains(attribute)){
			throw new NoSuchElementException("In getIndex() method, specified attribute does not exist in this AttributeSet."); 
		}
		return this.data.indexOf(attribute);
	}

	/**
	 *	Get the index of an Attribute object specified by its name
	 *	@param	attributeName: String attribute name
	 *	@return	Integer index of the Attribute object
	 */
	public int getIndex(String attributeName) throws NoSuchElementException{

		// Loop through the attributes and compare their names with input
		for(Attribute attribute : this.data){

			// If there is a name match, return the index
			if(attributeName.toLowerCase().equals(attribute.getName())){
				return this.data.indexOf(attribute);
			}
		}
		
		// Return an error otherwise
		throw new NoSuchElementException("In getIndex() method, specified attribute does not exist in this AttributeSet.");
	}

	/**
	 *	Returns the type of the attribute whose index is pass through the function.
	 *	@param	integer		number pertaining to the Attribute whose type will be returned
	 *	@return				integer pertaining to the specified Attribute's type (i.e., 0 for nominal; 1 for numeric)
	 */
	public int getType(int index){
		return this.data.get(index).getType();
	}

	/**
	 *	Get the types for all the Attribute objects in this set
	 *	@return List of types for each Attribute object
	 */
	public ArrayList<Integer> getTypes(){
		ArrayList<Integer> types = new ArrayList<>();

		// Loop through the attributes to get their types
		for(Attribute attribute : data){
			types.add(attribute.getType());
		}
		
		return types;
	}

	public static void main(String[] args){

		AttributeSet as = new AttributeSet();
		Attribute a = new Attribute("name", "nom", "cold hot warm cool");
		Example   e = new Example(new Double[]{1.0, 4.0, 2.0, 1.0});

		System.out.println(a.labels());
		as.add(a);
		//as.add(e);

		System.out.println(as.size());
		System.out.println(as.get(0).getName());


		Attribute a1 = new Attribute("cars", "num", "cold hot warm cool");
		Attribute a2 = new Attribute("trees", "nom", "pine fir maple oak hot");
		Attribute a3 = new Attribute("people", "num", "brett marge greg herman cool");
		Attribute a4 = new Attribute("doug", "nom", "patti skeeter beebe roger");
		Attribute[] a5 = {a, a1, a2, a3, a4};
		AttributeSet attributeSetTest = new AttributeSet(a5);

		for(Attribute aaa: attributeSetTest){
			System.out.println(aaa.getName());
			System.out.println(aaa.getLabel(2.0));
			System.out.println("Get type\t" + attributeSetTest.getIndex("DoUg"));
		}

		HashMap<Double, Double> testMap = new HashMap<>();
		testMap.put(1.0, 1.0);
		testMap.put(2.0, 1.0);
		testMap.put(3.0, 1.0);
		testMap.put(4.0, 1.0);
		for(Map.Entry entry : testMap.entrySet()){
			System.err.println(entry.getValue());
		}

		Attribute a6 = new Attribute("cats", "num", "232 123 141 1");
		System.out.println(attributeSetTest.getIndex(a6));


	}
}