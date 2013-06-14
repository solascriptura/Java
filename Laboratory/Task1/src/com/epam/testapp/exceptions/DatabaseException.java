/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.exceptions;

/**
 * Class DatabaseException contains objects and methods which allow to pack
 * database layer exceptions
 * @author Andrei_Ilyin1
 */
public class DatabaseException extends SafetyException {
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
    public DatabaseException(String message) {
	super(message);
	
    }
    
    /**
     * Constructor creates a new database connection exception 
     * with the detailed message and packed exception.
     * 
     * @param message - the detail message
     * @param exception - the hidden exception
     */
    public DatabaseException(String message, Exception exception) {
	super(message, exception);
	
    }
    
}
