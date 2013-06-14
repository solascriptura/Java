/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.model;

/**
 * Class creates unparsable entity object.
 * @author Andrei_Ilyin1
 */
public class Entity {
    /**
     * Entity name.
     */
    private String name;

    /**
     * Method returns the entity name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Method sets the entity name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
       
}
