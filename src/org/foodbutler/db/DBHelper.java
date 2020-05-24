package org.foodbutler.db;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.foodbutler.HttpClient;
import org.foodbutler.StoreInfo;
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
            Logger.info(dbUri);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
            Logger.info(username);
            Logger.info(password);
            Logger.info(dbUrl);
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
    public String getClosestStore(String name, double uLat, double uLong){
    	uLat = uLat + 0;
    	uLong = uLong + 0;
    	Logger.info("in the getClosestStore method");
    	Connection conn = connect();
    	Logger.info("connected to database");
    	final String sqlSelect = "select name \n" + 
    			"		from stores, stock\n" + 
    			"		where stock.name = (?)\n" + 
    			"		AND\n" + 
    			"		stock.name_of_store = stores.name\n" + 
    			"	)\n";
    	try {
    		PreparedStatement ps = conn.prepareStatement(sqlSelect);
    		ps.setString(1, name);
    		ResultSet rs = ps.executeQuery();
    		HttpClient client = new HttpClient();
    		double lowestDistance = 1000;
    		String lowestName = "Couldn't Find anything";
    		while(rs.next()) {
    			ArrayList<Double> distance = client.sendGet(rs.getString("name"));
    			DistanceCalculator calc = new DistanceCalculator();
    			double dist = calc.distance(uLat, uLong, distance.get(0), distance.get(1));
    			if (dist < lowestDistance)
    			{
    				lowestDistance = dist;
    				lowestName = rs.getString("name");
    			}
    		}
    		return lowestName;
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
    
    
    public ArrayList<StoreInfo> getStoresFromDistance(){
    	Logger.info("In the get stores from distance method");
    	ArrayList<StoreInfo> stores = new ArrayList<StoreInfo>();
    	int count = 0;
    	Connection conn = connect();
    	final String sqlSelect = "select name,address,phonenumber,image from stores where distance < 6";
    	try {
    		PreparedStatement ps = conn.prepareStatement(sqlSelect);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			StoreInfo store = new StoreInfo(rs.getString("name"),rs.getString("address"),rs.getString("phonenumber"),rs.getString("image"));
    			stores.add(store);
    			count++;
    		}
    		Logger.info(count);
    		return stores;
    	}catch(SQLException e){
    		Logger.warn(e);
    		return stores;
    	}catch(Exception e) {
    		Logger.warn(e);
    		return stores;
    	}
    	
    }
    public String findSelection(String selection){
		Logger.info("in the findSelection method");
		Connection conn = connect();
		final String sqlSelect = "select phonenumber from stores where name = (?)";
		try {
    		PreparedStatement ps = conn.prepareStatement(sqlSelect);
    		ps.setString(1, selection);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			return rs.getString("phonenumber");
    		}
	}catch(SQLException e){
		Logger.warn(e);
		return "There was an error";
	}catch(Exception e) {
		Logger.warn(e);
		return "There was an error";
	}
		return "Couldn't find any matches";
    }
    
    
    
    
    
    
    
    
    
    
}
