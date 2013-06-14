/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.parsers.stax;

import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.epam.xmlapp.exceptions.ParserException;
import com.epam.xmlapp.model.*;
import com.epam.xmlapp.parsers.ElementEnum;
import com.epam.xmlapp.parsers.IParser;
import com.epam.xmlapp.util.DataFormatter;

import com.epam.xmlapp.configuration.ConfigurationManager;

import static com.epam.xmlapp.constantdata.Constants.*;
import static javax.xml.stream.XMLStreamConstants.*;

/**
 * Class for a StAX product XML parser. 
 * StAX product XML parsers are used for parsing XML files
 * contained info about shop products using StAX model.
 * @author Andrei_Ilyin1
 */
public final class StAXParser implements IParser, IStAXParser {
    /**
     * Constant for the parse exception text.
     */
    private static final String PARSE_EXCEPTION_TEXT = 
	    "It's impossible to parse the product XML file " +
	    "using StAX parser either because of it's not found " +
	    "or because of its structure";
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(StAXParser.class);  

    /**
     * Constructs a new StAX product XML parser with null parameters.
     */
    public StAXParser() {}
    
    /** 
     * @see com.epam.xmlapp.parsers.IParser#parse(java.lang.String)
     */
    @Override
    public List<Category> parse(String xmlFile) throws ParserException {
	StreamReaderWrapper reader = null;
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("Start parsing XML file '" + xmlFile + "'");
	}	
	try {
	    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	    FileInputStream inputStream = new FileInputStream(xmlFile);
	    reader = new StreamReaderWrapper(
		    inputFactory.createXMLStreamReader(inputStream));
	    process(reader);	    
	} catch (Exception e) {
	    logger.error(e);
	    throw new ParserException(PARSE_EXCEPTION_TEXT, e);    
	}
	if(logger.isEnabledFor(Level.INFO)){	
	    logger.info("Parsing XML file '" + xmlFile + "' is completed");
	}
    	return reader.getCategories();
    }
      
    /**
     * Iterates over XML by the XMLStreamReader object 
     * and processes parse events.
     * @param reader - the XMLStreamReader object
     * @throws XMLStreamException if XMLStreamException occurs.
     */
    private void process(StreamReaderWrapper readerWrapper) throws XMLStreamException {
	XMLStreamReader reader = readerWrapper.getReader();
	while (reader.hasNext()) {
	    int type = reader.next();	    
	    switch (type) {
	    case START_ELEMENT:
		startElement(readerWrapper);
		break;
	    case CHARACTERS:
		characters(readerWrapper);
		break;
	    case END_ELEMENT:
		endElement(readerWrapper);
		break;
	    case START_DOCUMENT:
		startDocument(readerWrapper);
	    case END_DOCUMENT:
		endDocument(readerWrapper);
		break;
	    default:
		break;
	    }
	}
    }
    
    /**
     * @see com.epam.xmlapp.parsers.stax.IStAXParser#startElement(com.epam.xmlapp.parsers.stax.StreamReaderWrapper)
     */
    @Override
    public void startElement(StreamReaderWrapper readerWrapper) {	
	String elementName = readerWrapper.getReader().getLocalName();
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("Start of element " + "'" + elementName + "'");
	}		
	if (elementName.equals(CATEGORIES_ELEMENT)) {
	    readerWrapper.setCategories(new ArrayList<Category>());
	    return;
	}		
	ElementEnum startedElement = 
		ElementEnum.valueOf(elementName.toUpperCase());
	String name = null;		
	switch (startedElement) {
	case CATEGORY:
	    readerWrapper.setCategory(new Category());
	    name = getAttributeValue(readerWrapper, NAME_ATTRIBUTE_INDEX);
	    readerWrapper.getCategory().setName(name);
	    readerWrapper.setSubcategories(new ArrayList<Subcategory>());
	    break;
	case SUBCATEGORY:
	    readerWrapper.setSubcategory(new Subcategory());
	    name = getAttributeValue(readerWrapper, NAME_ATTRIBUTE_INDEX);
	    readerWrapper.getSubcategory().setName(name);
	    readerWrapper.setProducts(new ArrayList<Product>());
	    break;
	case PRODUCT:
	    readerWrapper.setProduct(new Product());
	    name = getAttributeValue(readerWrapper, NAME_ATTRIBUTE_INDEX);
	    readerWrapper.getProduct().setName(name);
	    break;
	default:
	    readerWrapper.setElement(startedElement);
	    break;
	}
    }
    
    /**
     * Gets the attribute value of the current element (parse event) 
     * from the XMLStreamReader object and returns it.
     * @param reader - the XMLStreamReader object
     * @param index - the position of the attribute
     * @return the attribute value
     */
    private String getAttributeValue(StreamReaderWrapper readerWrapper, int index) {
	XMLStreamReader reader = readerWrapper.getReader();
	String value = reader.getAttributeValue(index);
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("Attribute at position " + index + ": " + value);
	}
	return value;
    }
            
    /**
     * @see com.epam.xmlapp.parsers.stax.IStAXParser#characters(com.epam.xmlapp.parsers.stax.StreamReaderWrapper)
     */
    @Override
    public void characters(StreamReaderWrapper readerWrapper) {
	XMLStreamReader reader = readerWrapper.getReader();
	String text = reader.getText();
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("Element text: " + "'" + text + "'");
	}	
	if (readerWrapper.getElement() == null) {
	    return;
	}	
	Product product = readerWrapper.getProduct();
	try {
	    switch (readerWrapper.getElement()) {
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
	    }
	} catch (ParseException e) {
	    logger.error(e);
	}
    }

    /**
     * @see com.epam.xmlapp.parsers.stax.IStAXParser#endDocument(com.epam.xmlapp.parsers.stax.StreamReaderWrapper)
     */
    @Override
    public void endDocument(StreamReaderWrapper readerWrapper) {
	logger.info("Document parsing completed");
    }

    /**
     * @see com.epam.xmlapp.parsers.stax.IStAXParser#startDocument(com.epam.xmlapp.parsers.stax.StreamReaderWrapper)
     */
    @Override
    public void startDocument(StreamReaderWrapper readerWrapper) {
	logger.info("Document parsing started");
    }

    /**
     * @see com.epam.xmlapp.parsers.stax.IStAXParser#endElement(com.epam.xmlapp.parsers.stax.StreamReaderWrapper)
     */
    @Override
    public void endElement(StreamReaderWrapper readerWrapper) {
	XMLStreamReader reader = readerWrapper.getReader();
	String elementName = reader.getLocalName();
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("End of element " + "'" + elementName + "'");
	}	
	if (elementName.equals(CATEGORIES_ELEMENT)) {
	    return;
	}	
	ElementEnum endedElement = 
		ElementEnum.valueOf(elementName.toUpperCase());	
	Category category = readerWrapper.getCategory();
	List<Category> categories = readerWrapper.getCategories();
	List<Subcategory> subcategories = readerWrapper.getSubcategories();
	List<Product> products = readerWrapper.getProducts();
	Subcategory subcategory = readerWrapper.getSubcategory();
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
	    products.add(readerWrapper.getProduct());
	    break;
	default:
	    readerWrapper.setElement(null);
	    break;
	}
    }  
}