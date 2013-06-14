/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.presentation.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
/**
 * Class for a delete form that is automatically populated on the server side with data 
 * selected from a form on the client side.
 * @author Andrei_Ilyin1
 */
public class DeleteForm extends ActionForm {
    
    private static final long serialVersionUID = 41L;
    /**
     * Selected news IDs.
     */
    private int[] selectedNewsIds;  
    /*
     * Constant contains text for error logger message.
     */
    private static final String FORM_REQUIRED_ERROR = 
	    "Error of delete form. at least one field must be selected";
    /**
     * Logger object
     */
    private static final Logger logger = Logger.getLogger(DeleteForm.class);
    
    /**
     * Returns the selected news IDs this news form.
     * 
     * @return the selected news IDs
     */
    public int[] getSelectedNewsIds() {
	return selectedNewsIds;	
    }

    /**
     * Sets the selected news IDs for this news form.
     * 
     * @param selectedNewsIds - the selected news IDs
     */
    public void setSelectedNewsIds(int[] selectedNewsIds) {
	this.selectedNewsIds = selectedNewsIds;	
    }

    /** (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ActionErrors validate(ActionMapping mapping,
	    HttpServletRequest request) {
	ActionErrors actionErrors = new ActionErrors();
	
	if (getSelectedNewsIds() == null) {
	    actionErrors.add("delete", new ActionMessage(
		    "message.delete.nothingChecked"));	
	    logger.info(FORM_REQUIRED_ERROR);
	}	
	return actionErrors;
    }
    
    

}
