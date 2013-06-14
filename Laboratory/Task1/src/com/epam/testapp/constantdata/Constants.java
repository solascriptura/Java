/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.constantdata;

/**
 * Class contains different application constants
 * @author Andrei_Ilyin1
 */
public final class Constants {
    
    public static final String ENCODING_PARAMETER = "encoding";

    public static final String REFERRER_HEADER = "referer";

    public static final String SUCCESS_FORWARD_PATH = "success";

    public static final String LIST_FORWARD_PATH = "list";

    public static final String VIEW_FORWARD_PATH = "view";

    public static final String VIEW_FORWARD_ERROR = "error";
 
    public static final String DEFAULT_LANGUAGE = "en";

    public static final int VALUE_ZERO = 0;

    public static final int INDEX_ZERO = 0;

    public static final int INDEX_ONE = 1;

    public static final int INDEX_TWO = 2;

    public static final int INDEX_THREE = 3;

    public static final int INDEX_FOUR = 4;

    public static final int INDEX_FIVE = 5;
    
    public static final int INDEX_SIX = 6;

    public static final String INIT = "INIT";

    public static final String LIST = "LIST";

    public static final String DELETE = "DELETE";
    
    public static final String ADD = "ADD";
    
    public static final String LANGUAGES_DELIMITER = " ";
    
    public static final String LANGUAGES_LIST = "LANGUAGES_LIST";
    /**
     * Constant for change language message
     */
    public static final String LANGUAGE_DEFAULT ="Language was changed to dafault";
    /*
     * Constant contains change language by filter text.
     */
    public static final String CHANGE_LANGUAGE_FILTER = "Character encoding set by filter: ";
    /**
     * Constant for the path to configuration property file.
     */
    public static final String RESOURCE_PROPERTIES_PATH = "resource.AppResources";
    
    public static final String CONFIGURATION_PROPERTIES_PATH = "resource.Configuration";
    /**
     * Title field max length   
     */
    public static final int TITLE_MAXLENGTH = 100;
    /**
     * Brief field max length
     */
    public static final int BRIEF_MAXLENGTH = 500;
    /**
     * Content field max length
     */
    public static final int CONTENT_MAXLENGTH = 2048;
    /**
     * International date validate pattern resource
     */
    public static final String DATEPATTERN = "date.regexp";
    /**
     * Constructs a new Constants object with the default parameters.
     */
    private Constants() {	
    }

}
