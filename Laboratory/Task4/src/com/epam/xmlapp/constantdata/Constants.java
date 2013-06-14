/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.constantdata;

/**
 * Class contains different application constants
 * @author Andrei_Ilyin1
 */
public final class Constants {
    public static final String PARSER_TYPE_PARAMETER = "parser";
   
    public static final String LIST_OF_CATEGORIES_ATTRIBUTE = "categories";
    
    public static final String EXCEPTION_TEXT_ATTRIBUTE = "error";
    /**
     * Constant for DOM parser name value.
     */
    public static final String DOM_PARSER = "DOM";
    /**
     * Constant for SAX parser name value.
     */
    public static final String SAX_PARSER = "SAX";
    /**
     * Constant for StAX parser name value.
     */
    public static final String STAX_PARSER = "StAX";
    /**
     * Constant for the path to configuration property file.
     */
    public static final String CONFIGURATION_PROPERTIES_PATH = "resource.Config";
    /**
     * Constant for product XML file full path property key.
     */
    public static final String PRODUCT_XML_PATH = "path.xml.product";
    /**
     * Constant for index JSP property key.
     */
    public static final String INDEX_JSP = "jsp.index";
    /**
     * Constant for parsing result JSP property key.
     */
    public static final String RESULT_JSP = "jsp.result";
    /**
     * Constant for error JSP property key.
     */
    public static final String ERROR_JSP = "jsp.error";
    /**
     * Constant for issue date pattern property key.
     */
    public static final String ISSUE_DATE_PATTERN = "pattern.date.issue";
    
    public static final String CATEGORIES_ELEMENT_WITH_PREFIX = "product:categories";
   
    public static final String CATEGORIES_ELEMENT = "categories";
   
    public static final String CATEGORY_ELEMENT = "category";
   
    public static final String SUBCATEGORY_ELEMENT = "subcategory";
   
    public static final String PRODUCT_ELEMENT = "product";
   
    public static final String PRODUCER_ELEMENT = "producer";
   
    public static final String MODEL_ELEMENT = "model";
    
    public static final String ISSUE_DATE_ELEMENT = "issueDate";
   
    public static final String COLOR_ELEMENT = "color";
    
    public static final String NOT_IN_STOCK_ELEMENT = "notInStock";
    
    public static final String PRICE_ELEMENT = "price";
    
    public static final String NAME_ATTRIBUTE = "name";
    
    public static final int NAME_ATTRIBUTE_INDEX = 0;
    
    public static final int ZERO_VALUE = 0;
	
    /**
     * Constructs a new Constants object with the default parameters.
     */
    private Constants() { }

}
