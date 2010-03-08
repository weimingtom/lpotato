package com.ora.jsp.beans.calc;

import java.io.*;
import java.math.*;

/**
 * This class implements a simple calculator for a JSP page. 
 * It's a stateless bean, so all state must be carried by hidden 
 * fields in the JSP page, and set before the first getter
 * method is called. In order to set all properties with the
 * &lt;jsp:setProperty name="foo" property="*" /&gt; syntax (i.e. no
 * control over the order of setter calls), all processing to figure
 * out the new values of all properties is performed in the 
 * getCurrentNumber() method.
 * <p>
 * Basically, the bean accumulates digits and the decimal dot
 * in currentNumber until an operation is requested. The accumulated
 * number is then saved as previousNumber, and the next number
 * starts to accumulate in currentNumber. When an operation is
 * requested the second time, it's carried out based on the
 * previousNumber and currentNumber, and the result becomes the
 * new previousNumber.
 * <p>
 * Note! This bean is just used as an example of how to use a JSP 
 * error page to handle exceptions. Even though it appear to work 
 * as a typical calculator, it's not been heavily tested so I make
 * no guarantees that it handles error cases gracefully. Besides,
 * I'm not so sure a web based calculator is such a good idea. An
 * applet would probably be a better choice.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class CalcBean implements Serializable {
    private static final int NO_OPER = -1;
    private static final int ADD_OPER = 1;
    private static final int SUB_OPER = 2;
    private static final int DIV_OPER = 3;
    private static final int MULT_OPER = 4;
    
    private String newDigit = "";
    private String currentNumber = "";
    private String previousNumber = "";
    private int newOper = NO_OPER;
    private int currentOperation = NO_OPER;
    private boolean reset = false;
    private boolean operClicked = false;
    private boolean clearClicked = false;
    private boolean dotClicked = false;

    /**
     * Sets the new digit entered by the user.
     */
    public void setDigit(String value) {
        newDigit = value.trim();
    }

    /**
     * Sets the decimal separator entered by the user.
     */
    public void setDot(String value) {
        dotClicked = true;
    }
    
    /**
     * Sets the operation (+, -, *, / or =) entered by the user.
     */
    public void setOper(String value) {
        newOper = toOperValue(value.trim());
        operClicked = true;
    }
    
    /**
     * Sets the "cleared" flag, indicating that the
     * user clicked Clear.
     */
    public void setClear(String value) {
        clearClicked = true;
    }

    /**
     * Sets the current number, meaning the number as it
     * looks before applying the latest digit, dot, etc.
     */
    public void setCurrentNumber(String value) {
        currentNumber = value;
    }

    /**
     * Returns the current number resulting from applying the
     * current user input (new digit, dot or operation).
     */
    public String getCurrentNumber() {
        calcNewNumbers();
        return currentNumber + newDigit;
    }
    
    /**
     * Sets the current operation, meaning the operation that
     * will be applied the next time an operation is requested.
     */
    public void setCurrentOperation(String value) {
        currentOperation = toOperValue(value);
    }
    
    /**
     * Returns the current operation.
     */
    public String getCurrentOperation() {
        return toOperName(currentOperation);
    }

    /**
     * Sets the previous number, meaning the number accumulated
     * until the an operation was requested.
     */
    public void setPreviousNumber(String value) {
        previousNumber = value;
    }
    
    /**
     * Returns the previous number.
     */
    public String getPreviousNumber() {
        return previousNumber;
    }

    /**
     * Sets the "reset flag" to clear the current number on
     * the next submit (for instance, after performing an operation).
     */
    public void setReset(boolean value) {
        reset =  value;
    }
    
    /**
     * Returns the "reset flag".
     */
    public boolean getReset() {
        return reset;
    }
    
    /**
     * Processes the current input, meaning adding digits, performing
     * the requested operation, and saving the accumulated
     * the number, as described in the class comment.
     */
    private void calcNewNumbers() {
        if (dotClicked) {
            if (currentNumber.length() == 0) {
                currentNumber = "0.";
            }
            else if (currentNumber.indexOf(".") == -1) {
                currentNumber += ".";
            }
            dotClicked = false;
            reset = false;
        }
        else if (operClicked) {
            if (previousNumber.length() > 0 && currentNumber.length() > 0) {
                BigDecimal firstNumber = null;
                if (previousNumber.indexOf(".") == -1) {
                    // Make it decimal to get real division
                    firstNumber = new BigDecimal(previousNumber + ".0");
                }
                else {
                    firstNumber = new BigDecimal(previousNumber);
                }
                BigDecimal secondNumber = new BigDecimal(currentNumber);
                BigDecimal result = null;
                switch (currentOperation) {
                    case ADD_OPER:
                        result = firstNumber.add(secondNumber);
                        break;
                    case SUB_OPER:
                        result = firstNumber.subtract(secondNumber);
                        break;
                    case DIV_OPER:
                        result = firstNumber.divide(secondNumber, 
                            BigDecimal.ROUND_HALF_UP);
                        break;
                    case MULT_OPER:
                        result = firstNumber.multiply(secondNumber);
                        break;
                }
                if (result != null) {
                    currentNumber = result.toString();
                }
            }
            previousNumber = currentNumber;
            currentOperation = newOper;
            reset = true;
        }
        else if (clearClicked) {
            currentNumber = "";
            previousNumber = currentNumber;
            currentOperation = NO_OPER;
        }
        else if (reset) {
            currentNumber = "";
            reset = false;
        }
    }
    
    /**
     * Converts an operation int value to the corresponding
     * String representation.
     */
    private String toOperName(int value) {
        String name = "";
        switch (value) {
            case ADD_OPER:
                name = "+";
                break;
            case SUB_OPER:
                name = "-";
                break;
            case DIV_OPER:
                name = "/";
                break;
            case MULT_OPER:
                name = "*";
        }
        return name;
    }
    
    /**
     * Converts an operation String value to the corresponding
     * int representation.
     */
    private int toOperValue(String name) {
        int value = NO_OPER;
        if ("+".equals(name)) {
            value = ADD_OPER;
        }
        else if ("-".equals(name)) {
            value = SUB_OPER;
        }
        else if ("/".equals(name)) {
            value = DIV_OPER;
        }
        else if ("*".equals(name)) {
            value = MULT_OPER;
        }
        return value;
    }
}
