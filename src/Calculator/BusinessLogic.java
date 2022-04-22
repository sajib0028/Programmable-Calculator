
package Calculator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * <p> Title: BusinessLogic Class. </p>
 * 
 * <p> Description: The code responsible for performing the calculator business logic functions. 
 * This method deals with CalculatorValues and performs actions on them.  The class expects data
 * from the User Interface to arrive as Strings and returns Strings to it.  This class calls the
 * CalculatorValue class to do computations and this class knows nothing about the actual 
 * representation of CalculatorValues, that is the responsibility of the CalculatorValue class and
 * the classes it calls.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2017 </p>
 * 
 * @author Lynn Robert Carter
 * @author Sajib Biswas 
 
 * @version 4.00	2014-10-18 The JavaFX-based GUI implementation of a long integer calculator 
 * @version 4.1  	2019-01-19 The JavaFX-based GUI implementation of a long integer calculator
 * @version 4.2  	2019-02-13 Implementation of a double calculator with log base 2
 * @version 4.4     2019-04-06 Implementation of FSM on Error Term and perform all the operators with error term
 * @version 5.0     2019-09-28 Implementation of UNumber library and square root using Newton Rapson method
 * @version 5.1     2019-11-17 Implementing the units, so the calculator can calculate values with units. This calculator
 *                             is capable to calculate values of different measurement system (SI, CGS, MKS).
 * 
 * 
 * 
 */

public class BusinessLogic {
	public static boolean errorzero=false;
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the major calculator values 
	private CalculatorValue operand1 = new CalculatorValue(0,0);
	
	private CalculatorValue operand2 = new CalculatorValue(0,0);
	private CalculatorValue result = new CalculatorValue(0,0);
	private String operand1ErrorMessage = "";
	private boolean operand1Defined = false;
	private String operand2ErrorMessage = "";
	private boolean operand2Defined = false;
	private String resultErrorMessage = "";

	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/
	
	/**********
	 * This method initializes all of the elements of the business logic aspect of the calculator.
	 * There is no special computational initialization required, so the initialization of the
	 * attributes above are all that are needed.
	 */
	public BusinessLogic() {
	}

	/**********************************************************************************************

	Getters and Setters
	
	**********************************************************************************************/
	
	/**********
	 * This public method takes two input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand1, any associated error message
	 * into operand1ErrorMessage, and sets flags accordingly.
	 * 
	 * @param value  it is for measure value of operand 1
	 * @param value1 it is for error term  of operand 2
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setOperand1(String value, String value1) {
		operand1Defined = false;							// Assume the operand will not be defined
		if (value.length() <= 0) {						// See if the input is empty. If so no error
			operand1ErrorMessage = "";					// message, but the operand is not defined.
			return true;									// Return saying there was no error.
		}
		operand1 = new CalculatorValue(value, value1);			// If there was input text, try to convert it
		operand1ErrorMessage = operand1.getErrorMessage();	// into a CalculatorValue and see if it
		if (operand1ErrorMessage.length() > 0) 			// worked. If there is a non-empty error 
			return false;								// message, signal there was a problem.
		operand1Defined = true;							// Otherwise, set the defined flag and
		return true;										// signal that the set worked
	}
	

	
	/**********
	 * This public method takes two input Strings, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand2, any associated error message
	 * into operand1ErrorMessage, and sets flags accordingly.
	 * 
	 * The logic of this method is the same as that for operand1 above.
	 * 
	 * @param value  it is for measure value of operand 2
	 * @param value1 it is for error term of operand 2
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setOperand2(String value,String value1) {			// The logic of this method is exactly the
		operand2Defined = false;
		if(value.length()<=0) {                      // same as that for operand1, above.
		
			operand2ErrorMessage = "";
			return true;
		}
		operand2 = new CalculatorValue(value, value1);
		operand2ErrorMessage = operand2.getErrorMessage();
		if (operand2ErrorMessage.length() > 0)
			return false;
		operand2Defined = true;
		return true;
	}

	
	/**********
	 * This public method takes two input Strings, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into result, any associated error message
	 * into resuyltErrorMessage, and sets flags accordingly.
	 * 
	 * The logic of this method is similar to that for operand1 above. (There is no defined flag.)
	 * 
	 * @param value  it is for measure value for result
	 * @param value1 it is for error term  for result_error term
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setResult(String value, String value1) {				// The logic of this method is similar to
		if (value.length() <= 0) {						// that for operand1, above.
			operand2ErrorMessage = "";
			return true;
		}
		result = new CalculatorValue(value, value1);
		resultErrorMessage = operand2.getErrorMessage();
		if (operand2ErrorMessage.length() > 0)
			return false;
		return true;
	}
	
	/**********
	 * This public setter sets the String explaining the current error in operand1.
	 *  
	 * @param m It take string as error message
	 * 
	 */
	public void setOperand1ErrorMessage(String m) {
		operand1ErrorMessage = m;
		return;
	}
	
	
	/**********
	 * This public getter fetches the String explaining the current error in operand1, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand1ErrorMessage() {
		return operand1ErrorMessage;
	}
	
	
	/**********
	 * This public setter sets the String explaining the current error into operand1.
	 * @param m It takes string as error message
	 *
	 * 
	 */
	public void setOperand2ErrorMessage(String m) {
		operand2ErrorMessage = m;
		return;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in operand2, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand2ErrorMessage() {
		return operand2ErrorMessage;
	}
	
	/**********
	 * This public setter sets the String explaining the current error in the result.
	 * 
	 *@param m result error message
	 *
	 */
	public void setResultErrorMessage(String m) {
		resultErrorMessage = m;
		return;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in the result, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getResultErrorMessage() {
		return resultErrorMessage;
	}
	
	/**********
	 * This public getter fetches the defined attribute for operand1. You can't use the lack of an error 
	 * message to know that the operand is ready to be used. An empty operand has no error associated with 
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 * 
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand1Defined() {
		return operand1Defined;
	}
	
	
	/**********
	 * This public getter fetches the defined attribute for operand2. You can't use the lack of an error 
	 * message to know that the operand is ready to be used. An empty operand has no error associated with 
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 * 
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand2Defined() {
		return operand2Defined;
	}

	/**********************************************************************************************

	The toString() Method
	
	**********************************************************************************************/
	
	/**********
	 * This toString method invokes the toString method of the result type (CalculatorValue is this 
	 * case) to convert the value from its hidden internal representation into a String, which can be
	 * manipulated directly by the BusinessLogic and the UserInterface classes.
	 */
	public String toString() {
		return result.toString();
	}
	
	/**********
	 * This public toString method is used to display all the values of the BusinessLogic class in a
	 * textual representation for debugging purposes.
	 * 
	 * @return a String representation of the class
	 */
	public String debugToString() {
		String r = "\n******************\n*\n* Business Logic\n*\n******************\n";
		r += "operand1 = " + operand1.toString() + "\n";
		r += "     operand1ErrorMessage = " + operand1ErrorMessage+ "\n";
		r += "     operand1Defined = " + operand1Defined+ "\n";
		r += "operand2 = " + operand2.toString() + "\n";
		r += "     operand2ErrorMessage = " + operand2ErrorMessage+ "\n";
		r += "     operand2Defined = " + operand2Defined+ "\n";
		r += "result = " + result.toString() + "\n";
		r += "     resultErrorMessage = " + resultErrorMessage+ "\n";
		r += "*******************\n\n";
		return r;
	}
	
	/**********************************************************************************************

	Business Logic Operations (e.g. addition)
	
	**********************************************************************************************/
	
	/**********
	 * This public method computes the sum of the two operands using the CalculatorValue class method 
	 * for addition. The goal of this class is to support a wide array of different data representations 
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left operand with the 
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 * 
	 * @return Calculate the result with 25 precision 
	 */
	public String addition() {
		result = new CalculatorValue(operand1);		  //This is the business logic for addition
		result.add(operand2);		
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}
	
	/**********
	 * The following methods is for subtraction.
	 * @return Calculate result up to 25 significant digits 
	 */
	public String subtraction() {
		result = new CalculatorValue(operand1);       //This is the business logic for subtraction
		result.sub(operand2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();	
	}

	/**********
	 * The following methods is for multiplication
	 * @return Calculate result up to 25 significant digits 
	 */
	public String multiplication() {
		result = new CalculatorValue(operand1);          //This is the business logic for Multiplication
		result.mpy(operand2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();	
	}

	/**********
	 * The following methods is for division. 
	 * @return Calculate result up to 25 significant digits 
	 */
	public String division() {
		result = new CalculatorValue(operand1);        //This is the business logic for Division. 
		                                               //If the second operand is Zero,
		if(operand2.measuredValue==0) {                //it will show an error message 
			errorzero=true;
		}
		else {
				
		result.div(operand2);
	}
		 resultErrorMessage = result.getErrorMessage();
		 return result.toString();	
	}

	/**********
	 * The following methods is for square root.
	 * @return Calculate result up to 25 significant digits 
	 */
	public String sqrt() {
		result = new CalculatorValue(operand1);               //This is the business logic for Square Root. 
		result.sqrt(operand1);                               // it will take the first operand only.
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}
	/***
	 * Following method is to get the error term result
	 * @return string formated output
	 */
	public String errorTerm() {
		String str = CalculatorValue.toStringErrTerm();
		return str;
	}
	
	/***
	 * Following method is to get the error term result
	 * @return string formated output
	 */
	public String Unit() {
		String unit= CalculatorValue.unitOfResult();
		return unit;
	}
	/***
	 * Following method is to check the compatibility of units of addition and subtraction
	 * @return check the compatibility
	 */
	public boolean UnitsCheck() {	
		boolean check = result.setUnitcheck();
			if(!check) {	
		Alert notify = new Alert(AlertType.ERROR);
		notify.setTitle("Normalization Error");
		notify.setHeaderText("Units not compatilbe");
		notify.setContentText("For addition or subtraction the units must belong to same physical measure");
		notify.showAndWait();
		
			}
	
		return result.setUnitcheck();	
		}
	//FSM logic for Error term
	
	public static String errorTermErrorMessage = "";
	public static String errorTermInput = "";			// The input being processed
	public static int errorTermIndexofError = -1;		// The index where the error was located
	private static int state = 0;						// The current state value
	private static int nextState = 0;					// The next state value
	private static boolean finalState = false;			// Is this state a final state
	private static String inputLine = "";				// The input line
	private static char currentChar;						// The current character in the line
	private static int currentCharNdx;					// The index of the current character
	private static boolean running;						// The flag that specifies if it is running

	/**********
	 * This private method display the input line and then on a line under it displays an up arrow
	 * at the point where an error was detected.  This method is designed to be used to display the
	 * error message on the console terminal.
	 * 
	 * @param input				The input string
	 * @param currentCharNdx		The location where an error was found
	 * @return					Two lines, the entire input line followed by a line with an up arrow
	 */
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
	public static String checkErrorTerm(String input) {
		if(input.length() <= 0) return "";
		// The following are the local variable used to perform the Finite State Machine simulation
		state = 0;							// This is the FSM state number
		inputLine = input;					// Save the reference to the input line as a global
		currentCharNdx = 0;					// The index of the current character
		currentChar = input.charAt(0);		// The current character from the above indexed position

		// The Finite State Machines continues until the end of the input is reached or at some 
		// state the current character does not match any valid transition to a next state

		errorTermInput = input;			// Set up the alternate result copy of the input
		running = true;						// Start the loop
		

		// The Finite State Machines continues until the end of the input is reached or at some 
		// state the current character does not match any valid transition to a next state
		while (running) {
			// The switch statement takes the execution to the code for the current state, where
			// that code sees whether or not the current character is valid to transition to a
			// next state
			switch (state) {
			case 0: 
				// State 0 has three valid transitions.  Each is addressed by an if statement.
				
				// This is not a final state
				setFinalState(false);
				
				// If the current character is in the range from 1 to 9, it transitions to state 1
				if (currentChar >= '1' && currentChar <= '9') {
					nextState = 1;
					break;
				}
				// If the current character is a decimal point, it transitions to state 3
				else if (currentChar == '.') {
					nextState = 3;
					break;					
				}
				// If the current character is '0', it transitions to state 8
				else if (currentChar == '0') {
					nextState = 8;
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
				
				// In state 1, if the character is 0, it is accepted and we stay in this
				// state
				if (currentChar == '0') {
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
				// State 2 has one valid transitions.  Each is addressed by an if statement.
				
				// This is a final state
				setFinalState(true);
				
				
				
				// If the current character is an 'E' or 'e", it transitions to state 5
				 if (currentChar == 'E' || currentChar == 'e') {
					nextState = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else 
					running = false;

				// The execution of this state is finished
				break;
	
			case 3:
				// State 3 has two valid transition.  It is addressed by an if statement.
				
				// This is not a final state
				setFinalState(false);
				
				// If the current character is in the range from 1 to 9, it transitions to state 4
				if (currentChar >= '1' && currentChar <= '9') {
					nextState = 4;
					break;
				}
				// If the current character is 0, it is accepted and we stay in this state
				else if (currentChar == '0') {
					nextState = 3;
					break;
				}
				// If it is none of those characters, the FSM halts
				else 
					running = false;

				// The execution of this state is finished
				break;

			case 4: 
				// State 4 has only one valid transition.  It is addressed by an if statement.
				
				// This is a final state
				setFinalState(true);
				
				
				
				// If the current character is an 'E' or 'e", it transitions to state 5
				 if (currentChar == 'E' || currentChar == 'e') {
					nextState = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else 
					running = false;

				// The execution of this state is finished
				break;
				
		
		

			case 5: 
                 // State 5 has two valid transition.  Each is addressed by an if statement.
				
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
                 // State 6 has only one valid transition.  It is addressed by an if statement.
				
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
             // State 7 has only one valid transition.  It is addressed by an if statement.
				
				// This is not a final state
				setFinalState(true);
				
				// If the current character is in the range from 0 to 9
				//It is accepted and we can stay in this state
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 7;
					break;
				}
				
				// If it is none of those characters, the FSM halts
				else 
					running = false;

				// The execution of this state is finished
				break;
				
			case 8: 
	             // State 8 has only one valid transition.  It is addressed by an if statement.
					
					// This is not a final state
					setFinalState(false);
					
					// If the current character '.', it transitions to state 9
					if (currentChar == '.' ) {
						nextState = 9;
						break;
					}
					
					// If it is none of those characters, the FSM halts
					else 
						running = false;

					// The execution of this state is finished
					break;
					
			case 9: 
	             // State 9 has two valid transitions.  Each is addressed by an if statement.
					
					// This is not a final state
					setFinalState(false);
					
					// If the current character is in the range from 1 to 9, it transitions to state 4
					if (currentChar >= '1' && currentChar <= '9') {
						nextState = 4;
						break;
					}
					//If the current character is '0', it is accepted and we can stay in this state
					else if (currentChar == '0') {
						nextState = 9;
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

		

		errorTermIndexofError = currentCharNdx;		// Copy the index of the current character;
		
		// When the FSM halts, we must determine if the situation is an error or not.  That depends
		// of the current state of the FSM and whether or not the whole string has been consumed.
		// This switch directs the execution to separate code for each of the FSM states.
		switch (state) {
		case 0:
			// State 0 is not a final state, so we can return a very specific error message
			errorTermIndexofError = currentCharNdx;		// Copy the index of the current character;
			errorTermErrorMessage = "The first character must be a digit or a decimal point.";
			return "The first character must be a digit or a decimal point.";

		case 1:
			// State 1 is a final state, so we must see if the whole string has been consumed.
			if (currentCharNdx<input.length()) {
				// If not all of the string has been consumed, we point to the current character
				// in the input line and specify what that character must be in order to move
				// forward.
				errorTermErrorMessage = "This character may only be an \"E\", an \"e\", a digit, "
						+ "a \".\", or it must be the end of the input.\n";
				return errorTermErrorMessage + displayInput(input, currentCharNdx);
			}
			else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}

		case 2:
		case 4:
			// States 2 and 4 are the same.  They are both final states with only one possible
			// transition forward, if the next character is an E or an e.
			if (currentCharNdx<input.length()) {
				errorTermErrorMessage = "This character may only be an \"E\", an \"e\", or it must"
						+ " be the end of the input.\n";
				return errorTermErrorMessage + displayInput(input, currentCharNdx);
			}
			// If there is no more input, the input was recognized.
			else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}
		case 3:
			// States 3 is not a final states and in order to
			// move forward, the next character must be a digit.
			errorTermErrorMessage = "This character may only be a digit.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdx);
		case 6:
			// States  is not a final states and in order to 
			// move forward, the next character must be a digit.
			errorTermErrorMessage = "This character may only be a digit.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdx);
		case 8:
			// States 8is not a final states and in order to
			// move forward, the next character must be a decimal point.
			errorTermErrorMessage = "This character may only be a decimal point.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdx);
		case 9:
			// States 9 is not a final states and in order to
			// move forward, the next character must be a digit.
			errorTermErrorMessage = "This character may only be a digit.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdx);
		
		case 7:
			// States 7 is similar to state 6, but it is a final state, so it must be
			// processed differently. If the next character is not a digit, the FSM stops with an
			// error.  We must see here if there are no more characters. If there are no more
			// characters, we accept the input, otherwise we return an error
			if (currentCharNdx<input.length()) {
				errorTermErrorMessage = "This character may only be a digit.\n";
				return errorTermErrorMessage + displayInput(input, currentCharNdx);
			}
			else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}

		case 5:
			// State 5 is not a final state.  In order to move forward, the next character must be
			// a digit or a plus or a minus character.
			errorTermErrorMessage = "This character may only be a digit, a plus, or minus "
					+ "character.\n";
			return errorTermErrorMessage + displayInput(input, currentCharNdx);
		default:
			return "";
		}
	}

	public static boolean isFinalState() {
		return finalState;
	}

	public static void setFinalState(boolean finalState) {
		BusinessLogic.finalState = finalState;
	}

	
	
	}
