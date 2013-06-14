/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.presentation.form;


import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.epam.testapp.managers.resource.ResourceManager;
import com.epam.testapp.managers.resource.ResourceManagerProvider;
import com.epam.testapp.model.News;
import static com.epam.testapp.constantdata.Constants.*;


/**
 * Class for a News form that is automatically populated on the server side with data 
 * entered from a form on the client side.
 * 
 * @author Andrei_Ilyin1
 */
public class NewsForm extends ActionForm {
    /*
     * Constant for the serial version UID.
     */
    private static final long serialVersionUID = 51L;    
    /*
     * News message.
     */
    private News newsMessage = new News();
    /*
     * News date.
     */
    private String newsDate;
    /*
     * Date pattern.
     */
    private String datePattern;
    /*
     * News list.
     */
    private List<News> newsList;    
    /*
     * Cancel forward name.
     */
    private String cancelForwardName;
    /*
     * Selected language.
     */
    private String language;
    /*
     * Constant contains text for error logger message.
     */
    private static final String DATE_PATTERN_ERROR = "Error of news form. Date pattern isn't valid.";
    /*
     * Logger object
     */
    private static final Logger logger = Logger.getLogger(NewsForm.class);

    
    /**
     * Returns the news message of this news form.
     * 
     * @return the news message
     */
    public News getNewsMessage() {
	return newsMessage;	
    }

    /**
     * Sets the news message for this news form.
     * 
     * @param newsMessage - the news message
     */
    public void setNewsMessage(News newsMessage) {
	this.newsMessage = newsMessage;	
    }

    /**
     * Returns the news date of this news form.
     * 
     * @return the news date
     */
    public String getNewsDate() {
	return newsDate;	
    }

    /**
     * Sets the news date for this news form.
     * 
     * @param newsDate - the news date
     * 
     * @throws ParseException if ParseException occurs
     */
    public void setNewsDate(String newsDate) {
	this.newsDate = newsDate;
    }

    /**
     * Returns the date pattern of this news form.
     * 
     * @return the date pattern
     */
    public String getDatePattern() {
	return datePattern;	
    }

    /**
     * Sets the date pattern for this news form.
     * 
     * @param datePattern - the date pattern
     */
    public void setDatePattern(String datePattern) {
	this.datePattern = datePattern;	
    }

    /**
     * Returns the news list of this news form.
     * 
     * @return the news list
     */
    public List<News> getNewsList() {
	return newsList;	
    }

    /**
     * Sets the news list for this news form.
     * 
     * @param newsList - the news list
     */
    public void setNewsList(List<News> newsList) {
	this.newsList = newsList;	
    }


    /**
     * Returns the cancel forward name of this news form.
     * 
     * @return the cancel forward name
     */
    public String getCancelForwardName() {
	return cancelForwardName;	
    }

    /**
     * Sets the cancel forward name for this news form.
     * 
     * @param cancelForwardName - the cancel forward name
     */
    public void setCancelForwardName(String cancelForwardName) {
	this.cancelForwardName = cancelForwardName;	
    }
    /**
     * Returns the selected language of this language form.
     * 
     * @return the selected language
     */
    public String getLanguage() {
	return language;	
    }

    /**
     * Sets the selected language for this language form.
     * 
     * @param language - the selected language
     */
    public void setLanguage(String language) {
	this.language = language;	
    }
    
    /** (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ActionErrors validate(ActionMapping mapping,
	    HttpServletRequest request) {

	ActionErrors actionErrors = new ActionErrors();
	checkRequired(actionErrors, newsMessage);
	checkMaxLength(actionErrors, newsMessage);	
	return actionErrors;
    }
    
    /**
     * Method checks the text fields that required to be filled   
     * and adds errors to page in case they aren't filled
     * @param errors
     * @param newsMessage
     */
    private void checkRequired(ActionErrors errors, News newsMessage) {

	String title = newsMessage.getTitle();
	String brief = newsMessage.getBrief();
	String content = newsMessage.getContent();

	if ("".equals(title)) {
		errors.add("title", new ActionMessage(
			"errors.add.required.title"));
	}
	if ("".equals(brief)) {
		errors.add("brief", new ActionMessage(
			"errors.add.required.brief"));
	}
	if ("".equals(content)) {
		errors.add("content", new ActionMessage(
			"errors.add.required.content"));
	}	
	if ("".equals(getNewsDate())) {
		errors.add("date", new ActionMessage(
			"errors.add.required.date"));
	}
	else {
	    ResourceManager manager = ResourceManagerProvider.getInstance()
		    .getResourceManager(language.toUpperCase());	   
	    String pattern = manager.getProperty(DATEPATTERN);
	    if(!getNewsDate().matches(pattern)){
		errors.add("date", new ActionMessage("message.wrong.date"));
		logger.info(DATE_PATTERN_ERROR);
	    }		
	}
	
    }
    
    /**
     * Method checks the text fields max length 
     * and adds errors to page in case it is exceeded 
     * @param errors
     * @param newsMessage
     */
    private void checkMaxLength(ActionErrors errors, News newsMessage) {

	String title = newsMessage.getTitle();
	String brief = newsMessage.getBrief();
	String content = newsMessage.getContent();

	if (title != null && title.length() > TITLE_MAXLENGTH) {
		errors.add("title", new ActionMessage(
			"errors.add.maxLength.title"));
	}
	if (brief != null && brief.length() > BRIEF_MAXLENGTH) {
		errors.add("brief", new ActionMessage(
			"errors.add.maxLength.brief"));
	}
	if (content != null && content.length() > CONTENT_MAXLENGTH) {
		errors.add("content", new ActionMessage(
			"errors.add.maxLength.content"));
	}
	
    }
    
}
