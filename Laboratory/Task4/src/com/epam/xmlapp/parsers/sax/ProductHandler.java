/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.parsers.sax;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

import com.epam.xmlapp.model.*;
import com.epam.xmlapp.parsers.ElementEnum;
import com.epam.xmlapp.util.DataFormatter;
import com.epam.xmlapp.configuration.ConfigurationManager;

import static com.epam.xmlapp.constantdata.Constants.*;

/**
 * Class for a product handler. The class contains implemented basic 
 * document-related events like the start and end of elements 
 * and character data. An instance is used by XML parsers 
 * for parsing XML files with products divided into categories.
 * @author Andrei_Ilyin1
 */
final class ProductHandler implements ContentHandler {
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(ProductHandler.class);
    /**
     * Current category.
     */
    private Category category;
    /**
     * Current sub category.
     */
    private Subcategory subcategory;
    /**
     * Current product.
     */
    private Product product;
    /**
     * Current element as enumeration.
     */
    private ElementEnum element;
    /**
     * List of categories.
     */
    private List<Category> categories;
    /**
     * List of sub categories.
     */
    private List<Subcategory> subcategories;
    /**
     * List of products.
     */
    private List<Product> products;
    
    /**
     * Constructs a new product handler with null parameters.
     */
    public ProductHandler() {}
    
    /*
     * {@inheritDoc}
     */
    public void startElement(String uri, String localName, String qName, 
	    Attributes attrs) {
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("Start of element " + "'" + qName + "'");
	}
		
	if (qName.equals(CATEGORIES_ELEMENT_WITH_PREFIX)) {
	    categories = new ArrayList<Category>();
	    return;
	}
		
	ElementEnum startedElement = ElementEnum.valueOf(localName.toUpperCase());
	String name = null;
	
	switch (startedElement) {
	
	case CATEGORY:
	    category = new Category();
	    name = getAttributeValue(attrs, NAME_ATTRIBUTE);
	    category.setName(name);
	    subcategories = new ArrayList<Subcategory>();
	    break;
	    
	case SUBCATEGORY:
	    subcategory = new Subcategory();
	    name = getAttributeValue(attrs, NAME_ATTRIBUTE);
	    subcategory.setName(name);
	    products = new ArrayList<Product>();
	    break;
	    
	case PRODUCT:
	    product = new Product();
	    name = getAttributeValue(attrs, NAME_ATTRIBUTE);
	    product.setName(name);
	    break;
	    
	default:
	    element = startedElement;
	    break;
	}
    }
    
    /**
     * Gets the attribute value from the list of attributes and returns it.
     * @param attrs - the list of attributes
     * @param name - the attribute name
     * @return the attribute value
     */
    private String getAttributeValue(Attributes attrs, String name) {
	String value = attrs.getValue(name);
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("'" + name + "' attribute: " + value);
	}
	return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void characters(char[] ch, int start, int length) {
	String text = new String(ch, start, length).trim();
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("Element text: " + "'" + text + "'");
	}
	
	if (element == null) {
	    return;
	}
	
	try {
	    switch (element) {
	    case PRODUCER:
		product.setProducer(text);
		break;
	    case MODEL:
		product.setModel(text);
		break;
	    case COLOR:
		Color color = Color.valueOf(text.toUpperCase());
		product.setColor(color);
		break;
	    case ISSUEDATE:
		String datePattern = ConfigurationManager.getInstance()
		.getProperty(ISSUE_DATE_PATTERN);
		Date issueDate = DataFormatter.parseToDate(text, datePattern);
		product.setIssueDate(issueDate);
		break;
	    case PRICE:
		double price = Double.valueOf(text);
		product.setPrice(price);
		break;
	    case NOTINSTOCK:
		boolean notInStock = Boolean.valueOf(text);
		product.setNotInStock(notInStock);
		break;
	    default:
		break;
	    }
	} catch (ParseException e) {
	    logger.error(e);
	}
    }
    
    /**
     * {@inheritDoc}
     */	
    public void endElement(String uri, String localName, String qName) {
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("End of element " + "'" + qName + "'");
	}
	
	if (qName.equals(CATEGORIES_ELEMENT_WITH_PREFIX)) {
	    return;
	}
	
	ElementEnum endedElement = ElementEnum.valueOf(localName.toUpperCase());
	
	switch (endedElement) {
	case CATEGORY:
	    category.setSubcategories(subcategories);
	    categories.add(category);
	    break;
	case SUBCATEGORY:
	    subcategory.setProducts(products);
	    subcategories.add(subcategory);
	    break;
	case PRODUCT:
	    products.add(product);
	    break;
	default:
	    element = null;
	    break;
	}
    }
    
    /**
     * {@inheritDoc}
     */
    public void startDocument() {
	logger.info("Document parsing started");
    }
    
    /**
     * {@inheritDoc}
     */
    public void endDocument() {
	logger.info("Document parsing completed");
    }
	
    /**
     * {@inheritDoc}
     */
    public void endPrefixMapping(String s) {
    }
	
    /**
     * {@inheritDoc}
     */
    public void processingInstruction(String s1, String s2) {
    }
    
    /**
     * {@inheritDoc}
     */
    public void setDocumentLocator(Locator s) {
    }
    
    /**
     * {@inheritDoc}
     */
    public void ignorableWhitespace(char[] ch,
	    int start, int length) {
	characters(ch, start, length);
    }
    
    /**
     * {@inheritDoc}
     */
    public void skippedEntity(String s) {
    }
    
    /**
     * {@inheritDoc}
     */
    public void startPrefixMapping(String s1, String s2) {
    }
    
    /**
     * Returns the list of product categories of this product handler.
     * @return the list of product categories
     */
    public List<Category> getCategorys() {
	return categories;
    }
}