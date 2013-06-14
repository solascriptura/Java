package com.epam.testapp.database.dao.hibernate;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import com.epam.testapp.database.dao.INewsDAO;
import com.epam.testapp.exceptions.DAOException;
import com.epam.testapp.model.News;
import com.epam.testapp.util.DataFormatter;


import static com.epam.testapp.constantdata.Constants.*;

public class HibernateNewsDAO implements INewsDAO {
    /**
     * Constant for the data access exception text.
     */
    protected static final String DATA_ACCESS_EXCEPTION_TEXT = 
	    "HibernateNewsDAO. " +
	    "It's impossible to get a connection session from the session " +
	    "factory, execute a dialect query, build bean objects or close" +
	    " the connection session";
    /**
     * Logger object.
     */
    private static final Logger logger = 
	    Logger.getLogger(HibernateNewsDAO.class);
    /**
     * Session factory object.
     */
    private static SessionFactory factory = 
	    new Configuration().configure().buildSessionFactory();
	
    /**
     * Constructs a new Hibernate news data access object 
     * with the default parameters.
     */
    public HibernateNewsDAO() {}
    
    /**
     * Method gets news list from Database
     * @see com.epam.testapp.database.dao.INewsDAO#getList()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<News> getList() throws DAOException {
	logger.info("HibernateNewsDAO. Getting list of all news from the DB");
	List<News> newsList = null;
	Session session = null;
	Query query = null;
	Transaction tx = null;
	try {		    	
	    session = factory.getCurrentSession();
	    tx = session.beginTransaction();
	    query = session.getNamedQuery(NAMED_GET_ALL_NEWS);
	    newsList = (List<News>)query.list();		
	    tx.commit();
	} catch (HibernateException e) {
	    logger.error(e);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
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
	    logger.info("HibernateNewsDAO. " +
	    		"Getting the news from the DB by its ID. id: " + id);
	}	
	News news = null;
	Session session = null;
	Transaction tx = null;
	try {
	    session = factory.getCurrentSession();
	    tx = session.beginTransaction();
	    news = (News) session.get(News.class, id);
	    tx.commit();
	} catch (HibernateException e) {
	    logger.error(e);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
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
	    logger.info("HibernateNewsDAO. " +
	    		"Saving the news in the DB. news: " + news.toString());
	}
	Session session = null;
	Transaction tx = null;
	try {
	    session = factory.getCurrentSession();
	    tx = session.beginTransaction();
	    if (news.getId() == VALUE_ZERO) {
		session.save(news);
	    } else {
		session.update(news);
	    }
	    tx.commit();
	} catch (HibernateException e) {
	    logger.error(e);
	    cancelDBUpdate(tx);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	} 
    }
	
    /**
     * Method removes news from database
     * @see com.epam.testapp.database.dao.INewsDAO#remove(int[])
     */
    @Override
    public void remove(int[] ids) throws DAOException {
	if(logger.isEnabledFor(Level.INFO)) { 
	    logger.info("HibernateNewsDAO. " +
		    "Removing the news from the DB. IDs: " + Arrays.toString(ids));
	}
	Session session = null;
	Query query = null;
	Transaction tx = null;
	List<Integer> idsList = null;
	try {
	    session = factory.getCurrentSession();
	    tx = session.beginTransaction();
	    query = session.getNamedQuery(NAMED_DELETE_NEWS_BY_IDS);
	    idsList = DataFormatter.toList(ids);
	    query.setParameterList(IDS_LIST_PARAMETER, idsList);
	    query.executeUpdate();
	    tx.commit();
	} catch (HibernateException e) {
	    logger.error(e);
	    cancelDBUpdate(tx);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	} 
    }
	
    /**
     * Rolls back the transaction if it isn't null.
     * @param tx - the transaction
     * @throws DataAccessException - if DataAccessException occurs
     */
    private void cancelDBUpdate(Transaction tx) throws DAOException {
	if (tx != null) {
	    try {
		tx.rollback();
	    } catch (HibernateException e) {
		logger.error(e);
		throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	    }
	}
    }    
}
