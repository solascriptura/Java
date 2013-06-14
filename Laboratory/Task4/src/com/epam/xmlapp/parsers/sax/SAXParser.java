/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.parsers.sax;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.epam.xmlapp.exceptions.ParserException;
import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.parsers.IParser;


/**
 * Class for a SAX product XML parser. 
 * SAX product XML parsers are used for parsing XML files
 * contained info about shop products using SAX model.
 * @author Andrei_Ilyin1
 */
public final class SAXParser implements IParser {
    /**
     * Constant for the parse exception text.
     */
    private static final String PARSE_EXCEPTION_TEXT = 
    		"It's impossible to parse the product XML file using SAX parser " +
    		"either because of it's not found or because of its structure";
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(SAXParser.class);
    
    /**
     * {@inheritDoc}
     */
    @Override 
    public List<Category> parse(String xmlFile) throws ParserException {
	if(logger.isEnabledFor(Level.INFO)){	
	    logger.info("Start parsing XML file '" + xmlFile + "'");
	}	
	List<Category> categories = null;
	try {
	    XMLReader reader = XMLReaderFactory.createXMLReader();
	    ProductHandler handler = new ProductHandler();
	    reader.setContentHandler(handler);
	    reader.parse(xmlFile);
	    categories = handler.getCategorys();
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