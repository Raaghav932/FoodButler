package org.foodbutler.db;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tinylog.Logger;
//jdbc:postgresql://localhost:5432/FoodButler
//postgres://liyyjhbihezjus:e511477a502a343f368e5309b7c85722ee5c33e9068d65ff0c7cbfee872db85b@ec2-18-235-20-228.compute-1.amazonaws.com:5432/deiakrethhr73
public class DBHelper {
	private final String url = "jdbc:postgresql://localhost:5432/FoodButler";
	private final String user = "butler";
	private final String password = "foodbutler";
	
//    private Connection connect() {
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(url, user, password);
//            Logger.info("Connected to the PostgreSQL server successfully.");
//        } catch (SQLException e) {
//            Logger.error(e);
//        }
//
//        return conn;
//    }
	private static Connection getConnection() throws URISyntaxException, SQLException {
	    String dbUrl = System.getenv("jdbc:postgresql://localhost:5432/FoodButler");
	    return DriverManager.getConnection(dbUrl);
	}
    public String getClosestStore(String name) throws URISyntaxException, SQLException{
    	Logger.info("in the getClosestStore method");
    	Connection conn = getConnection();
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
}
