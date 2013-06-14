/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.database.dao;

import static com.epam.testapp.constantdata.Constants.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.epam.testapp.database.connection.ConnectionPool;
import com.epam.testapp.exceptions.DAOException;
import com.epam.testapp.exceptions.DatabaseException;
import com.epam.testapp.model.News;
import com.epam.testapp.util.DataFormatter;

/**
 * The NewsDAO class description:
 * Class NewsDAO contains methods that bind
 * the News object with the DB
 * @author Andrei_Ilyin1
 */
public final class NewsDAO implements INewsDAO {
    /**
     * Constant for the data access exception text.
     */
    private static final String DATA_ACCESS_EXCEPTION_TEXT = 
	    "It's impossible to get a connection from the connection pool, "
	    + "execute a query, build a bean object, close the statement "
	    + "or return the connection back";
    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(NewsDAO.class);
    /**
     * Constant for get all news SQL query.
     */
    private static final String SQL_GET_ALL_NEWS = 
	    "SELECT ID, TITLE, NEWS_DATE, BRIEF, CONTENT "
	    + "FROM NEWS ORDER BY NEWS_DATE DESC";
    /**
     * Constant for get news by ID SQL query.
     */
    private static final String SQL_GET_NEWS_BY_ID = 
	    "SELECT ID, TITLE, NEWS_DATE, BRIEF, CONTENT "
	    + "FROM NEWS WHERE ID = ?";
    /**
     * Constant for insert news SQL query.
     */
    private static final String SQL_INSERT_NEWS = 
	    "INSERT INTO NEWS (TITLE, NEWS_DATE, BRIEF, CONTENT) "
	    + "VALUES (?, ?, ?, ?)";
    /**
     * Constant for update news SQL query.
     */
    private static final String SQL_UPDATE_NEWS = 
	    "UPDATE NEWS SET TITLE = ?, NEWS_DATE = ?, BRIEF = ?, CONTENT = ? "
	    + "WHERE ID = ?";
    /**
     * Constant for delete news by ID SQL query.
     */
    private static final String SQL_DELETE_NEWS_BY_ID = 
	    "DELETE FROM NEWS WHERE ID IN (";   
    /**
     * DB connection pool.
     */
    private ConnectionPool connectionPool;

    /**
     * Constructs a new News data access object with the DB connection
     * pool.
     */
    public NewsDAO() {
    }

    /**
     * Method gets news list from Database
     * @see com.epam.testapp.database.dao.INewsDAO#getList()
     */
    public List<News> getList() throws DAOException {
	logger.info("Getting list of all news from the DB");
	News news = null;
	List<News> newsList = new ArrayList<News>();
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	try {
	    connection = connectionPool.getConnection();
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(SQL_GET_ALL_NEWS);
	    while (resultSet.next()) {
		news = buildNews(resultSet);
		newsList.add(news);
	    }	
	} catch (SQLException | DatabaseException e) {
	    logger.error(e);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	} finally {
	    releaseResources(statement, connection);
	}
	return newsList;
    }

    /**
     * Method fetches the news with the specified ID
     * @see com.epam.testapp.database.dao.INewsDAO#fetchById(int)
     */
    public News fetchById(int id) throws DAOException {
	 if(logger.isEnabledFor(Level.INFO)){
	     logger.info("Getting the news from the DB by its ID. " +
	     		"id: " + id);    
	 }
	
	News news = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    connection = connectionPool.getConnection();
	    preparedStatement = connection.prepareStatement(SQL_GET_NEWS_BY_ID);
	    preparedStatement.setInt(INDEX_ONE, id);
	    resultSet = preparedStatement.executeQuery();
	
	    if (resultSet.next()) {
		news = buildNews(resultSet);
	    }	    
	} catch (SQLException | DatabaseException e) {
	    logger.error(e);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	} finally {
	    releaseResources(preparedStatement, connection);
	}
	return news;
    }

    /**
     * Method saves the news into database 
     * @see com.epam.testapp.database.dao.INewsDAO#save(com.epam.testapp.model.News)
     */
    public void save(News news) throws DAOException {
	 if(logger.isEnabledFor(Level.INFO)){
	     logger.info("Saving the news in the DB. " +
	     		"news: " + news.toString());    
	 }	 
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	try {
	    connection = connectionPool.getConnection();
	    if (news.getId() == VALUE_ZERO) {
		preparedStatement = connection.prepareStatement(SQL_INSERT_NEWS);
	    } else {
		preparedStatement = connection.prepareStatement(SQL_UPDATE_NEWS);
		preparedStatement.setInt(INDEX_FIVE, news.getId());
	    }
	    Date date = news.getDate();
	    java.sql.Date dbDate = DataFormatter.toDBDate(date);
	    preparedStatement.setString(INDEX_ONE, news.getTitle());
	    preparedStatement.setDate(INDEX_TWO, dbDate);
	    preparedStatement.setString(INDEX_THREE, news.getBrief());
	    preparedStatement.setString(INDEX_FOUR, news.getContent());
	    preparedStatement.executeUpdate();	
	} catch (SQLException | DatabaseException e) {
	    logger.error(e);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	} finally {
	    releaseResources(preparedStatement, connection);
	}

    }

    /**
     * Method removes news from database
     * @see com.epam.testapp.database.dao.INewsDAO#remove(int[])
     */
    public void remove(int[] id) throws DAOException {
	 if(logger.isEnabledFor(Level.INFO)){
	     logger.info("Removing the news from the DB. " +
	     		"IDs: " + Arrays.toString(id));    
	 }
	int iMax = id.length-1;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	StringBuilder query = new StringBuilder();	
	query.append(SQL_DELETE_NEWS_BY_ID);	

	for (int i = 0; i < iMax ; i++) {
	    query.append(" ?,");	    	    
	}
	query.append(" ? )");
	
	try {	    
	    connection = connectionPool.getConnection();
	    preparedStatement = 
		    connection.prepareStatement(query.toString());	    	    
	    for(int i = 0; i < id.length; i ++) {
		preparedStatement.setInt(i+1, id[i]);		
	    }	    
	    preparedStatement.executeUpdate();	    	
	} catch (SQLException | DatabaseException e) {
	    logger.error(e);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);	
	} finally {
	    releaseResources(preparedStatement, connection);
	}
    }
    
    /**
     * Method builds the News object from the result set current row and returns it.
     * 
     * @param resultSet - the result set with the positioned cursor
     * @return the built News object
     * @throws DAOException - if DAOException occurs
     */
    private News buildNews(ResultSet resultSet) throws DAOException {
	News news = null;	
	try {
	    int id = resultSet.getInt(INDEX_ONE);
	    String title = resultSet.getString(INDEX_TWO);
	    Date date = resultSet.getDate(INDEX_THREE);
	    String brief = resultSet.getString(INDEX_FOUR);
	    String content = resultSet.getString(INDEX_FIVE);
	    news = new News(id, title, date, brief, content);
	    if(logger.isEnabledFor(Level.INFO)){
		logger.info("News from DB: " + news.toString());
	    }
	
	} catch (SQLException e) {
	    logger.error(e);
	    throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	}	
	return news;
    }
	
    /**
     * Method closes the statement and returns the connection back 
     * to the connection pool.
     * 
     * @param statement - the statement
     * @param connection - the connection
     * @throws DAOException - if DAOException occurs
     */
    private void releaseResources(Statement statement, Connection connection)
	    throws DAOException {
	
	if (statement != null) {	   
	    try {
		statement.close();
	    } catch (SQLException e) {
		logger.error(e);
		throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	    }
	}	
	if (connection != null) {
	    try {
		connectionPool.returnConnection(connection);
	    } catch (DatabaseException e) {
		logger.error(e);
		throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
	    }
	}
    }
	
    /**
     * Method returns the DB connection pool of this news data access object.
     * @return the DB connection pool
     */
    public ConnectionPool getConnectionPool() {
	return connectionPool;
    }

    /**
     * Method sets the DB connection pool for this news data access object.
     * @param connectionPool - the DB connection pool
     */
    public void setConnectionPool(ConnectionPool connectionPool) {
	this.connectionPool = connectionPool;
    }

}
