package Calculator;

import java.util.Scanner;

import uNumberLibrary.UNumber;

/**
 * <p> Title: CalculatorValue Class. </p>
 * 
 * <p> Description: A component of a JavaFX demonstration application that performs computations </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2017 </p>
 * 
 * @author Lynn Robert Carter
 * @author Sajib Biswas 
 * 
 * @version 4.00	2017-10-18 Long integer implementation of the CalculatorValue class 
 * @version 4.1     2019-01-20 Long integer implementation of the CalculatorValue class
 * @version 4.2     2019-02-17 Implementation of the Double Calculator in CalculatorValue class
 * @version 4.3     2019-03-19 Implementation of  FSM to provide more accurate error message
 * @version 4.4     2019-04-06 Implementation of FSM on Error Term and perform all the operators with error term
 * @version 5.0     2019-09-28 Implementation of UNumber library and square root using Newton Rapson method
 * @version 5.1     2019-11-17 Implementing the units, so the calculator can calculate values with units. This calculator
 *                             is capable to calculate values of different measurement system (SI, CGS, MKS).
 * 
 */
public class CalculatorValue  {
	
	/**********************************************************************************************

	Attributes
	
	******************************
	*****************************************************************/
	
	// These are the major values that define a calculator value
	double measuredValue = 0;
	String errorMessage = "";
	double errorTerm = 0.05;
	UNumber two = new UNumber(2);
	UNumber mValue= new UNumber();
	static UNumber eValue= new UNumber();

	private static int index_op1;
	private static int index_op2;
	
	private static String unit = "";
	
	/**
	 * This calculator can calculate values of different units. 
	 * The user can input units to solve at least all the equations of the huffmann transfer 
	 * in different measurement systems such as S.I., cgs, mks etc.
	 */
	
	private String[] operand_units = { "m", "km","s", "min", "h", "day", "cm", "g", "kg","mm","mg","No Unit"};
    
	/**
     * Conversation Table
     * SI units: m, km,kg
     * CGS units: cm,m, km,mg, g, kg, s,min, h, day
     * MKS units: mm, m, km, mg, g, kg, s, min, h, day	
     */
	
	public static double converssionTable[][] = {
			//m       //Km       //sec   //min     //hours         //days                     //cm     //g     //kg    // mm   // mg  //No Unit

/*m*/		{1       ,0.001    ,1     ,1         ,1               ,1                          ,100       ,1      ,1      ,1000   ,1      ,1},
/*Km*/		{1000    ,1        ,1     ,1         ,1               ,1       				      ,100000    ,1      ,1      ,1e6    ,1      ,1},
/*sec*/		{1       ,1        ,1     ,0.0166667 ,0.00027777833333,1.157409722208333465e-5    ,1         ,1      ,1      ,1      ,1      ,1},
/*min*/     {1       ,1        ,60    ,1         ,0.0166666999998 ,06.9444583332500006527e-4  ,1         ,1      ,1      ,1      ,1      ,1},
/*hours*/   {1       ,1        ,3600  ,60        ,1               ,0.041666749999500006518    ,1         ,1      ,1      ,1      ,1      ,1},
/*days*/    {1       ,1        ,86400 ,1440      ,24              ,1                          ,1         ,1      ,1      ,1      ,1      ,1},
/* cm*/     {0.01    ,1e-5     ,1     ,1         ,1               ,1                          ,1         ,1      ,1      ,1      ,1      ,1},
/*g*/       {1       ,1        ,1     ,1         ,1               ,1                          ,1         ,1      ,0.001  ,1      ,1000   ,1 },
/*kg*/      {1       ,1        ,1     ,1         ,1               ,1                          ,1         ,1000   ,1      ,1      ,1e6    ,1},
/* mm*/     {0.001   ,1e-6     ,1     ,1         ,1               ,1                          ,0.1       ,1      ,1      ,1      ,1      ,1},
/* mg*/     {1       ,1        ,1     ,1         ,1               ,1                          ,1         ,0.001  ,1e-6   ,1      ,1      ,1},
/*No Unit*/	{1       ,1        ,1     ,1         ,1               ,1                          ,1	     ,1      ,1      ,1      ,1      ,1}};
	
	//Check the compatability of different measurement system.
	private boolean lookUpTableAddSub[][] = 
			//m     //Km     //sec   //min   //hours  //days   //cm      //g     //kg    //mm    //mg   //No Unit

/*m*/	   {{true 	,true 	,false   ,false   ,false  ,false   ,true    ,false   ,false  ,true   ,false  ,true},
/*Km*/		{true 	,true 	,false   ,false   ,false  ,false   ,true    ,false   ,false  ,true   ,false  ,true},
/*sec*/		{false	,false 	,true    ,true    ,true   ,false   ,false   ,false   ,false  ,false  ,false  ,true},
/*min*/		{false	,false 	,true    ,true    ,true   ,false   ,false   ,false   ,false  ,false  ,false  ,true},
/*hour*/	{false	,false 	,true    ,true    ,true   ,false   ,false   ,false   ,false  ,false  ,false  ,true},
/*days*/    {false	,false 	,true    ,true    ,true   ,false   ,false   ,false   ,false  ,false  ,false  ,true},
/*cm*/      {true   ,true   ,false   ,false   ,false  ,false   ,true    ,false   ,false  ,true   ,false  ,true},
/*g*/       {false  ,false  ,false   ,false   ,false  ,false   ,false   ,true    ,true   ,false  ,true   ,true},
/*kg*/      {false  ,true   ,false   ,false   ,false  ,false   ,false   ,true    ,true   ,false  ,true   ,true},
/*mm*/      {false  ,false  ,false   ,false   ,false  ,false   ,false   ,true    ,true   ,true   ,false  ,true},
/*mg*/      {false  ,false  ,false   ,false   ,false  ,false   ,false   ,true    ,true   ,false  ,true   ,true},
/*No Unit*/	{false 	,false  ,false   ,false   ,false  ,true    ,false   ,false   ,false  ,false  ,false  ,true}};
			   	
	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	/*****
	 * This is the default constructor
	 */
	public CalculatorValue() {
	}

	/*****
	 * This constructor creates a calculator value based on a double floating point. For future calculators, it
	 * is best to avoid using this constructor.
	 * @param v set measuredValue as v
	 * @param e set error term as e
	 */
	public CalculatorValue(double v, double e) {
		measuredValue = v;
		errorTerm=e;
	}

	/*****
	 * This copy constructor creates a duplicate of an already existing calculator value
	 * @param v CalculatorValue v
	 */
	public CalculatorValue(CalculatorValue v) {
		measuredValue = v.measuredValue;
		errorTerm=v.errorTerm;
		errorMessage = v.errorMessage;
	}

	/*****
	 * This constructor creates a calculator value from a string... Due to the nature
	 * of the input, there is a high probability that the input has errors, so the 
	 * routine returns the value with the error message value set to empty or the string 
	 * of an error message.
	 * @param s String for measuredValue
	 * @param e String for errorTerm
	 */
//	
	public CalculatorValue(String s, String e) {
		measuredValue = 0.00;
		
		if (s.length() == 0) {							     	// If there is nothing there,
			errorMessage = "***Error*** Input is empty";		// signal an error	
			return;												
		}
		
														
		// If the first character is a plus sign, ignore it.
		int start = 0;										// Start at character position zero
		boolean negative = false;							// Assume the value is not negative
		 
		switch(s.charAt(start)) { 
		case 1: start++;                  //Switch case is used to check sign only once as break will stops
				negative =true;           // after one check and two consecutive signs will be considered as
				break;                    //an invalid input
		 }
		
		/*****
		 * Here a scanner is created for the digits, to see if the next token is a valid or not, here numbers starting 
		 * with "+" are invalid as they don't have any influence over the number, they remain as it is 
		 *
		 */
		// See if the user-entered string can be converted into an double value
		Scanner tempScanner = new Scanner(s.substring(start));// Create scanner for the digits
		
		if (!tempScanner.hasNextDouble()) {					// See if the next token is a valid
			errorMessage = "***Error*** Invalid value"; 		
			tempScanner.close();								
			return;												
		}
		
		// Convert the user-entered string to a integer value and see if something else is following it
		measuredValue = tempScanner.nextDouble();				// Convert the value and check to see
		
		if(e.length() > 0)
			errorTerm = Double.parseDouble(e);
		
		if (tempScanner.hasNext()) {							// that there is nothing else is 
			errorMessage = "***Error*** Excess data"; 		// following the value.  If so, it
			tempScanner.close();								// is an error.  Therefore we must
			measuredValue = 0;								// return a zero value.
			return;													
		}
		
		if (s.charAt(0) == '+') {
			errorMessage = "***Error*** Invalid value"; 		
			tempScanner.close();								
			return;
			
		}
		tempScanner.close();
		
		
		errorMessage = "";
		if (negative)										// Return the proper value based
			measuredValue = -measuredValue;					// on the state of the flag that
	                                                         // on the state of the flag that
	// For Error Term
         errorTerm = 0.05;
		
		if (e.length() == 0.0) {								// If there is nothing there,
			errorMessage = "";		                            // signal an error	
			return;												
		}
		// If the first character is a plus sign, ignore it.
		int start1 = 0;										// Start1 at character position zero
									                       // Assume the value is not negative
		if (e.charAt(start1) == '+')							// See if the first character is '+'
			 start1++;										// If so, skip it and ignore it
		
		// If the first character is a minus sign, skip over it, but remember it
		else if (e.charAt(start1) == '-'){					// See if the first character is '-'
			start1++;											// if so, skip it
			negative = true;									// but do not ignore it
		}
		
		// See if the user-entered string can be converted into an integer value
		Scanner tempScanner1 = new Scanner(e.substring(start1));// Create scanner for the digits
		if (!tempScanner1.hasNextDouble()) {					// See if the next token is a valid
			errorMessage = "***Error*** Invalid value"; 		// double value.  If not, signal there
			tempScanner1.close();								// return a zero
			return;												
		}
		
		// Convert the user-entered string to a double value and see if something else is following it
		errorTerm = tempScanner1.nextDouble();	
		// Convert the value and check to see
		if (tempScanner1.hasNext()) {							// that there is nothing else is 
			errorMessage = "***Error*** Excess data"; 		 // following the value.  If so, it
			tempScanner1.close();								// is an error.  Therefore we must
			errorTerm = 0.05;	
			                                                   // return a zero value.
			return;													
		}
		tempScanner1.close();
		errorMessage = "";
		if (negative)										// Return the proper value based
			errorTerm = -errorTerm;	
	                                                        // on the state of the flag that
		
	}

	/**********************************************************************************************

	Getters and Setters
	
	**********************************************************************************************/
	

	/*****
	 * This is the start of the getters and setters
	 * 
	 * Get the error message
	 * @return return error message
	 */
	public String getErrorMessage(){
		return errorMessage;
	}
	
	/*****
	 * Set the current value of a calculator value to a specific double calculator
	 * @param v set measuredValue as double(v)
	 */
	public void setValue(double v){
		measuredValue = v;
		
	}
	
	
	/*****
	 * 
	 * Set the current value of a calculator error message to a specific string
	 * @param m It is for error message
	 * 
	 */
	public void setErrorMessage(String m){
		errorMessage = m;
	}
	
	/*****
	 * Set the current value of a calculator value to the value of another (copy)
	 * @param v CalculatorValue v
	 */
	public void setValue(CalculatorValue v){
		measuredValue = v.measuredValue;
		
		errorMessage = v.errorMessage;
	}
	
	/**********************************************************************************************

	The toString() Method
	
	**********************************************************************************************/
	
	
	public String toString() {
		return mValue.toStringDecimal();
	}
	
	
	public static String toStringErrTerm() {
		return eValue.toStringDecimal();
		
	}
	/*****
	 * This is the debug toString method
	 * @return Calculate
	 * When more complex calculator values are creating this routine will need to be updated
	 */
	public String debugToString() {
		return "measuredValue = " + measuredValue + "\nerrorMessage = " + errorMessage + "\n";
		
	}
	public String debugToStringErrTerm() {
		return "errorTerm = " + errorTerm + "\n";
	}
	
	public void setIndexofUnits(int ndx1, int ndx2) {
		index_op1 = ndx1;
		index_op2 = ndx2;
	}
	
	public boolean setUnitcheck() {	
		return lookUpTableAddSub[index_op1][index_op2];
	}
			
	public static String unitOfResult() {
		return unit;
	}
	// Method to check the units for addition and subtraction
	public void setUnitAddSub() {
		if(index_op1 < index_op2)
			unit = operand_units[index_op1];
		
		else
			unit = operand_units[index_op2];
	}
	
	/**********************************************************************************************

	The computation methods
	
	**********************************************************************************************/
	

	/*******************************************************************************************************
	 * The following methods implement computation on the calculator values.  These routines assume that the
	 * caller has verified that things are okay for the operation to take place.  These methods understand
	 * the technical details of the values and their reputations, hiding those details from the business 
	 * logic and user interface modules.
	 * @param v calculate both the measuredValue as well as error term
	 * 
	 *  Here we do support units of different measurement system.
	 */
	public void add(CalculatorValue v) {
//		double lowerb1, upperb1, lowerb2, upperb2, Term1, Term2; 
//		lowerb1 = measuredValue - errorTerm;
//		upperb1 = measuredValue + errorTerm;
//		                                                              
//		lowerb2 = v.measuredValue - v.errorTerm;
//		upperb2 = v.measuredValue + v.errorTerm;
//		
//		Term1 = upperb1 + upperb2;
//		Term2 = lowerb1 + lowerb2;
//		
//		measuredValue =(Term1+Term2)/2 ;
//		errorTerm = (Term1-Term2)/2;
//		errorTerm= Math.round(errorTerm*100.0)/100.0
		
		
		//Store double value into UNumber 
		UNumber linkingValue = new UNumber();      
		linkingValue = new UNumber(linkingValue,25);
		UNumber m1 = new UNumber(measuredValue);  
		m1 = new UNumber(m1,25);
		UNumber m2 = new UNumber(v.measuredValue);   
		m2 = new UNumber(m2,25);
		UNumber eT1 = new UNumber(errorTerm);  
		eT1 = new UNumber(eT1,25);
		UNumber eT2 = new UNumber(v.errorTerm);  
		eT2 = new UNumber(eT2,25);
		if(index_op1 > index_op2) {
			linkingValue = new UNumber(converssionTable[index_op1][index_op2]); 
			m1.mpy(linkingValue);
			eT1.mpy(linkingValue);

		}
		else {
			linkingValue = new UNumber(converssionTable[index_op2][index_op1]); 
			m2.mpy(linkingValue);
			eT2.mpy(linkingValue);
		}
		UNumber temp1 = new UNumber(m1);    
		temp1 = new UNumber(temp1,25);
		
		UNumber temp2 = new UNumber(m2);   
		temp2 = new UNumber(temp2,25);
		
		m1.sub(eT1);       // Finding the lower bound of operand 1
		temp1.add(eT1);    // Finding the upper bound of operand 1
		
		
		m2.sub(eT2);       // Finding the lower bound of operand 2
		temp2.add(eT2);    // Finding the upper bound of operand 2
		
		// Finding the range
		m1.add(m2);   
		temp1.add(temp2);       
		
		UNumber t3 = new UNumber(m1);   // Saving the lower bound 
		t3 = new UNumber(t3,25);          
		UNumber t4 = new UNumber(temp1);     // Saving the upper bound 
		t4 = new UNumber(t4,25);
		
		// Calculate measuredValue
		m1.add(temp1);  
		m1.div(two);
		
		// Calculate error term
		t4.sub(t3);	
		t4.div(two);
		
		mValue = new UNumber(m1);    // Save the result 
		eValue = new UNumber(t4);      
		errorMessage = "";
		setUnitAddSub();
	}
	
	/*****
	 *The below is the subtraction method which subtracts the value between two operand 
	 *This method has been developed by using UNumber library
	 * 
	 * @param v calculate both the measuredValue as well as error term
	 */
	
	public void sub(CalculatorValue v) {
		
//		double lowerb1, upperb1, lowerb2, upperb2, Term1, Term2; 
//		lowerb1 = measuredValue - errorTerm;
//		upperb1 = measuredValue + errorTerm;
//		                                                            
//		lowerb2 = v.measuredValue - v.errorTerm;
//		upperb2 = v.measuredValue + v.errorTerm;
//		
//		defineTerm1 = upperb1 - upperb2;
//		defineTerm2 = lowerb1 - lowerb2;
//		
//		measuredValue=measuredValue-v.measuredValue;
//		errorTerm = (Term1-Term2)/2;
//		errorTerm= Math.round(errorTerm*100.0)/100.0;
		
		//store double value in UNumber
		UNumber linkingValue = new UNumber();      
		linkingValue = new UNumber(linkingValue,25);
		UNumber m1 = new UNumber(measuredValue);      
		m1 = new UNumber(m1,25);
		
		UNumber m2 = new UNumber(v.measuredValue);    
		m2 = new UNumber(m2,25);
		
		UNumber eT1 = new UNumber(errorTerm);  
		eT1 = new UNumber(eT1,25);
		
		UNumber eT2 = new UNumber(v.errorTerm);  
		eT2 = new UNumber(eT2,25);
		
		if(index_op1 > index_op2) {
			linkingValue = new UNumber(converssionTable[index_op1][index_op2]); 
			m1.mpy(linkingValue);
			eT1.mpy(linkingValue);

		}

		else {
			linkingValue = new UNumber(converssionTable[index_op2][index_op1]); 
			m2.mpy(linkingValue);
			eT2.mpy(linkingValue);
		}
		UNumber temp1 = new UNumber(m1);    
		temp1 = new UNumber(temp1,25);
		
		UNumber temp2 = new UNumber(m2);    
		temp2 = new UNumber(temp2,25);
		
		 
		m1.sub(eT1);       // Finding the lower bound of operand 1
		temp1.add(eT1);    // Finding the upper bound of operand 1
		
		 
		m2.sub(eT2);       // Finding the lower bound of operand 2
		temp2.add(eT2);    // Finding the upper bound of operand 2
		
		
		m1.sub(m2);             // subtracting lower bounds
		temp1.sub(temp2);       // subtracting upper bounds
		
		UNumber temp3 = new UNumber(m1);   // Saving the lower bound 
		temp3 = new UNumber(temp3,25);          
		
		UNumber temp4 = new UNumber(temp1);     // Saving the upper bound 
		temp4 = new UNumber(temp4,25);
		
		//Calculate measuredValue
		m1.add(temp1); 
		m1.div(two);
		
		// Calculate error term
		temp4.sub(temp3);	
		temp4.div(two);
		
		mValue = new UNumber(m1);    // Saving the result 
		eValue = new UNumber(temp4);      
		errorMessage = "";
		setUnitAddSub();
	}
	/*****
	 *The below is the multiplication which multiplies the values between two operands.
	 * This method has been developed by using UNumber library
	 * @param v calculate both the measuredValue as well as error term
	 */
	
	
	public void mpy(CalculatorValue v) {
		      
//		double value1, value2;
//		
//		value1 = errorTerm / measuredValue;
//		value2 = v.errorTerm / v.measuredValue ;
//                                                         		
//		measuredValue *=  v.measuredValue;
//		errorTerm = (value1+value2) * measuredValue;
//		errorTerm= Math.round(errorTerm*100.0)/100.0;	
		
		UNumber linkingValue = new UNumber();      
		linkingValue = new UNumber(linkingValue,25);
		UNumber m1 = new UNumber(measuredValue);      
		m1 = new UNumber(m1,25);
		
		UNumber m2 = new UNumber(v.measuredValue);    
		m2 = new UNumber(m2,25);
		
		UNumber eT1 = new UNumber(errorTerm);  
		eT1 = new UNumber(eT1,25);
		
		UNumber eT2 = new UNumber(v.errorTerm);  
		eT2 = new UNumber(eT2,25);
		
		if(index_op1 > index_op2) {
			linkingValue = new UNumber(converssionTable[index_op1][index_op2]); 
			m1.mpy(linkingValue);
			eT1.mpy(linkingValue);

		}

		else {
			linkingValue = new UNumber(converssionTable[index_op2][index_op1]); 
			m2.mpy(linkingValue);
			eT2.mpy(linkingValue);
		}
		UNumber temp1 = new UNumber(m1);    
		temp1 = new UNumber(temp1,25);
		
		UNumber temp2 = new UNumber(m2);    
		temp2 = new UNumber(temp2,25);
		
		//Calculate measuredValue   
		// Finding the product of operand 1 and operand 2
		temp1.mpy(temp2);
		
		// Calculate error Term
		eT1.div(m1);     //divide operand 1 error term by operand 1 measuredValue and store it in eT1
		
		eT2.div(m2);     //divide operand 2 error term by operand 2 measuredValue and store it in eT2
		
		eT1.add(eT2);		  // Adding the error fractions
		
		eT1.mpy(temp1);       // Multiplying the error fraction with the product 
			
		mValue = new UNumber(temp1);    // Saving the result 
		eValue = new UNumber(eT1);      
		errorMessage = "";
		
		// Show the units at result combo box by performing multiplication operator
		if(index_op1 != index_op2)
			if(index_op1 >1 || index_op2 >1)
				unit = operand_units[index_op1] + "." +operand_units[index_op2];
			else
				if(index_op1 >  index_op2)
					unit = operand_units[index_op2] + "\u00B2";
				else 
					unit = operand_units[index_op1] + "\u00B2";
		
		else 
			unit = operand_units[index_op1] + "\u00B2";
		
		
	}
	
	/*****
	 *The below is the division which divides the value between two operands.
	 * This method has been developed by using UNumber library
	 * @param v calculate both the measuredValue as well as error term
	 */
	
	public void div(CalculatorValue v) {
		
//		double value1, value2;
//		
//		value1 = errorTerm / measuredValue;
//		value2 = v.errorTerm / v.measuredValue;
//	                                                                           	
//		measuredValue /=  v.measuredValue;
//		errorTerm = (value1+value2) * measuredValue;
//		errorTerm= Math.round(errorTerm*100.0)/100.0;	
		
		//Storing double value into UNumber
		
		
		UNumber linkingValue = new UNumber();      
		linkingValue = new UNumber(linkingValue,25);
		UNumber m1 = new UNumber(measuredValue);      
		m1 = new UNumber(m1,25);
		
		UNumber m2 = new UNumber(v.measuredValue);    
		m2 = new UNumber(m2,25);
		
		UNumber eT1 = new UNumber(errorTerm);  
		
		UNumber eT2 = new UNumber(v.errorTerm);  
		eT2 = new UNumber(eT2,25);
		if(index_op1 > index_op2) {
			linkingValue = new UNumber(converssionTable[index_op1][index_op2]); 
			m1.mpy(linkingValue);
			eT1.mpy(linkingValue);

		}

		else {
			linkingValue = new UNumber(converssionTable[index_op2][index_op1]); 
			m2.mpy(linkingValue);
			eT2.mpy(linkingValue);
		}
		
		UNumber temp1 = new UNumber(m1);    
		temp1 = new UNumber(temp1,25);
		
		UNumber temp2 = new UNumber(m2);    
		temp2 = new UNumber(temp2,25);
		
		temp1.div(temp2);     // Find Quotient of operand 1 and operand 2
		
		eT1.div(m1);     //divide operand 1 error term by operand 1 measuredValue and store it in eT1
		
		eT2.div(m2);     //divide operand 2 error term by operand 2 measuredValue and store it in eT2
		
		eT1.add(eT2);		  // Adding the error fractions
		
		eT1.mpy(temp1);       // Multiplying the error fraction with the product 
			
		mValue = new UNumber(temp1);    // Saving the product 
		eValue = new UNumber(eT1);      
		errorMessage = "";
		
		// Show the units at result combo box by performing multiplication operator
		if(index_op1 != index_op2)
		if(index_op1 >1 || index_op2 >1)
			if(index_op1 == 6)
				unit = operand_units[index_op2] + "\u207B" + "\u00B9";
			else if(index_op2 == 6)
				unit =  operand_units[index_op1];
			else
				unit = operand_units[index_op1] +"/" + operand_units[index_op2];
			
		else
			if(index_op1 >  index_op2)
				unit = "No unit";
			else 
				unit = "No unit";
	
	else 
		unit = "No unit";
		}

   
     public void sqrt(CalculatorValue v) {
    	 UNumber h=new UNumber(0.5);
    	 UNumber mValue1 = new UNumber(measuredValue); // Compute the the first estimate
    	 mValue1 = new UNumber(mValue1, 25);
    	 mValue1=mValue1.sqrt();  //call square root method for measuredValue
    	 
    	UNumber measuredValue1 = new UNumber(mValue1);
    	 mValue=new UNumber(measuredValue1);
    	 
    	 UNumber mValue2 = new UNumber(measuredValue); 
    	 mValue2 = new UNumber(mValue2, 25); 
    	 UNumber eT = new UNumber(errorTerm);
    	 eT = new UNumber(eT,25);
    	 
    	 
    	 eT.div(mValue2);
    	 eT.mpy(mValue);
    	 eT.mpy(h);
    	 
    	
    	 mValue = new UNumber(mValue1);     // Saving the result
    	 eValue = new UNumber(eT);   
    	 errorMessage = "";
    	 unit = "\u221A" + operand_units[index_op1];
     }
     


/***
 * I have taken the baseline code from W8D3 study hall (FSM baseline-code)
 * 
 */

public static String measuredValueErrorMessage = "";	// The alternate error message text
public static String measuredValueInput = "";		// The input being processed
public static int measuredValueIndexofError = -1;		// The index where the error was located
private static int state = 0;						// The current state value
private static int nextState = 0;					// The next state value
static boolean finalState = false;			// Is this state a final state
private static String inputLine = "";				// The input line
private static char currentChar;						// The current character in the line
private static int currentCharNdx;					// The index of the current character
private static boolean running;	
private static String displayInput(String input, int currentCharNdx) {
	// Display the entire input line
	String result = input + "\n";

	// Display a line with enough spaces so the up arrow point to the point of an error
	for (int ndx=0; ndx < currentCharNdx; ndx++) result += " ";

	// Add the up arrow to the end of the second line
	return result + "\u21EB";				// A Unicode up arrow with a base
}

private static void moveToNextCharacter() {
	currentCharNdx++;
	if (currentCharNdx < inputLine.length())
		currentChar = inputLine.charAt(currentCharNdx);
	else {
		currentChar = ' ';
		running = false;
	}
}

/**********
 * This method is a mechanical transformation of a Finite State Machine diagram into a Java
 * method.
 * 
 * @param input		The input string for the Finite State Machine
 * @return			An output string that is empty if every things is okay or it will be
 * 						a string with a help description of the error follow by two lines
 * 						that shows the input line follow by a line with an up arrow at the
 *						point where the error was found.
 */
public static String checkMeasureValue(String input) {
	if(input.length() <= 0) return "";
	// The following are the local variable used to perform the Finite State Machine simulation
	state = 0;							// This is the FSM state number
	inputLine = input;					// Save the reference to the input line as a global
	currentCharNdx = 0;					// The index of the current character
	currentChar = input.charAt(0);		// The current character from the above indexed position

	// The Finite State Machines continues until the end of the input is reached or at some 
	// state the current character does not match any valid transition to a next state

	measuredValueInput = input;			// Set up the alternate result copy of the input
	running = true;						// Start the loop
	

	// The Finite State Machines continues until the end of the input is reached or at some 
	// state the current character does not match any valid transition to a next state
	while (running) {
		// The switch statement takes the execution to the code for the current state, where
		// that code sees whether or not the current character is valid to transition to a
		// next state
		switch (state) {
		case 0: 
			// State 0 has two valid transitions.  Each is addressed by an if statement.
			
			// This is not a final state
			setFinalState(false);
			
			// If the current character is in the range from 1 to 9, it transitions to state 1
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 1;
				break;
			}
			// If the current character is a decimal point, it transitions to state 3
			else if (currentChar == '.') {
				nextState = 3;
				break;					
			}
			
			// If it is none of those characters, the FSM halts
			else 
				running = false;
			
			// The execution of this state is finished
			break;
		
		case 1: 
			// State 1 has three valid transitions.  Each is addressed by an if statement.
			
			// This is a final state
			setFinalState(true);
			
			// In state 1, if the character is 0 through 9, it is accepted and we stay in this
			// state
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 1;
				break;
			}
			
			// If the current character is a decimal point, it transitions to state 2
			else if (currentChar == '.') {
				nextState = 2;
				break;
			}
			// If the current character is an E or an e, it transitions to state 5
			else if (currentChar == 'E' || currentChar == 'e') {
				nextState = 5;
				break;
			}
			// If it is none of those characters, the FSM halts
			else
				running = false;
			
			// The execution of this state is finished
			break;			
			
		case 2: 
			// State 2 has two valid transitions.  Each is addressed by an if statement.
			
			// This is a final state
			setFinalState(true);
			
			// If the current character is in the range from 1 to 9, it transitions to state 1
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 2;
				break;
			}
			// If the current character is an 'E' or 'e", it transitions to state 5
			else if (currentChar == 'E' || currentChar == 'e') {
				nextState = 5;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running = false;

			// The execution of this state is finished
			break;

		case 3:
			// State 3 has only one valid transition.  It is addressed by an if statement.
			
			// This is not a final state
			setFinalState(false);
			
			// If the current character is in the range from 0 to 9, it transitions to state 1
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 4;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running = false;

			// The execution of this state is finished
			break;

		case 4: 
			// State 4 has two valid transitions.  Each is addressed by an if statement.
			
			// This is a final state
			setFinalState(true);
			
			// If the current character is in the range from 0 to 9, it transitions to state 4
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 4;
				break;
			}
			// If the current character is an 'E' or 'e", it transitions to state 5
			else if (currentChar == 'E' || currentChar == 'e') {
				nextState = 5;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running = false;

			// The execution of this state is finished
			break;

		case 5: 
         // State 5 has two valid transitions.  Each is addressed by an if statement.
			
			// This is not a final state
			setFinalState(false);
			
			// If the current character is in the range from 0 to 9, it transitions to state 7
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 7;
				break;
			}
			// If the current character is an '+' or '-", it transitions to state 6
			else if (currentChar == '+' || currentChar == '-') {
				nextState = 6;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running = false;
			
			// The execution of this state is finished
			break;

		case 6: 
          // State 6 has one valid transitions.  It is addressed by an if statement.
			
			// This is not a final state
			setFinalState(false);
			
			// If the current character is in the range from 0 to 9, it transitions to state 7
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 7;
				break;
			}
			
			

			// If it is none of those characters, the FSM halts
			else 
				running = false;
			
			// The execution of this state is finished
			break;

		case 7: 
          // State 7 has one valid transitions.  It is addressed by an if statement.
			
			// This is a final state
			setFinalState(true);
			
			// If the current character is in the range from 0 to 9, it transitions to state 7
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 7;
				break;
			}
			

			// If it is none of those characters, the FSM halts
			else 
				running = false;
			

				// The execution of this state is finished
				break;
		}
		
		if (running) {
			
			// When the processing of a state has finished, the FSM proceeds to the next character
			// in the input and if there is one, it fetches that character and updates the 
			// currentChar.  If there is no next character the currentChar is set to a blank.
			moveToNextCharacter();
			
			// Move to the next state
			state = nextState;

		}
		// Should the FSM get here, the loop starts again

	}

	

	measuredValueIndexofError = currentCharNdx;		// Copy the index of the current character;
	
	// When the FSM halts, we must determine if the situation is an error or not.  That depends
	// of the current state of the FSM and whether or not the whole string has been consumed.
	// This switch directs the execution to separate code for each of the FSM states.
	switch (state) {
	case 0:
		// State 0 is not a final state, so we can return a very specific error message
		measuredValueIndexofError = currentCharNdx;		// Copy the index of the current character;
		measuredValueErrorMessage = "The first character must be a digit or a decimal point.";
		return "The first character must be a digit or a decimal point.";

	case 1:
		// State 1 is a final state, so we must see if the whole string has been consumed.
		if (currentCharNdx<input.length()) {
			// If not all of the string has been consumed, we point to the current character
			// in the input line and specify what that character must be in order to move
			// forward.
			measuredValueErrorMessage = "This character may only be an \"E\", an \"e\", a digit, "
					+ "a \".\", or it must be the end of the input.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdx);
		}
		else {
			measuredValueIndexofError = -1;
			measuredValueErrorMessage = "";
			return measuredValueErrorMessage;
		}

	case 2:
	case 4:
		// States 2 and 4 are the same.  They are both final states with only one possible
		// transition forward, if the next character is an E or an e.
		if (currentCharNdx<input.length()) {
			measuredValueErrorMessage = "This character may only be an \"E\", an \"e\", or it must"
					+ " be the end of the input.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdx);
		}
		// If there is no more input, the input was recognized.
		else {
			measuredValueIndexofError = -1;
			measuredValueErrorMessage = "";
			return measuredValueErrorMessage;
		}
	case 3:
	case 6:
		// States 3, and 6 are the same. None of them are final states and in order to
		// move forward, the next character must be a digit.
		measuredValueErrorMessage = "This character may only be a digit.\n";
		return measuredValueErrorMessage + displayInput(input, currentCharNdx);

	case 7:
		// States 7 is similar to states 3 and 6, but it is a final state, so it must be
		// processed differently. If the next character is not a digit, the FSM stops with an
		// error.  We must see here if there are no more characters. If there are no more
		// characters, we accept the input, otherwise we return an error
		if (currentCharNdx<input.length()) {
			measuredValueErrorMessage = "This character may only be a digit.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdx);
		}
		else {
			measuredValueIndexofError = -1;
			measuredValueErrorMessage = "";
			return measuredValueErrorMessage;
		}

	case 5:
		// State 5 is not a final state.  In order to move forward, the next character must be
		// a digit or a plus or a minus character.
		measuredValueErrorMessage = "This character may only be a digit, a plus, or minus "
				+ "character.\n";
		return measuredValueErrorMessage + displayInput(input, currentCharNdx);
	default:
		return "";
	}
}

public static boolean isFinalState() {
	return finalState;
}

public static void setFinalState(boolean finalState) {
	CalculatorValue.finalState = finalState;
}
}



