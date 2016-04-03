/**
 *	The MachineLearner abstract class outlines the required methods used by
 *	most if not all of the classifiers in this package. Each machine learner
 *	has their own unique training and testing implementation.
 *	
 *	@author		Glenn Abastillas
 *	@version	1.0.0
 *	@since		March 21, 2016
 *	
 */
 package edu.classifier.ml;

 import java.util.*;

 import edu.classifier.dataset.DataSet;

 public abstract class MachineLearner{

 	public static void train(DataSet dataSet){
 		// implement training code here;
 	}

 	public static void validate(ExampleSet validateSet){
 		// implement training code here;
 	}

 	public static test(ExampleSet testSet){
 		// implement training code here;
 	}

 }