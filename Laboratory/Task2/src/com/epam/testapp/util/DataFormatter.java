/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contains static methods date format transformation.
 * @author Andrei_Ilyin1
 */
public final class DataFormatter {
    /**
     * Constructs a new DataFormatter object with the default parameters.
     */
    private DataFormatter() {
	
    }
    
    /**
     * Returns the arguments in the specified format.
     * 
     * @param format - the format string as described in Format string syntax
     * 
     * @param args - the arguments referenced by the format specifiers in the
     * format string. If there are more arguments than format
     * specifiers, the extra arguments are ignored. The maximum
     * number of arguments is limited by the maximum dimension of a
     * Java array as defined by the Java Virtual Machine
     * Specification
     * @return the arguments in the specified format
     */
    public static String format(String format, Object... args) {
	Formatter formatter = new Formatter();
	formatter.format(format, args);
	return formatter.toString();
	
    }

    /**
     * Converts the String value to the java.util.Date object according to the
     * date pattern.
     * 
     * @param s - the String value
     * @param datePattern - the date pattern
     * 
     * @return the java.util.Date object
     * 
     * @throws ParseException if ParseException occurs
     */
    public static java.util.Date parseToDate(String s, String datePattern)
	    throws ParseException {
	DateFormat dateFormat = new SimpleDateFormat(datePattern);
	Date date = dateFormat.parse(s);
	return date;
	
    }

    /**
     * Converts the java.util.Date object to the java.sql.Date object which is
     * equal to the first one.
     * 
     * @param date - the java.util.Date object
     * 
     * @return the java.sql.Date object
     */
    public static java.sql.Date toDBDate(java.util.Date date) {
	long time = date.getTime();
	return new java.sql.Date(time);
	
    }
    
    /**
	 * Converts the array of basic integer values to the Integer list 
	 * which contains all array items in the same order.
	 * @param date - the java.util.Date object
	 * @return the java.sql.Date object
	 */
	public static List<Integer> toList(int[] array) {
	    /*Integer[] list = new Integer[array.length];
		for (int x=0;x<array.length;x++) {
			list[x]=array[x];
		
		}
		*/
	    List<Integer> list = new ArrayList<Integer>(array.length);
		for (int item : array) {
			list.add(item);
		}
		return list;
	}

    /**
     * Determines if the specified character sequence and regular expression
     * match.
     * 
     * @param input - the input character sequence to check
     * @param regex - the regular expression
     * 
     * @return true if the input character sequence matches the regular
     * expression; false otherwise
     */
    public static boolean match(CharSequence input, String regex) {
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(input);
	return matcher.matches();
	
    }
	   
}
