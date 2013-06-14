/**
 * Copyright (c) 2013
 */
package com.epam.xmlapp.exceptions;

/**
 * Class for a database connection exception.
 * 
 * @author Andrei_Ilyin1
 */
public class ParserException extends SafetyException {
    /**
     * Constant for the serial version UID.
     */
    private static final long serialVersionUID = 2L;
    
    /**
     * Constructor creates a new database connection exception 
     * with the detailed message.
     * 
     * @param message - the detail message
     */
    public ParserException(String message) {
	super(message);
	
    }
    
    /**
     * Constructor creates a new database connection exception 
     * with the detailed message and packed exception.
     * 
     * @param message - the detail message
     * @param exception - the hidden exception
     */
    public ParserException(String message, Exception exception) {
	super(message, exception);
	
    }
    
}
