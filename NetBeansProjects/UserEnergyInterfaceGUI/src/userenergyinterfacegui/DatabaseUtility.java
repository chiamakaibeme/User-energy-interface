/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userenergyinterfacegui;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author chiamakaibeme
 */
public class DatabaseUtility {

    private final String MYSQL_URL;
    final String DB_URL;
    private Connection sqlConnection, dbConnection;
    private Statement statement;
    private final String dbCreateSQL;
    private final String USER_NAME;
    private final String PASSWORD;
    private final String TABLE_ENTRY_QRY;
    private final String TABLE_RESULT_QRY;

    private final String SELECT_ENTRY_QRY;
    private final String SELECT_RESULT_QRY;
    private DatabaseMetaData dbmd;

    public DatabaseUtility() {
        MYSQL_URL = "jdbc:mysql://localhost:3306";
        DB_URL = MYSQL_URL + "/Users";

        //initialise MySql username and password
        USER_NAME = "ibemec";
        PASSWORD = "ibemec01";

        statement = null;

        //sql query to create database
        dbCreateSQL = "CREATE DATABASE Users";

        //sql queries to create Tables
        TABLE_ENTRY_QRY = "CREATE TABLE NAME "
                + "(EntryID INTEGER not NULL AUTO_INCREMENT,"
                + "Name VARCHAR(30),"
                + "Height INTEGER,"
                + "Gender VARCHAR(10),"
                + "AgeGroup VARCHAR(20),"
                + "PALVAL VARCHAR(60),"
                + "PRIMARY KEY (EntryID) )";
        TABLE_RESULT_QRY = "CREATE TABLE HEIGHT "
                + "(ResultID INTEGER not NULL, "
                + "PalDesc VARCHAR(60),"
                + "AvgWeight INTEGER,"
                + "PRIMARY KEY (ResultID),"
                + "FOREIGN KEY (EntryID) REFERENCES ENTRY(EntryID) "
                + "ON UPDATE CASCADE ON DELETE CASCADE )";
        
        SELECT_ENTRY_QRY = "SELECT * FROM ENTRY";
        SELECT_RESULT_QRY = "SELECT * FROM RESULT";

    }
    public boolean createDBtables(){
        boolean dbExists = false,
                tabEntryExist = false,
                tabResultExist = false,
                dbCreated = false;
        String databaseName = "";
        
        //Register MySql database driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            return false;
	}
      	System.out.println("MySQL JDBC Driver Registered!"); 
        
        //connect to MySql ;
        try {
            sqlConnection = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASSWORD);
            statement = sqlConnection.createStatement();
            System.out.println("Database Connected");
        } catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		return false;
	}
        
        //check whether the databse exists.
       try {
             //get the list of databases
             ResultSet dbaseData = sqlConnection.getMetaData().getCatalogs();
                         
             //iterate each catalog in the ResultSet 
             while (dbaseData.next()) {
             // Get the database name, which is at position 1
                      databaseName = dbaseData.getString(1);
                      // Test print of database names, can be removed
                     // System.out.printf("%s ",databaseName);  
                      if (databaseName.equalsIgnoreCase("Users") )
                         dbExists = true;
             }
             if (! dbExists)  //if database doesn't exist create database executing the query.
             {
                statement.executeUpdate(dbCreateSQL);
             }
             if (sqlConnection != null)
                sqlConnection.close();  //close the existing connection to connect to MySql
             //connect to Users database
             dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);                                 
             statement = dbConnection.createStatement();
             dbmd= dbConnection.getMetaData();
              // loop through the list of tables if the tables are already created
             ResultSet rSet = dbmd.getTables(null, null, "%", null);
             while (rSet.next()) {
                     if((rSet.getString(2).equalsIgnoreCase("ENTRY")))
                       tabEntryExist = true;
                     if((rSet.getString(2).equalsIgnoreCase("RESULT")))
                       tabResultExist = true;
             }
             
             //if any of the tables doesn't exist create table executing the query
             if (!tabEntryExist)
                statement.executeUpdate(TABLE_ENTRY_QRY);                                   
             if (!tabResultExist)
                statement.executeUpdate(TABLE_RESULT_QRY);
   
//             if (tblAuthorISBNExist)   
//                return true;    

       } catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
             System.out.println("SQLException: " + e.getMessage());
             System.out.println("SQLState: " + e.getSQLState());
		return false;
	}
      return true; 
    }
}
