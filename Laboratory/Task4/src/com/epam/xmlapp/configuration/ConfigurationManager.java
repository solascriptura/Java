/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.configuration;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.epam.xmlapp.constantdata.Constants.*;


/**
 * Class provides access to the configuration properties.
 * @author Andrei_Ilyin1
 */
public final class ConfigurationManager {
    /**
     * Single instance of the class.
     */
    private static ConfigurationManager instance = new ConfigurationManager();
    /**
     * Resource bundle.
     */
    private ResourceBundle resourceBundle;
    
    /**
     * Return the configuration property value by the property key.
     * @param key - the property key
     * @return the property value
     * @throws NullPointerException - if key is null
     * @throws MissingResourceException - if no object for 
     * the given key can be found
     * @throws ClassCastException - if the object found for 
     * the given key is not a string
     */
    public String getProperty(String key) 
	    throws NullPointerException, MissingResourceException, ClassCastException {
        return resourceBundle.getString(key);
    }
    
    /**
     * Constructs a new configuration manager 
     * with the configuration properties file.
     */
    private ConfigurationManager() {
	resourceBundle = ResourceBundle.getBundle(CONFIGURATION_PROPERTIES_PATH);
    }

    /**
     * Return the instance of ConfigurationManager class.
     * @return the instance of the class
     */
    public static ConfigurationManager getInstance() {
	return instance;
    }

}
