package com.ora.jsp.util;

/**
 * This class contains a couple of static methods for converting 
 * between Fahrenheit and Celsius. The methods are mapped to
 * el functions in the book examples TLD file.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class TempConverter {
    /**
     * Main method to test the other methods.
     */
    public static void main (String[] args) throws Exception {
        System.out.println("20 C is " + toFahrenheit(20) + " F");
        System.out.println("68 F is " + toCelsius(68) + " C");
    }
    
    public static double toCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static double toFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }
}
