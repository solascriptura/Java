/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.epam.xmlapp.util.DataFormatter;

/**
 * Class Category implements AbstractEntity interface,
 * contains some methods which provide 
 * access to the data fields and the constructor that 
 * create parsable entity object.
 * @author Andrei_Ilyin1
 */
public final class Category extends Entity {
    /**
     * Each Category contains sub categories list.
     */
    private List<Subcategory> subcategories = new ArrayList<Subcategory>();
    /**
     * Creates a new Category object with default parameters.
     */
    public Category() {}

    /**
     * Returns the sub categories list for each category
     * @return the sub categories
     */
    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    /**
     * Sets the sub categories list for the category
     * @param subcategories - the sub categories to set
     */
    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    /**
     * Overridden method
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	int hashCode = 
		31+(this.getName() == null ? 0 : this.getName().hashCode());
	
	 Iterator<Subcategory> i = subcategories.iterator();
	 while (i.hasNext()) {
	     Subcategory obj = i.next();
	     hashCode +=  
		     (obj == null ? 0 : obj.hashCode());
	 }
	return hashCode;
    }

    /**
     * Overridden method
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;    
	}
	
	if (obj == null) {
	    return false;    
	}
	
	if (obj instanceof Category) {
	    Category category = (Category) obj;	
	    return 
		    this.getSubcategories().
		    containsAll(category.getSubcategories())&&
		    DataFormatter.equalFields(this.getName(), category.getName());
	    
	    } else {
		return false;		
	    }
    }  
    
}
