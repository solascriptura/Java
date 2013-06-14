/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.presentation.action;

import static com.epam.testapp.constantdata.Constants.*;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.epam.testapp.database.dao.INewsDAO;
import com.epam.testapp.model.News;
import com.epam.testapp.presentation.form.DeleteForm;
import com.epam.testapp.presentation.form.NewsForm;
import com.epam.testapp.util.DataFormatter;


/**
 * Action Class which contains multiple 
 * independent actions for each function.
 * The class extends MappingDispatchAction class and
 * contains action methods for navigating over the news,
 * editing news, adding and deleting news.
 * 
 * @author Andrei_Ilyin1
 */
public final class NewsAction extends MappingDispatchAction{
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(NewsAction.class);
    /*
     * Constant contains change language text.
     */
    private static final String CHANGE_LANGUAGE = "Language was changed to: ";
    /**
     * News data access object.
     */    
    private INewsDAO newsDAO;

    /**
     * Method sets the language of the application localization to the default value if
     * no language was selected in the session before.
     * 
     * @param mapping - the action mapping
     * @param form - the action form
     * @param request - the request
     * @param response - the response
     * 
     * @return the ActionForward object
     * 
     * @throws Exception if any exception occurs
     */
    public ActionForward init(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
	NewsForm newsForm = (NewsForm) form;
	String language = newsForm.getLanguage();
	if (language == null || language.isEmpty()) {	    
	    language = DEFAULT_LANGUAGE;
	    newsForm.setLanguage(language);
	    logger.info(INIT);
	    logger.info(LANGUAGE_DEFAULT);	    
	}	
	Locale locale = new Locale(language);
	setLocale(request, locale);
	return mapping.findForward(SUCCESS_FORWARD_PATH);	
    }

    /**
     * Method gets the list of all stored news and sets this list for the form.
     * 
     * @param mapping - the action mapping
     * @param form - the action form
     * @param request - the request
     * @param response - the response
     * 
     * @return the ActionForward object
     * 
     * @throws Exception if any exception occurs
     */
    public ActionForward list(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
	List<News> newsList = newsDAO.getList();
	NewsForm newsForm = (NewsForm) form;
	newsForm.setNewsList(newsList);
	logger.info(LIST);
	return mapping.findForward(SUCCESS_FORWARD_PATH);	
    }

    /**
     * Method gets the selected news IDs from the form and removes these news.
     * 
     * @param mapping - the action mapping
     * @param form - the action form
     * @param request - the request
     * @param response - the response
     * 
     * @return the ActionForward object
     * 
     * @throws Exception if any exception occurs
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
	DeleteForm deleteForm = (DeleteForm) form;
	int[] ids = deleteForm .getSelectedNewsIds();
	if (ids != null) {
	    newsDAO.remove(ids);	    
	}
	logger.info(DELETE);
	return mapping.findForward(SUCCESS_FORWARD_PATH);	
    }

    /**
     * Method gets the news message ID from the form and fetches the news by this ID.
     * 
     * @param mapping - the action mapping
     * @param form - the action form
     * @param request - the request
     * @param response - the response
     * 
     * @return the ActionForward object
     * 
     * @throws Exception if any exception occurs
     */
    public ActionForward view(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
	NewsForm newsForm = (NewsForm) form;
	int id = newsForm.getNewsMessage().getId();
	News news = newsDAO.fetchById(id);
	newsForm.setNewsMessage(news);
	return mapping.findForward(SUCCESS_FORWARD_PATH);	
    }

    /**
     * Method sets a new news as the news message for the form.
     * 
     * @param mapping - the action mapping
     * @param form - the action form
     * @param request - the request
     * @param response - the response
     * 
     * @return the ActionForward object
     * 
     * @throws Exception if any exception occurs
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
	NewsForm newsForm = (NewsForm) form;
	News news = new News();
	newsForm.setNewsMessage(news);
	logger.info(ADD);
	return mapping.findForward(SUCCESS_FORWARD_PATH);	
    }

    /**
     * Method gets the news message ID from the form and fetches the news by this ID.
     * 
     * @param mapping - the action mapping
     * @param form - the action form
     * @param request - the request
     * @param response - the response
     * 
     * @return the ActionForward object
     * 
     * @throws Exception if any exception occurs
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
	NewsForm newsForm = (NewsForm) form;
	int id = newsForm.getNewsMessage().getId();
	News news = newsDAO.fetchById(id);
	newsForm.setNewsMessage(news);
	return mapping.findForward(SUCCESS_FORWARD_PATH);	
    }

    /**
     * Method gets the news message from the form and saves this news.
     * 
     * @param mapping - the action mapping
     * @param form - the action form
     * @param request - the request
     * @param response - the response
     * 
     * @return the ActionForward object
     * 
     * @throws Exception if any exception occurs
     */
    public ActionForward save(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
	NewsForm newsForm = (NewsForm) form;	
	Date date = DataFormatter.parseToDate(newsForm.getNewsDate(), newsForm.getDatePattern());
	newsForm.getNewsMessage().setDate(date);
	News news = newsForm.getNewsMessage();
	newsDAO.save(news);
	return mapping.findForward(SUCCESS_FORWARD_PATH);	
    }

    /**
     * Method gets the cancel forward name from the form.
     * 
     * @param mapping - the action mapping
     * @param form - the action form
     * @param request - the request
     * @param response - the response
     * 
     * @return the ActionForward object
     * 
     * @throws Exception if any exception occurs
     */
    public ActionForward cancel(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
	NewsForm newsForm = (NewsForm) form;
	String forwardName = newsForm.getCancelForwardName();
	return mapping.findForward(forwardName);	
    }
    
    /**
     * Method changes the language of the application localization.
     * 
     * @param mapping - the action mapping
     * @param form - the action form
     * @param request - the request
     * @param response - the response
     * 
     * @return the ActionForward object
     * 
     * @throws IOException if IOException occurs
     * @throws ServletException if ServletException occurs
     */
    public ActionForward changeLanguage(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
	NewsForm newsForm = (NewsForm) form;
	String language = newsForm.getLanguage();
	Locale locale = new Locale(language);
	setLocale(request, locale);
	
	if(logger.isEnabledFor(Level.INFO)) {
	    logger.info(CHANGE_LANGUAGE + language);
	}
	
	String path = request.getHeader(REFERRER_HEADER);
	return new ActionForward(path, true);	
    }

    /**
     * Method returns the news data access object of this news.
     * 
     * @return the news data access object
     */
    public INewsDAO getNewsDAO() {
	return newsDAO;	
    }

    /**
     * Method sets the news data access object for this news action.
     * 
     * @param newsDAO - the news data access object
     */
    public void setNewsDAO(INewsDAO newsDAO) {
	this.newsDAO = newsDAO;	
    }

}
