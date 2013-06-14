/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.model;

import java.util.Date;

import com.epam.testapp.util.DataFormatter;


/**
 * The News class description:
 * Class News contains methods and fields
 * for create and change a news objects
 * @author Andrei_Ilyin1
 */
public class News{
    /**
     * News ID.
     */
    private int id;
    /**
     * News title.
     */
    private String title;
    /**
     * News date.
     */
    private Date date;
    /**
     * News brief.
     */
    private String brief;
    /**
     * News content.
     */
    private String content;

    /**
     * Constructs a new news with null parameters.
     */
    public News() {
	
    }

    /**
     * Creates a new news object with the specified news title, news date, news
     * brief and news content.
     * 
     * @param title - the title
     * @param date - the date
     * @param brief - the brief
     * @param content - the content
     */
    public News(String title, Date date, String brief, String content) {
	setTitle(title);
	setDate(date);
	setBrief(brief);
	setContent(content);	
    }

    /**
     * Constructs a new news object with the specified news ID, news title, news date,
     * news brief and news content.
     * 
     * @param id - the ID
     * @param title - the title
     * @param date - the date
     * @param brief - the brief
     * @param content - the content
     */
    public News(int id, String title, Date date, String brief, String content) {
	this(title, date, brief, content);
	setId(id);
	
    }

    /**
     * Returns the ID of this news.
     * 
     * @return the ID
     */
    public int getId() {
	return id;
	
    }

    /**
     * Sets the ID for this news.
     * 
     * @param id - the ID
     */
    public void setId(int id) {
	this.id = id;
	
    }

    /**
     * Returns the title of this news.
     * 
     * @return the title
     */
    public String getTitle() {
	return title;
	
    }

    /**
     * Sets the title for this news.
     * 
     * @param title
     *            - the title
     */
    public void setTitle(String title) {
	this.title = title;
	
    }

    /**
     * Returns the news date.
     * 
     * @return the date
     */
    public Date getDate() {
	return date;
	
    }

    /**
     * Sets the news date.
     * 
     * @param date - the news date
     */
    public void setDate(Date date) {
	this.date = date;
	
    }

    /**
     * Returns the news brief.
     * 
     * @return the brief
     */
    public String getBrief() {
	return brief;
	
    }

    /**
     * Sets the news brief.
     * 
     * @param brief - the news brief
     */
    public void setBrief(String brief) {
	this.brief = brief;
	
    }

    /**
     * Returns the news content.
     * 
     * @return the content
     */
    public String getContent() {
	return content;
	
    }

    /**
     * Sets the news content.
     * 
     * @param content - the news content
     */
    public void setContent(String content) {
	this.content = content;
	
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	
	if (this == obj) {
	    return true;    
	}
	
	if (obj == null) {
	    return false;    
	}	
	
	if (obj instanceof News) {
	    News news = (News) obj;
	    return  equalFields(this.getId(), news.getId() ) && 
		    equalFields(this.getTitle(), news.getTitle()) && 
		    equalFields(this.getDate(), news.getDate()) && 
		    equalFields(this.getBrief(), news.getBrief());
	    } else {
		return false;
		
	    }
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	int hash = 31;
	hash += this.id + 
		(this.title == null ? 0 : this.title.hashCode()) + 
		(this.date == null ? 0 : this.date.hashCode()) + 
		(this.brief == null ? 0 : this.brief.hashCode());
	return hash;	
    }
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return DataFormatter.format("%s@id:%d, title:%s, date:%s", 
		getClass().getName(), getId(), getTitle(), getDate());	
    }

    /**
     * Determines if the specified string fields are equal. 
     * The null or empty fields are equal.
     * 
     * @param field1 - the first string field value
     * @param field2 - the second string field value
     * 
     * @return true if the fields are equal; false otherwise
     */  
    private boolean equalFields(String field1, String field2) {
	
	if (isEmpty(field1)) {
	    return isEmpty(field2);	    
	} else {
	    return field1.equals(field2);
	    
	}
	
    }

    /**
     * Determines if the specified integer fields are equal.
     * 
     * @param field1 - the first integer field value
     * @param field2 - the second integer field value
     * 
     * @return true if the fields are equal; false otherwise
     */
    private boolean equalFields(Integer field1, Integer field2) {
	return field1.equals(field2);
	
    }

    /**
     * Determines if the specified date fields are equal.
     * 
     * @param field1 - the first date field value
     * @param field2 - the second date field value
     * 
     * @return true if the fields are equal; false otherwise
     */
    private boolean equalFields(java.util.Date field1, java.util.Date field2) {
	if (field1 == null) {
	    return (field2 == null);	    
	} else {
	    return field1.equals(field2);	    
	}
	
    }

    /**
     * Determines if the specified string is empty or null.
     * 
     * @param string - the string
     * 
     * @return true if the string is empty or null; false otherwise
     */  
    private boolean isEmpty(String string) {
	return (string.length() == 0);
	
    }    
    
}

