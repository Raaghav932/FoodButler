package org.foodbutler.webhook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.foodbutler.HttpClient;
import org.foodbutler.StoreInfo;
import org.foodbutler.db.DBHelper;
import org.tinylog.Logger;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.Capability;
import com.google.actions.api.ConstantsKt;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.Permission;
import com.google.actions.api.response.helperintent.SelectionList;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.ListSelectListItem;
import com.google.api.services.actions_fulfillment.v2.model.OptionInfo;
import com.google.api.services.actions_fulfillment.v2.model.SimpleResponse;
import com.google.api.services.actions_fulfillment.v2.model.Location;

@Path("ga")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GoogleWebhook extends DialogflowApp{
	@POST
	@Path("webhook")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String fromGA(@Context HttpHeaders headers, String requestJson) {
		//Logger.trace("got the request from google: {}", requestJson);
		String response;
		try {
			response = handleRequest(requestJson, getHeadersMap(headers.getRequestHeaders())).get();
			//Logger.trace("returning to google", response);
		} catch (Throwable throwable) {
			Logger.error(throwable);
			response = throwable.getMessage();
		}
		return response;
	}

	private Map<String, String> getHeadersMap(MultivaluedMap<String, String> request) {
		Map<String, String> map = new HashMap<String, String>();

		Set<String> headers = request.keySet();
		for (String header : headers) {
			String value = request.getFirst(header);
			map.put(header, value);

			if (request.get(header).size() > 1)
				Logger.warn("headers missed for {} , {}", header, request.get(header));
		}

		return map;
	}
	@GET
	@Path("test")
	public String test(@QueryParam("test")String test) {
		Logger.info("in the test method");
		return "It worked "+test;
	}
	
	@ForIntent("Default Welcome Intent")
	public ActionResponse welcome(ActionRequest request) {
		ResponseBuilder builder = getResponseBuilder(request);
		
		//Google speach
		SimpleResponse simple = new SimpleResponse();
		simple.setTextToSpeech("Welcome to Raaghav's food butler app.");
		
		
		//Google ui
		BasicCard card = new BasicCard();
		card.setTitle("Food Butler");
		card.setFormattedText("Welcome to Raaghav's food butler app");
		
		
		ActionResponse response = builder.add(simple)
										 .add(card)
										 .build();
		return response;
	}
	
	@ForIntent("GoodBoyIntent")
	public ActionResponse goodBoy(ActionRequest request) {
		ResponseBuilder builder = getResponseBuilder(request);
		
		//Google speach
		SimpleResponse simple = new SimpleResponse();
		simple.setTextToSpeech("Raaghav is a good boy");
		
		
		//Google ui
		BasicCard card = new BasicCard();
		card.setTitle("Good Boy");
		card.setFormattedText("Raaghav is a good boy");
		
		
		ActionResponse response = builder.add(simple)
										 .add(card)
										 .build();
		return response;
	}
	
	@ForIntent("BadBoyIntent")
	public ActionResponse badBoy(ActionRequest request) {
		ResponseBuilder builder = getResponseBuilder(request);
		
		//Google speach
		SimpleResponse simple = new SimpleResponse();
		simple.setTextToSpeech("Voldemort is a bad boy");
		
		
		//Google ui
		BasicCard card = new BasicCard();
		card.setTitle("Bad Boy");
		card.setFormattedText("Voldemort is a bad boy");
		ActionResponse response = builder.add(simple)
										 .add(card)
										 .build();
		return response;
	}
	@ForIntent("GetStoresIntent")
	public ActionResponse listStores(ActionRequest request) {
		String listofstores = "Here are the stores I know ";
		ArrayList<String> arrlist = new ArrayList<String>();
		
		ResponseBuilder builder = getResponseBuilder(request);
		DBHelper helper = new DBHelper();
		arrlist = helper.getStores();
		//Google speach
		SimpleResponse simple = new SimpleResponse();
		for(String store : arrlist) {
			listofstores+=" "+store+" ,";
		}
		simple.setTextToSpeech(listofstores);
		
		
		//Google ui
		BasicCard card = new BasicCard();
		card.setTitle("The stores I know");
		card.setFormattedText(listofstores);
		
		//Set the stuff
		ActionResponse response = builder.add(simple)
				 .add(card)
				 .build();
		return response;
	}
	
	
	@ForIntent("List")
	public ActionResponse list(ActionRequest request) {
	  ResponseBuilder responseBuilder = getResponseBuilder(request);
	  List<ListSelectListItem> storeList = new ArrayList<ListSelectListItem>();
	  
	  DBHelper helper = new DBHelper();
	  ArrayList<StoreInfo> stores = helper.getStoresFromDistance();
	  SelectionList mylist = new SelectionList();
	  
	  if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
	    return responseBuilder
	        .add("Sorry, try ths on a screen device or select the phone surface in the simulator.")
	        .build();
	  }

for(StoreInfo store:stores) {
		  storeList.add(
			new ListSelectListItem()
				.setTitle(store.getName())
				.setDescription(store.getAddress())
				.setImage(
						new Image()
						.setUrl(store.getImage())
						.setAccessibilityText("adsfdsaf"))
				.setOptionInfo(
					new OptionInfo()
						.setKey(store.getName())
						));
		  			}

	responseBuilder
	      .add("Here is a list of the stores that match your criteria")
	      .add(
	          mylist
	              .setTitle("Stores")
	              .setItems(storeList));

	  return responseBuilder.build();
	}
	

	@ForIntent("List - OPTION")
	public ActionResponse listSelected(ActionRequest request) {
	  ResponseBuilder responseBuilder = getResponseBuilder(request);
	  String selectedItem = request.getSelectedOption();
	  String response;
	  DBHelper helper = new DBHelper();
	  response = helper.findSelection(selectedItem);
	  return responseBuilder.add(response).build();
	}
	
	@ForIntent("FindFood")
	public ActionResponse FindFood(ActionRequest request) {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		DBHelper helper = new DBHelper();
		String store = helper.getClosestStore((String) request.getParameter("food"));
		String response = "You can get that at " + store;
		responseBuilder.add(response);
		return responseBuilder.build();
	}
	
	@ForIntent("user_location")
	public ActionResponse user_location(ActionRequest request) {
	  ResponseBuilder responseBuilder = getResponseBuilder(request);
	  String[] permissions = new String[] {ConstantsKt.PERMISSION_NAME};
	  // Location permissions only work for verified users
	  // https://developers.google.com/actions/assistant/guest-users
	  if (request.getUser().getUserVerificationStatus().equals("VERIFIED")) {
	    // Could use PERMISSION_DEVICE_COARSE_LOCATION instead for city, zip code
	    permissions =
	        new String[] {
	          ConstantsKt.PERMISSION_NAME, ConstantsKt.PERMISSION_DEVICE_PRECISE_LOCATION
	        };
	  }
	  responseBuilder
	      .add("PLACEHOLDER")
	      .add(new Permission().setPermissions(permissions));

	  return responseBuilder.build();
	}
	
	@ForIntent("FindStore")
	public ActionResponse FindStore(ActionRequest request) throws Exception {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		Location location = request.getPlace();
		//String name = request.getUser().getProfile().getDisplayName();
		HttpClient client = new HttpClient();
		String store = (String) request.getParameter("store");
		for(int i = 0; i < store.length(); i++){
			if(store.charAt(i) == 32) {
			   String temp = store.substring(0,i-1) + "%20" + store.substring(i+1,store.length());
			   store = temp;
			}
		}
		ArrayList<String> distance = client.sendGet(store);
		responseBuilder.add(distance.get(0));
		responseBuilder.add(distance.get(1));
		if (request.isPermissionGranted()) {
			Logger.info(location);
		    responseBuilder.add("Okay " + ", I see you're at " + location.getFormattedAddress());
		  } else {
		    responseBuilder.add("Looks like I can't get your information");
		  }
		return responseBuilder.build();
	}
}








