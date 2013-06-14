/**
 * Copyright (c) 2013
 */
package com.epam.xmlapp.exceptions;

/**
 * The SafetyException class description:
 * Abstract class contains objects and methods which allow to pack exceptions which 
 * can occur on all levels of the application work.
 * @author Andrei_Ilyin1
 */
public class SafetyException extends Exception {
    /**
     * Constant for the serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Pointer to the packed exception.
     */
    private Exception hiddenException;
    
    /**
     * Constructor gets message detailed by user.
     * 
     * @param message - the detailed message
     */
    public SafetyException(String message) {
	super(message);
	
    }

    /**
     * Constructor gets message detailed by user and object exception which will be packed.
     * 
     * @param message - the detailed message
     * @param exception - the exception which will be packed
     */
    public SafetyException(String message, Exception exception) {
	super(message);
	hiddenException = exception;
	
    }

    /**
     * Method returns the packed exception.
     * 
     * @return the exception that was packaged
     */
    public Exception getHiddenException() {
	return hiddenException;
	
    }
    
}
