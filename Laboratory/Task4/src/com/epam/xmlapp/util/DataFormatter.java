/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.epam.xmlapp.model.Color;

/**
 * Class contains static methods date format transformation.
 * @author Andrei_Ilyin1
 */
public final class DataFormatter {
    /**
     * Constructs a new DataFormatter object with the default parameters.
     */
    private DataFormatter() {}
    
    /**
     * Converts the String value to the java.util.Date object according to the
     * date pattern.
     * 
     * @param strDate - the String date value
     * @param datePattern - the date pattern
     * 
     * @return the java.util.Date object
     * 
     * @throws ParseException if ParseException occurs
     */
    public static java.util.Date parseToDate(String strDate, String datePattern) 
	    throws ParseException {
	DateFormat dateFormat = new SimpleDateFormat(datePattern);
	Date date = dateFormat.parse(strDate);
	return date;
	
    }
    
    /**
     * Determines if the specified string fields are equal. The null or empty
     * fields are equal.
     * 
     * @param field1 - the first string field value
     * @param field2 - the second string field value
     * 
     * @return true if the fields are equal; false otherwise
     */
   
    public static boolean equalFields(String field1, String field2) {
	
	if (isEmpty(field1)) {
	    return isEmpty(field2);
	    
	} else {
	    return field1.equals(field2);
	    
	}
	
    }

    /**
     * Determines if the specified integer fields are equal.
     * 
     * @param field1 - the first integer field value
     * @param field2 - the second integer field value
     * 
     * @return true if the fields are equal; false otherwise
     */
    public static boolean equalFields(Double field1, Double field2) {
	return field1.equals(field2);
	
    }

    /**
     * Determines if the specified date fields are equal.
     * 
     * @param field1 - the first date field value
     * @param field2 - the second date field value
     * 
     * @return true if the fields are equal; false otherwise
     */
    public static boolean equalFields(java.util.Date field1,
	    java.util.Date field2) {
	if (field1 == null) {
	    return (field2 == null);
	    
	} else {
	    return field1.equals(field2);
	    
	}
	
    }
    
    /**
     * Determines if the specified date fields are equal.
     * 
     * @param field1 - the first date field value
     * @param field2 - the second date field value
     * 
     * @return true if the fields are equal; false otherwise
     */
    public static boolean equalFields(Color color1, Color color2) {
	if (color1 == null) {
	    return (color2 == null);	    
	} else {
	    return color1.equals(color2);
	    
	}
	
    }

    /**
     * Determines if the specified string is empty or null.
     * 
     * @param string - the string
     * 
     * @return true if the string is empty or null; false otherwise
     */
   
    public static boolean isEmpty(String string) {
	return (string.length() == 0);
	
    }
	   
}
