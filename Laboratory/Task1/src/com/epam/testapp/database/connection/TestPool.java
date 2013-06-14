/**
 * 
 */
package com.epam.testapp.database.connection;

import java.sql.Connection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.epam.testapp.exceptions.DatabaseException;
import com.epam.testapp.database.connection.ConnectionPool;


/**
 * Class TestPool which tests the Connection Pool by using 
 * its connections. 
 * It contains methods which try to get multiply connections independently, 
 * waits some times and close them after a specified time interval. 
 * @author Andrei_Ilyin1
 */
class TestPool {
    /**
     * Connection pool instance
     */
    static ConnectionPool pool = new ConnectionPool();
    /**
     * Get logger
     */
    private static final Logger logger = Logger.getLogger(TestPool.class);
    

    /**
     * The Inner class SampleThread which contains methods
     * to create and start an independent thread.
     *
     * @author Andrei_Ilyin1
     */
    private static class SampleThread extends Thread {
	/**
	 * Thread number
	 */
	private int number;
	/**
	 * wait interval
	 */
	private long interval;
	/**
	 * Pointer to connection which was obtained from pool
	 */
	private Connection connection;
	
	/**
	 * Constructor with a parameter
	 */
	private SampleThread(long interval){
	    this.interval = interval;    
	}
	    
	private void setNumber(int i){
	    this.number = i;
	}

	public void run(){
	    if(logger.isEnabledFor(Level.INFO)){
		logger.info("Thread number "+number+" started!");
	    }	
		
	    try {
		sleep(interval);	
		    
		connection = pool.getConnection();
		    
		sleep(interval);
		
		if(logger.isEnabledFor(Level.INFO)){   
		    logger.info("Thread number "+number+" finished!");
		}
	            
		pool.returnConnection(connection);	
	            
	    } catch (DatabaseException | InterruptedException e) {
		e.printStackTrace();
	    }
	        
	}
	    
    }
    
    /**
     * Main method which try to create  multiply pool connections.
     * @param args
     * @throws DatabaseException
     */
    public static void main(String[] args) throws DatabaseException {
	
	pool.setDriverName("oracle.jdbc.driver.OracleDriver");
	pool.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
	pool.setUser("Test");
	pool.setPassword("test");
	pool.setLocale("en");
	pool.setMaxConnections(20);
	pool.setMaxIdleConnections(10);
	
	pool.init();
	
	if(logger.isEnabledFor(Level.INFO)) {
	    logger.info("Maximum connections " +
	    		"are : " + pool.getMaxConnections());
	    logger.info("Proactive connections " +
	    		"are : " + pool.getMaxIdleConnections());
	}
		
	for (int i = 1; i < 16; i++){
	    SampleThread t = new SampleThread(900);
	    t.setNumber(i);
	    t.start(); 
	}	
	
	try {
	    Thread.sleep(4000);
	} catch (InterruptedException e) {
	    logger.info("The Main_Thread got interrupted!");
	}
	
	pool.destroy();
	logger.info("The Main_Thread completes its execution... Bye!");
    }

}
