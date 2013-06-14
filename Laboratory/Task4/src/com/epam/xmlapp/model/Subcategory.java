/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.epam.xmlapp.util.DataFormatter;

/**
 * Class Subcategory implements AbstractEntity interface,
 * contains some methods which provide 
 * access to the data fields and the constructor that 
 * create parsable entity object.
 * @author Andrei_Ilyin1
 */
public final class Subcategory extends Entity {
    /**
     * Each Subcategory contains products list.
     */
    private List<Product> products = new ArrayList<Product>();
    
    /**
     * Constructs a new Subcategory object with the default parameters.
     */
    public Subcategory() {}

    /**
     * Returns the products list for each sub category
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the products list for the sub category.
     * @param products - the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Overridden method
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	int hashCode = 
		31+(this.getName() == null ? 0 : this.getName().hashCode());
	
	 Iterator<Product> i = products.iterator();
	 while (i.hasNext()) {
	     Product obj = i.next();
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
	
	if (obj instanceof Subcategory) {
	    Subcategory subcategory = (Subcategory) obj;	
	    return 
		    this.getProducts().
		    containsAll(subcategory.getProducts())&&
		    DataFormatter.equalFields(this.getName(), subcategory.getName());
	    } else {
		return false;		
	    }
    }  
          
}
