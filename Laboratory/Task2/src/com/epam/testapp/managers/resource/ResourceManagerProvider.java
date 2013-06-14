/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.managers.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import com.epam.testapp.managers.configuration.ConfigurationManager;

import static com.epam.testapp.constantdata.Constants.*;

/**
 * Class contains the methods which provide create and getting access 
 * to resources that match the parameter locale value from the request. 
 * @author Andrei_Ilyin1
 */
public class ResourceManagerProvider {
    /**
     * Single instance of the class.
     */
    private static ResourceManagerProvider instance = null;

    /**
     * Constant for languages list.
     */
    private static final ArrayList<String> languagesList = 
	    new ArrayList<String>(
		    Arrays.asList(ConfigurationManager.getInstance().
			    getProperty(LANGUAGES_LIST).split(LANGUAGES_DELIMITER)));
    /**
     * Possible resource managers.
     */
    private HashMap<String, ResourceManager> resourceManagers =
	    new HashMap<String, ResourceManager>();
 
    /**
     * Constructs a new resource manager provider with the possible commands.
     */
    private ResourceManagerProvider() {
	Locale locale = null;
	ResourceManager resourceManager = null;
	for (String language : languagesList) {
	    locale = new Locale(language);
	    resourceManager = new ResourceManager(locale);
	    resourceManagers.put(language, resourceManager);
	    
	}
    }
 
    /**
     * Returns the resource manager instance that matches
     * the language parameter value from the request.
     * @param language - the language value
     * @return the instance of ResourceManager class
     */
    public ResourceManager getResourceManager(String language) {
	if (language == null || language.isEmpty()) {
	    language = ConfigurationManager.getInstance()
		    .getProperty(DEFAULT_LANGUAGE);
	}
	ResourceManager resourceManager = resourceManagers.get(language);
	return resourceManager;
    }
 
    /**
    * Return the instance of ResourceManagerProvider class.
    * @return the instance of the class
    */
    public static ResourceManagerProvider getInstance() {
	if (instance == null) {
	    instance = new ResourceManagerProvider();    
	}
	return instance;	
    }

}
