/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.xmlapp.configuration.ConfigurationManager;
import com.epam.xmlapp.exceptions.ParserException;
import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.parsers.IParser;
import com.epam.xmlapp.parsers.ParsersStorage;

import static com.epam.xmlapp.constantdata.Constants.*;

import org.apache.log4j.Logger;


/**
 * Servlet implementation class ServletController
 * @author Andrei_Ilyin1
 */

public final class ServletController extends HttpServlet {
    /**
     * Constant for the serial version UID.
     */
    private static final long serialVersionUID = 10L;
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(ServletController.class);
    
    /** 
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {}

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * Gets request and transfer control to method 
     * @see processRequest(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * Gets request and transfer control to method 
     * @see processRequest(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	processRequest(request, response);
    }
    
    /**
     * Gets the product XML parser that matches the request parameters, 
     * parses the XML file using the parser, 
     * adds list of Category objects to the request as parameter 
     * and forwards the request and response to the page 
     * that must be the result of the request.
     * @param request - the request
     * @param response - the response
     * @throws ServletException if occurs
     * @throws IOException if occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
    	ConfigurationManager manager = ConfigurationManager.getInstance();
    	IParser parser = ParsersStorage.getParser(request);
    	String page = null;
    
    	    	if (parser != null) {
            try {
            	List<Category> categories = parser.parse(
            			manager.getProperty(PRODUCT_XML_PATH));
            	request.setAttribute(LIST_OF_CATEGORIES_ATTRIBUTE, categories);
            	page = manager.getProperty(RESULT_JSP);
            } catch (ParserException e) {
                logger.error(e);
                request.setAttribute(EXCEPTION_TEXT_ATTRIBUTE, e.toString());
                page = manager.getProperty(ERROR_JSP);
            }
    	} else {
    		page = manager.getProperty(INDEX_JSP);
    	}
        request.getRequestDispatcher(page).forward(request, response);
    }
    
}
