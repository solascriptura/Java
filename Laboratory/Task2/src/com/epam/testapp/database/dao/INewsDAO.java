/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.database.dao;

import java.util.List;

import com.epam.testapp.exceptions.DAOException;
import com.epam.testapp.model.News;

/**
 * The INewsDAO interface description:
 * Interface INewsDAO contains the methods which are
 * necessary for work with a database.
 * @author Andrei_Ilyin1
 */
public interface INewsDAO {
    
    /**
     * Returns the list of all news sorting by news date descending. Returns the
     * empty list if no news is found.
     * 
     * @return the news list
     * @throws DAOException if DAO layer exceptions occur
     */
    public List<News> getList() throws DAOException;

    /**
     * Fetches the news with the specified ID and returns it. Returns null if 
     * news with the specified ID is not found
     * 
     * @param id - the news id
     * 
     * @return the news list
     * @throws DAOException if DAO layer exceptions occur 
     */
    public News fetchById(int id) throws DAOException;

    /**
     * Saves the news into database.
     * 
     * @param news - the news object
     * 
     * @throws DAOException if DAO layer exceptions occur
     */
    public void save(News news) throws DAOException;

    /**
     * Removes the news with the specified IDs.
     * 
     * @param id - the news ID
     * 
     * @throws DAOException if DAO layer exceptions occur
     */
    public void remove(int[] id) throws DAOException;
    
}
