/**
 * Copyright (C) 2013 EPAM Systems
 */
package com.epam.testapp.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.epam.testapp.exceptions.DatabaseException;

/**
 * Class contains constructor and methods which provide for 
 * the creation ConnectionPool object  and the location connections for their further use.
 * @author Andrei_Ilyin1
 */
public final class ConnectionPool {    
    /**
     * Constant for the JDBC connection exception text.
     */
    private static final String DB_CONNECTION_EXCEPTION_TEXT = 
	    "Oracle JDBC Driver error!";   
    /**
     * Logger object.
     */   
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);
    /**
     * Max number of opened connections.
     */
    private int maxConnections;
    /**
     * Max number of idle connections.
     */
    private int maxIdleConnections;
    /**
     * DB connection driver class name.
     */
    private String driverName;
    /**
     * DB connection URL.
     */
    private String url;
    /**
     * DB connection user name.
     */
    private String user;
    /**
     * DB connection password.
     */
    private String password;
    /**
     * DB connection locale.
     */
    private String locale;
    /**
     * Reentrant for synchronizing of getting / returning connections.
     */
    private final ReentrantLock lock = new ReentrantLock(Boolean.TRUE);    
    /**
     * Semaphore for connections access synchronizing.
     */
    private Semaphore semaphore;
    /**
     * Rented connections thread-safe list.
     */
    private CopyOnWriteArrayList<RentedConnection> connections;
    
    /**
     * Constructs a new database connection pool with null parameters.
     */
    public ConnectionPool() {
	
    }
    
    /**
     * Method loads the connection driver class.
     * 
     * @throws DBConnectionException if DBConnectionException occurs
     */
    private void loadDriverClass() throws DatabaseException {
	try {
	    Class.forName(driverName);
	    } catch (ClassNotFoundException e) {
		logger.error(e);
		throw new DatabaseException(DB_CONNECTION_EXCEPTION_TEXT, e);		
	    }	
    }

    /**
     * Method creates a new rented connection using the connection URL, the user name
     * and the password.
     * 
     * @return a newly-created rented connection
     * @throws DBConnectionException if DBConnectionException occurs
     */
    private RentedConnection createNewConnection()throws DatabaseException {
	try {
	    Locale.setDefault(new Locale(this.locale));
	    RentedConnection rc = new RentedConnection(DriverManager.getConnection(url, user, password));
	    //One connection created! DB locale set as this.locale
	    return rc;	    
	} catch (SQLException e) {
	    logger.error(e);
	    throw new DatabaseException(DB_CONNECTION_EXCEPTION_TEXT, e);	    
	}	
    }

    /**
     * Method searches for an available rented connection in this connection pool and
     * returns it. Returns null if no available rented connection found.
     * 
     * @return an available rented connection if any available was found; 
     * null otherwise
     */
    private RentedConnection findAvailableConnection() {	
	for (RentedConnection connection : connections) {
	    if (connection.isAvailable()) {
		return connection;		
	    }	    
	}
	return null;	
    }

    /**
     * Method counts currently available connections in this connection pool and
     * returns the number of them.
     * 
     * @return the number of available connections
     */
    private int getAvailableConnectionsNumber() {
	int number = 0;	
	for (RentedConnection connection : connections) {
	    if (connection.isAvailable()) {
		number++;		
	    }	    
	}
	return number;	
    }

    /**
     * Method registers the connection driver and initializes the semaphore with the
     * maximum number of opened connections and the connections list with the
     * maximum number of idle connections.
     * 
     * @throws DBConnectionException if DBConnectionException occurs
     */
    public void init() throws DatabaseException {
	loadDriverClass();
	semaphore = new Semaphore(maxConnections, true);
	connections = new CopyOnWriteArrayList<RentedConnection>();	
	for (int i = 0; i < maxIdleConnections; i++) {
	    connections.add(createNewConnection());  
	}	
    }

    /**
     * Method acquires an available connection from this connection pool or creates a
     * new connection in the pool if no idle connections and number of opened
     * connections is less than the max number of them.
     * 
     * @return the connection
     * @throws DBConnectionException if DBConnectionException occurs
     */
    public Connection getConnection() throws DatabaseException {
	try {
	    semaphore.acquire();	    
	} catch (InterruptedException e) {
	    logger.error(e);
	    throw new DatabaseException(DB_CONNECTION_EXCEPTION_TEXT, e);	    
	}
	lock.lock();	
	RentedConnection connection = findAvailableConnection();
	if (connection != null) {
	    connection.setBusy();
	    //One connection obtained from pool
	}	
	lock.unlock();		
	if (connection == null) {
	     connection = createNewConnection();
	     connection.setBusy();
	     connections.add(connection);
	     //Pool Increased, new Connection is created!	     
	} 	
	return connection;
    }

    /**
     * Method returns the connection back to this connection pool.
     * 
     * @param connection - the connection
     * @throws DBConnectionException if DBConnectionException occurs
     */
    public void returnConnection(Connection connection) throws DatabaseException {	
	lock.lock();	
	int available = getAvailableConnectionsNumber();
	
	if (available < maxIdleConnections){
	    ((RentedConnection) connection).setAvailable();
	    //One connection marked as available...		    
	}	
	lock.unlock(); 
	    
	if (available >= maxIdleConnections) {
	    try {
		connection.close();
		} catch (SQLException e) {
		    logger.error(e);
		    throw new DatabaseException(DB_CONNECTION_EXCEPTION_TEXT, e);
		}		
	    
	    connections.remove(connection);
	    //Connection removed!		
	}      		
	semaphore.release();	
    }

    /**
     * Method destroys the Connection pool by closing and removing all its connections
     * 
     * @throws DBConnectionException if DBConnectionException occurs
     */
    public void destroy() throws DatabaseException {
	try {
	    for (RentedConnection connection : connections) {
		connection.close();
		connections.remove(connection);
	    }
	} catch (SQLException e) {
	    logger.error(e);
	    throw new DatabaseException(DB_CONNECTION_EXCEPTION_TEXT, e);	    
	}
	logger.info("Connection pool is destroyed...");	
    }

    /**
     * Method returns the max number of opened connections of this connection pool.
     * 
     * @return the max number of opened connections
     */
    public int getMaxConnections() {
	return maxConnections;	
    }

    /**
     * Method sets the max number of opened connections for this connection pool.
     * 
     * @param maxConnections - the max number of opened connections
     */
    public void setMaxConnections(int maxConnections) {
	this.maxConnections = maxConnections;	
    }

    /**
     * Method returns the max number of idle connections of this connection pool.
     * 
     * @return the max number of idle connections
     */
    public int getMaxIdleConnections() {
	return maxIdleConnections;	
    }

    /**
     * Method sets the max number of idle connections for this connection pool.
     * 
     * @param maxIdleConnections - the max number of idle connections
     */
    public void setMaxIdleConnections(int maxIdleConnections) {
	this.maxIdleConnections = maxIdleConnections;	
    }

    /**
     * Method returns the database connection driver class name of the
     * connection pool.
     * 
     * @return the database connection driver class name
     */
    public String getDriverName() {
	return driverName;	
    }

    /**
     * Method sets the database connection driver class name for the connection pool.
     * 
     * @param maxIdleConnections - the database connection driver class name
     */
    public void setDriverName(String driverName) {
	this.driverName = driverName;	
    }

    /**
     * Method returns the database connection URL of the connection pool.
     * 
     * @return the database connection URL
     */
    public String getUrl() {
	return url;	
    }

    /**
     * Method sets the database connection URL for the connection pool.
     * 
     * @param url - the database connection URL
     */
    public void setUrl(String url) {
	this.url = url;	
    }

    /**
     * Method returns the database connection user name of the connection pool.
     * 
     * @return the database connection user name
     */
    public String getUser() {
	return user;	
    }

    /**
     * Method sets the database connection user name for the connection pool.
     * 
     * @param user - the database connection user name
     */
    public void setUser(String user) {
	this.user = user;	
    }

    /**
     * Method returns the database connection password of the connection pool.
     * 
     * @return the database connection password
     */
    public String getPassword() {
	return password;	
    }

    /**
     * Method sets the database connection password for the connection pool.
     * 
     * @param password - the database connection password
     */
    public void setPassword(String password) {
	this.password = password;	
    }
    
    /**
     * Method sets locale for database connection string
     * 
     * @return
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Method gets locale for database connection string
     * 
     * @param locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

}
