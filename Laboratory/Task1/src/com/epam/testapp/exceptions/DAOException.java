/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.exceptions;

/**
 * Class DAOException contains objects and methods which allow to pack
 * data access layer exceptions
 * @author Andrei_Ilyin1
 */
public class DAOException extends SafetyException {
    /**
     * Constant for the serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor creates a new database connection exception 
     * with the detailed message.
     * 
     * @param message - the detail message
     */
    public DAOException(String message) {
	super(message);
	
    }
    
    /**
     * Constructor creates a new database connection exception 
     * with the detailed message and packed exception.
     * 
     * @param message - the detail message
     * @param exception - the hidden exception
     */
    public DAOException(String message, Exception exception) {
	super(message, exception);
	
    }

    
    
    
    
    

}
