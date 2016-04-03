/**
 *	This interface sets the required methods for the DataObjectSet class.
 *	The four required methods relate to accessing the data members of a HashMap
 *	variable called data as well as getting its size.
 *	
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		February 9, 2016
 *	
 *	@update		None
 *	
 */
package edu.templates.data.interfaces;

import java.util.*;

public interface DataObjectSetInterface<T>{
	
	public void add(T element);

	public void remove(int index);

	public T get(int index);

	public Integer size();

}
