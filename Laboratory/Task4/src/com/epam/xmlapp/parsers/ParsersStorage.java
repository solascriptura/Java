/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.parsers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.epam.xmlapp.parsers.dom.DOMParser;
import com.epam.xmlapp.parsers.sax.SAXParser;
import com.epam.xmlapp.parsers.stax.StAXParser;

import static com.epam.xmlapp.constantdata.Constants.*;

/**
 * Class for a product XML parsers storage. Contains methods for getting
 * the product XML parser according to the request parameters.
 * @author Andrei_Ilyin1
 */
public final class ParsersStorage {
    
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(ParsersStorage.class);
    /**
     * Possible parsers.
     */
    private static HashMap<String, IParser> parsers = new HashMap<String, IParser>();
	
    /**
     * Static initialization block.
     */
    static {
	parsers.put(DOM_PARSER, new DOMParser());
	parsers.put(SAX_PARSER, new SAXParser());
	parsers.put(STAX_PARSER, new StAXParser());	
    }
    
    /**
     * Returns the product XML parser instance that matches 
     * the parser type parameter value from the request.
     * @param request - the request
     * @return the product XML parser instance
     */
    public static IParser getParser(HttpServletRequest request) {
	String parserType = request.getParameter(PARSER_TYPE_PARAMETER);
	IParser parser = parsers.get(parserType);
	if(logger.isEnabledFor(Level.INFO)){
	    logger.info("Recieved parser: " + 
		((parser != null) ? parser.getClass().getName() : null));
	}
	return parser;
    }
}
