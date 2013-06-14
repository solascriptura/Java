package com.epam.testapp.database.dao.jpa;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.epam.testapp.database.dao.INewsDAO;
import com.epam.testapp.exceptions.DAOException;
import com.epam.testapp.model.News;
import com.epam.testapp.util.DataFormatter;

import static com.epam.testapp.constantdata.Constants.*;

public class JPANewsDAO implements INewsDAO {
    /**
     * Constant for the data access exception text.
     */
    protected static final String DATA_ACCESS_EXCEPTION_TEXT = 
    		"JPANewsDAO." +
    		"It's impossible to get a entity manager from " +
    		"the entity manager factory, execute a Java Persistence query, " +
    		"build bean objects or close the entity manager";
    /**
     * Logger object.
     */
    private static final Logger logger = 
	    Logger.getLogger(JPANewsDAO.class);

    /**
     * Entity manager factory object.
     */
    private static final EntityManagerFactory factory = 
	    Persistence.createEntityManagerFactory(NEWS_PERSISTENCE_UNIT);
	
    /**
     * Method gets news list from Database
     * @see com.epam.testapp.database.dao.INewsDAO#getList()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<News> getList() throws DAOException {
	logger.info("JPANewsDAO. Getting list of all news from the DB");
	List<News> newsList = null;
	Query query = null;
	EntityManager manager = factory.createEntityManager();
	try {
	    query = manager.createNamedQuery(NAMED_GET_ALL_NEWS);
	    newsList = (List<News>) query.getResultList();
	} catch (Exception e) {
	    logger.error(e);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	} finally {
	    releaseResource(manager);
	}
	return newsList;
    }
	
    /**
     * Method fetches the news with the specified ID
     * @see com.epam.testapp.database.dao.INewsDAO#fetchById(int)
     */
    @Override
    public News fetchById(int id) throws DAOException {
	if(logger.isEnabledFor(Level.INFO)){ 
	    logger.info("JPANewsDAO. Getting the news from the DB by its ID. " +
	    		"id: " + id);
	}
	News news = null;
	EntityManager manager = factory.createEntityManager();
	try {
	    news = (News) manager.find(News.class, id);
	} catch (Exception e) {
	    logger.error(e);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	} finally {
	    releaseResource(manager);
	}
	return news;
    }
	
    /**
     * Method saves the news into database 
     * @see com.epam.testapp.database.dao.INewsDAO#save(com.epam.testapp.model.News)
     */
    @Override
    public void save(News news) throws DAOException {
	if(logger.isEnabledFor(Level.INFO)){ 
	    logger.info("JPANewsDAO. Saving the news in the DB. " +
	    		"news: " + news.toString());
	}
	EntityManager manager = factory.createEntityManager();
	EntityTransaction tx = null;
	try {
	    tx = manager.getTransaction();
	    tx.begin();
	    manager.merge(news);
	    tx.commit();
	} catch (Exception e) {
	    logger.error(e);
	    cancelDBUpdate(tx);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	} finally {
	    releaseResource(manager);
	}
    }
	
    /**
     * Method removes news from database
     * @see com.epam.testapp.database.dao.INewsDAO#remove(int[])
     */
    @Override
    public void remove(int[] ids) throws DAOException {
	if(logger.isEnabledFor(Level.INFO)){ 
	    logger.info("JPANewsDAO. " +
		    "Removing the news from the DB. " +
		    "IDs: " + Arrays.toString(ids));
	}
	List<Integer> idsList = null;
	EntityManager manager = factory.createEntityManager();
	Query query = null;
	EntityTransaction tx = null;
	try {
	    tx = manager.getTransaction();
	    tx.begin();
	    if(ids.length == 1) {
		News news = manager.find(News.class, ids[0]);
		if(news != null) {
		    manager.remove(news);
		}
	    } else {
		idsList = DataFormatter.toList(ids);
		query = manager.createNamedQuery(NAMED_DELETE_NEWS_BY_IDS);
		query.setParameter(IDS_LIST_PARAMETER, idsList);
		query.executeUpdate();
	    }
	    tx.commit();
	} catch (Exception e) {
	    logger.error(e);
	    cancelDBUpdate(tx);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	} finally {
	    releaseResource(manager);
	}
    }
	
    /**
     * Rolls back the entity transaction if it in not null.
     * @param tx - the entity transaction
     * @throws DataAccessException - if DataAccessException occurs
     */
    private void cancelDBUpdate(EntityTransaction tx) 
	    throws DAOException {
	if (tx != null) {
	    try {
		tx.rollback();
	    } catch (HibernateException e) {
		logger.error(e);
		throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	    }
	}
    }
	
    /**
     * Closes the entity manager if it in not null.
     * @param manager - the entity manager
     * @throws DataAccessException - if DataAccessException occurs
     */
    private void releaseResource(EntityManager manager) 
	    throws DAOException {
	if (manager != null) {
	    try {
		manager.close();
	    } catch (Exception e) {
		logger.error(e);
		throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	    }
	}
    }
}