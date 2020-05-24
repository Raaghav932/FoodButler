package org.foodbutler;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.foodbutler.db.DBHelper;





@Path("butler")
public class FoodButler {
	@GET
	@Path("search")
	public String getResults(@QueryParam("search")String search){
		DBHelper result = new DBHelper();
		return result.getClosestStore(search,40.15, -105.14,"80503");
	}
}
