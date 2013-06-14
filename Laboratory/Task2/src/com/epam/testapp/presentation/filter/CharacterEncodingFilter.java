/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.presentation.filter;

import static com.epam.testapp.constantdata.Constants.*;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Class filter sets the request character encoding to UTF-8.
 * @author Andrei_Ilyin1
 */
public class CharacterEncodingFilter implements Filter{
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(CharacterEncodingFilter.class);
    /**
     * Character encoding.
     */
    private String encoding;

    /**
     * Method initializes the character encoding of the filter from the initial
     * parameter value.
     * 
     * @param config - the filter configuration
     * 
     * @throws ServletException - if ServletException occurs
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
	encoding = config.getInitParameter(ENCODING_PARAMETER);	
    }

    /**
     * Method sets the request character encoding to UTF-8.
     * 
     * @param request - the request
     * @param response - the response
     * @param chain - the filter chain
     * 
     * @throws IOException - if IOException occurs
     * 
     * @throws ServletException - if ServletException occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {
	String requestEncoding = request.getCharacterEncoding();

	if (!encoding.equals(requestEncoding)) {
	    request.setCharacterEncoding(encoding);
	    
	    if(logger.isEnabledFor(Level.INFO)){
		logger.info(CHANGE_LANGUAGE_FILTER + encoding);
	    }	
	    
	}
	chain.doFilter(request, response);	
    }

    /**
     * Method sets the character encoding of the filter to null value.
     */
    @Override
    public void destroy() {
	encoding = null;	
    }

}
