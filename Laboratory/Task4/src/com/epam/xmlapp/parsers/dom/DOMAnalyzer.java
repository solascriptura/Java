/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.parsers.dom;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.epam.xmlapp.configuration.ConfigurationManager;
import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.model.Color;
import com.epam.xmlapp.model.Product;
import com.epam.xmlapp.model.Subcategory;
import com.epam.xmlapp.util.DataFormatter;

import static com.epam.xmlapp.constantdata.Constants.*;

/**
 * Class contains static methods for analyzing trees of Category objects.
 * @author Andrei_Ilyin1
 */
final class DOMAnalyzer {
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(DOMAnalyzer.class);
    
    /**
     * Constructs a new DOM analyzer with default parameters.
     */
    private DOMAnalyzer() {}
    
    /**
     * Builds the list of Category objects from the tree of Category objects.
     * @param root - the root element of Category tree
     * @return the list of Category objects
     * @throws ParseException if ParseException occurs
     */
    public static List<Category> categoryListBuilder(Element root) 
	    throws ParseException {
	logger.info("Start Category three analyzing");
		
	List<Category> categories = new ArrayList<Category>();
	NodeList categoryNodes = root.getElementsByTagName(CATEGORY_ELEMENT);
	int categoryLength = categoryNodes.getLength();	
	
	for (int i = 0; i < categoryLength; i++) { 
	    Category category = new Category(); 
	    Element categoryElement = (Element) categoryNodes.item(i);
			
	    String name = getAttribute(categoryElement, NAME_ATTRIBUTE);
	    category.setName(name);
			
	    List<Subcategory> subcategories = subcategoryListBuilder(categoryElement);
	    category.setSubcategories(subcategories);
			
	    categories.add(category);
	}
			
	logger.info("Category three analyzing is completed");
	return categories;
    }
	
    /**
     * Builds the list of Sub category objects from the tree of Category objects.
     * @param root - the root element of Sub category tree
     * @return the list of Sub category objects
     * @throws ParseException if ParseException occurs
     */
    private static List<Subcategory> subcategoryListBuilder(Element root) 
	    throws ParseException {
	logger.info("Start Subcategory three analyzing");
	
	List<Subcategory> subcategories = new ArrayList<Subcategory>();
	NodeList subcategoryNodes = root.getElementsByTagName(SUBCATEGORY_ELEMENT);
	int subcategorylength = subcategoryNodes.getLength();
	
	for (int i = 0; i < subcategorylength; i++) { 
	    Subcategory subcategory = new Subcategory(); 
	    Element subcategoryElement = (Element) subcategoryNodes.item(i);
	    
	    String name = getAttribute(subcategoryElement, NAME_ATTRIBUTE);
	    subcategory.setName(name);
			
	    List<Product> products = productListBuilder(subcategoryElement);
	    subcategory.setProducts(products);
			
	    subcategories.add(subcategory);
	}
			
	logger.info("Subcategory three analyzing is completed");
	return subcategories;
    }
	
    /**
     * Builds the list of Product objects from the tree of Product objects.
     * @param root - the root element of Product tree
     * @return the list of Product objects
     * @throws ParseException if ParseException occurs
     */
    private static List<Product> productListBuilder(Element root) 
	    throws ParseException {
	logger.info("Start Product three analyzing");
		
	List<Product> products = new ArrayList<Product>();
	NodeList productNodes = root.getElementsByTagName(PRODUCT_ELEMENT);
	int productLength = productNodes.getLength();
	
	for (int i = 0; i < productLength; i++) { 
	    Product product = new Product(); 
	    Element productElement = (Element) productNodes.item(i);
			
	    String name = getAttribute(productElement, NAME_ATTRIBUTE);
	    product.setName(name);
			
	    String producer = getChildValue(productElement, PRODUCER_ELEMENT);
	    product.setProducer(producer);
			
	    String model = getChildValue(productElement, MODEL_ELEMENT);
	    product.setModel(model);
			
	    String dateValue = getChildValue(productElement, ISSUE_DATE_ELEMENT);
	    String datePattern = ConfigurationManager.getInstance()
		    .getProperty(ISSUE_DATE_PATTERN);
	    Date issueDate = DataFormatter.parseToDate(dateValue, datePattern);
	    product.setIssueDate(issueDate);
			
	    String colorValue = getChildValue(productElement, COLOR_ELEMENT);
	    Color color = Color.valueOf(colorValue.toUpperCase());
	    product.setColor(color);
			
	    if (isChildPresent(productElement, PRICE_ELEMENT)) {
		String priceValue = getChildValue(productElement, PRICE_ELEMENT);
		double price = Double.valueOf(priceValue);
		product.setPrice(price);
	    }
			
	    if (isChildPresent(productElement, NOT_IN_STOCK_ELEMENT)) {
		String notInStockValue = getChildValue(productElement, NOT_IN_STOCK_ELEMENT);
		boolean notInStock = Boolean.valueOf(notInStockValue);
		product.setNotInStock(notInStock);
	    }
			
	    products.add(product);
	}
			
	logger.info("Product three analyzing is completed");
	return products;
    }
	
    /**
     * Returns the attribute of the tree element.
     * @param element - the element
     * @param attribute - the attribute name 
     * @return the value of the attribute
     */
    private static String getAttribute(Element element, String attribute) {
	String value = element.getAttribute(attribute);
	
	if(logger.isEnabledFor(Level.INFO)){
	logger.info("'" + attribute + "' attribute of '" +
		element.getNodeName() + "' element: " + value);
	}
	return value;
    }
	
    /**
     * Returns if the element has the child.
     * @param parent - the element
     * @param childName - the child element name
     * @return true if the element has the child; false otherwise
     */
    private static boolean isChildPresent(Element parent, String childName) {
	NodeList nlist = parent.getElementsByTagName(childName);
	int nLength = nlist.getLength();
	return (nLength > ZERO_VALUE);
    }
    
    /**
     * Returns the child of the element.
     * @param parent - the element
     * @param childName - the child element name
     * @return the child element
     */
    private static Element getChild(Element parent, String childName) {
	NodeList nlist = parent.getElementsByTagName(childName);
	Element child = (Element) nlist.item(0); 
	return child; 
    } 
    
    /**
     * Returns the body value of the child element.
     * @param parent - the element
     * @param childName - the child element name
     * @return the child element body value
     */
    private static String getChildValue(Element parent, String childName) { 
	Element child = getChild(parent, childName); 
	Node node = child.getFirstChild(); 
	String value = node.getNodeValue();
	
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("'" + childName + 
		    "' element of '" + parent.getNodeName() + 
		    "' element: " + value);
	}
	return value; 
    }
}