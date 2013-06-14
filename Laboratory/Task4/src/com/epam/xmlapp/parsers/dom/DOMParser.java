/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.parsers.dom;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.epam.xmlapp.exceptions.ParserException;
import com.epam.xmlapp.parsers.IParser;
import com.epam.xmlapp.model.Category;


/**
 * Class for a DOM product XML parser. 
 * DOM product XML parsers are used for parsing XML files
 * contained info about shop products using DOM model.
 * @author Andrei_Ilyin1
 */
public final class DOMParser implements IParser {
    /**
     * Constant for the parse exception text.
     */
    private static final String PARSE_EXCEPTION_TEXT = 
	    "It's impossible to parse the product XML file using DOM parser " +
	    "either because of it's not found or because of its structure";
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(DOMParser.class);
	
    /**
     * Constructs a new DOM product XML parser.
     */
    public DOMParser() {}

    /** 
     * {@inheritDoc}
     * @see com.epam.xmlapp.parsers.IParser#parse(java.lang.String)
     */
    @Override
    public List<Category> parse(String xmlFile) throws ParserException {
	
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("Start parsing XML file '" + xmlFile + "'");
	}
    	
    	List<Category> categories = null;
    	
    	try {
    	    //Get factory
    	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	    //Tune-up parsers factory, set ignore spaces mode
    	    dbf.setIgnoringElementContentWhitespace(true);
    	    //Get DOM parser from factory
    	    DocumentBuilder db = dbf.newDocumentBuilder();
    	    //Parse xmlFile, get Document 
    	    Document doc = db.parse(xmlFile);
    	    //Get root element 
    	    Element root = ((Document)doc).getDocumentElement();
    	    //Get Document structure 
    	    categories = DOMAnalyzer.categoryListBuilder(root);
    	    
    	} catch (Exception e) {
    	    logger.error(e);
    	    throw new ParserException(PARSE_EXCEPTION_TEXT, e);	
    	}
    	if(logger.isEnabledFor(Level.INFO)){
    	    logger.info("Parsing XML file '" + xmlFile + "' is completed");
    	}
     	return categories;
	
    }
}