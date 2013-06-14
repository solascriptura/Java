/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.managers.resource;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


import static com.epam.testapp.constantdata.Constants.*;


/**
 * Class provides access to the resource properties 
 * considering of selected locale.
 * @author Andrei_Ilyin1
 */
public final class ResourceManager {
    /**
     * Resource bundle pointer
     */
    private ResourceBundle resourceBundle;
    
    /**
     * Constructor which binds the resource pointer 
     * to language resource file
     * @param locale
     */
    ResourceManager(Locale locale) {
	resourceBundle = 
		ResourceBundle.getBundle(RESOURCE_PROPERTIES_PATH, locale);	 
    } 
    
    /**
     * Return the resource property value by the property key.
     * @param key
     * @return
     * @throws NullPointerException
     * @throws MissingResourceException
     * @throws ClassCastException
     */
    public String getProperty(String key) 
	    throws NullPointerException, MissingResourceException, ClassCastException {
        return resourceBundle.getString(key);
    }

}
