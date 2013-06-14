/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.model;

import java.util.Date;

import com.epam.xmlapp.util.DataFormatter;

/**
 * Class Product implements AbstractEntity interface,
 * contains some methods which provide 
 * access to the data fields and the constructor that 
 * create parsable entity object.
 * @author Andrei_Ilyin1
 */
public final class Product extends Entity {
    /**
     * Product producer name.
     */
    private String producer;
    /**
     * Product model.
     */
    private String model;
    /**
     * Product issue date.
     */
    private Date issueDate;
    /**
     * Product color.
     */
    private Color color;
    /**
     * Product price.
     */
    private Double price;
    /**
     * Product stock status.
     */
    private boolean notInStock;
    
    /**
     * Creates a new product object with default parameters.
     */
    public Product() {}
    
    //GETTERS
    
    /**
     * Returns the producer name
     * @return the producer
     */
    public String getProducer() {
        return producer;
    }

    /**
     * Returns the model name
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the product issue date
     * @return the issueDate
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     * Returns the product color
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the product price
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Returns product stock status if present
     * @return the notInStock
     */
    public boolean isNotInStock() {
        return notInStock;
    }
    
    //SETTERS

    /**
     * Sets the product producer name
     * @param producer the product producer to set
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * Sets the product model
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Sets the product issue date
     * @param issueDate the issueDate to set
     */
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Sets the product color
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the product price
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Sets the product stock status is present
     * @param notInStock the notInStock to set
     */
    public void setNotInStock(boolean notInStock) {
        this.notInStock = notInStock;
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
	
	if (obj instanceof Product) {
	    Product product = (Product) obj;
	
	    return  DataFormatter.equalFields(this.getColor(), product.getColor()) && 
		    DataFormatter.equalFields(this.getIssueDate(), product.getIssueDate()) && 
		    DataFormatter.equalFields(this.getModel(), product.getModel()) && 
		    DataFormatter.equalFields(this.getProducer(), product.getProducer()) &&
		    DataFormatter.equalFields(this.getPrice(), product.getPrice()) &&
		    DataFormatter.equalFields(this.getName(), product.getName());
	    } else {
		return false;
		
	    }
    }

    /**
     * Overridden method
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	int hash = 31;
	hash += (this.color == null ? 0 : this.color.hashCode()) + 
		(this.issueDate == null ? 0 : this.issueDate.hashCode()) + 
		(this.model == null ? 0 : this.model.hashCode()) + 
		(this.price == null ? 0 : this.price.hashCode()) + 
		(this.getName() == null ? 0 : this.getName().hashCode()) +
		(this.producer == null ? 0 : this.producer.hashCode());
	return hash;	
    } 
    
}
