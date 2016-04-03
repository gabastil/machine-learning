/** 
 *	@name		DatObjectSet.java
 *  @author		Glenn Abastillas
 *  @version	1.0.0
 *  @since		February 10, 2016
 *  
 *  
 * 	DataObjectSet() implements the Iterable<K> interface to allow for iteration over its protected ArrayList<K> variable. This
 * 	Abstract Class provides data object set functionality for edu.classifier.AttributeSet and edu.classifier.ExampleSet classes.
 * 	This class includes the following classes: 
 * 		void add(K):			add K element to set
 * 		void remove(int):		remove K element at int index
 * 		K get(int):				get K element at int index
 * 		Integer size():			get size of object's ArrayList
 * 		Iterator<K> iterator():	get iterator for this object's ArrayList (overridden)
 * 
 *	DataObjectSet is inherited by the following classes: AttributeSet, ExampleSet
 */

package edu.templates.data;
import java.util.*;

public abstract class DataObjectSet<K> implements Iterable<K>{

	protected ArrayList<K> data = new ArrayList<K>();	

	// Add K element to the data ArrayList if it does not exist
	public void add(K element){
		if(!this.data.contains(element)){
			this.data.add(element);
		}
	}

	// Remove K element at int index from Arraylist
	public void remove(int index){
		this.data.remove(index);
	}

	// Return K element at int index
	public K get(int index){
		return this.data.get(index);
	}

	// Return K element at Integer index
	public K get(Integer index){
		return this.data.get(index);
	}

	// Return size of ArrayList
	public Integer size(){
		return this.data.size();
	}

	// Return iterable for this ArrayList/Class
	public Iterator<K> iterable(){
		return this.data.iterator();
	}

	/**
	 *	Get this class's data
	 *	@return	List of this class's data
	 */
	public ArrayList<K> data(){
		return this.data;
	}

}