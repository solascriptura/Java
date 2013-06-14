/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.xmlapp.parsers.stax;


/**
 * Interface for a StAX parser. The interface contains 
 * methods of processing StAX parsing events.
 * @author Andrei_Ilyin1
 */
interface IStAXParser {
    /**
     * Processes starting of the element.
     * @param reader - the XMLStreamReader object
     */
    public void startElement(StreamReaderWrapper reader);
    /**
     * Processes the text of the element.
     * @param reader - the XMLStreamReader object
     */
    public void characters(StreamReaderWrapper reader);
    /**
     * Processes ending of the element.
     * @param reader - the XMLStreamReader object
     */
    public void endElement(StreamReaderWrapper reader);
    /**
     * Processes starting of the document.
     * @param reader - the XMLStreamReader object
     */
    public void startDocument(StreamReaderWrapper reader);
    /**
     * Processes ending of the document.
     * @param reader - the XMLStreamReader object
     */
    public void endDocument(StreamReaderWrapper reader);
}