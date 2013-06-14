/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.parsers;

import java.util.List;

import com.epam.xmlapp.exceptions.ParserException;
import com.epam.xmlapp.model.Category;

/**
 * Interface for a product XML parser. Product XML parsers 
 * are used for parsing XML files contained info about shop products.
 * @author Andrei_Ilyin1
 */
public interface IParser {
    public List<Category> parse(String parseFile) throws ParserException;

}
