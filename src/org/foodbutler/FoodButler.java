package org.foodbutler;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


@Path("butler")
public class FoodButler {
	@GET
	@Path("search")
	public int getResults(@QueryParam("search")String search){
		return 0;
	}
}
