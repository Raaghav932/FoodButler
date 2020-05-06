package org.foodbutler.db;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tinylog.Logger;
//jdbc:postgresql://localhost:5432/FoodButler
//postgres://liyyjhbihezjus:e511477a502a343f368e5309b7c85722ee5c33e9068d65ff0c7cbfee872db85b@ec2-18-235-20-228.compute-1.amazonaws.com:5432/deiakrethhr73
public class DBHelper {
//	private final String url = "jec2-18-235-20-228.compute-1.amazonaws.com";
//	private final String user = "liyyjhbihezjus";
//	private final String password = "e511477a502a343f368e5309b7c85722ee5c33e9068d65ff0c7cbfee872db85b";
	
    private Connection connect() {
        Connection conn = null;
        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            Logger.info("Connected to the PostgreSQL server successfully.");
            return connection;
        } catch (SQLException e) {
            Logger.error(e);
        } catch (URISyntaxException e) {
        	Logger.error(e);
        }

        return conn;
    }
    public String getClosestStore(String name){
    	Logger.info("in the getClosestStore method");
    	Connection conn = connect();
    	Logger.info("connected to database");
    	final String sqlSelect = "select name \n" + 
    			"from stores\n" + 
    			"where\n" + 
    			"	distance = (\n" + 
    			"		select\n" + 
    			"		MIN(distance)\n" + 
    			"		from stores, stock\n" + 
    			"		where stock.name = (?)\n" + 
    			"		AND\n" + 
    			"		stock.name_of_store = stores.name\n" + 
    			"	)\n";
    	try {
    		PreparedStatement ps = conn.prepareStatement(sqlSelect);
    		ps.setString(1, name);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			return rs.getString("name");
    		}
    		return ps.enquoteLiteral(sqlSelect);
    	}catch(SQLException e){
    		Logger.warn(e);
    		return "There is a problem "+e;
    	}catch(Exception e) {
    		Logger.warn(e);
    		return "There is a problem "+e;
    	}
    }
    public ArrayList<String> getStores() {
    	Logger.info("In the get stores methid");
    	ArrayList<String> stores = new ArrayList<String>();
    	Connection conn = connect();
    	final String sqlSelect = "select name from stores";
    	try {
    		PreparedStatement ps = conn.prepareStatement(sqlSelect);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			stores.add(rs.getString("name"));
    		}
    		return stores;
    	}catch(SQLException e){
    		Logger.warn(e);
    		return stores;
    	}catch(Exception e) {
    		Logger.warn(e);
    		return stores;
    	}
    }
    
    
    public ArrayList<String> getStoresFromDistance(){
    	Logger.info("In the get stores from distance method");
    	ArrayList<String> stores = new ArrayList<String>();
    	Connection conn = connect();
    	final String sqlSelect = "select name from stores where distance < 3";
    	try {
    		PreparedStatement ps = conn.prepareStatement(sqlSelect);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			stores.add(rs.getString("name"));
    		}
    		return stores;
    	}catch(SQLException e){
    		Logger.warn(e);
    		return stores;
    	}catch(Exception e) {
    		Logger.warn(e);
    		return stores;
    	}
    }
    
    
    
    
    
    
    
    
    
}
