package org.foodbutler.webhook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import org.foodbutler.db.DBHelper;
import org.tinylog.Logger;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.Capability;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.SelectionList;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.ListSelectListItem;
import com.google.api.services.actions_fulfillment.v2.model.OptionInfo;
import com.google.api.services.actions_fulfillment.v2.model.SimpleResponse;

@Path("ga")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GoogleWebhook extends DialogflowApp{
	@POST
	@Path("webhook")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String fromGA(@Context HttpHeaders headers, String requestJson) {
		Logger.trace("got the request from google: {}", requestJson);
		String response;
		try {
			response = handleRequest(requestJson, getHeadersMap(headers.getRequestHeaders())).get();
			Logger.trace("returning to google", response);
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
	  if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
	    return responseBuilder
	        .add("Sorry, try ths on a screen device or select the phone surface in the simulator.")
	        .add("Which response would you like to see next?")
	        .build();
	  }

	  responseBuilder
	      .add("This is a list example.")
	      .add(
	          new SelectionList()
	              .setTitle("List Title")
	              .setItems(
	                  Arrays.asList(
	                      new ListSelectListItem()
	                          .setTitle("Title of First List Item")
	                          .setDescription("This is a description of a list item.")
	                          .setImage(
	                              new Image()
	                                  .setUrl(
	                                      "https://storage.googleapis.com/actionsresources/logo_assistant_2x_64dp.png")
	                                  .setAccessibilityText("Image alternate text"))
	                          .setOptionInfo(
	                              new OptionInfo()
	                                  .setKey("SELECTION_KEY_ONE")),
	                      new ListSelectListItem()
	                          .setTitle("Google Home")
	                          .setDescription(
	                              "Google Home is a voice-activated speaker powered by the Google Assistant.")
	                          .setImage(
	                              new Image()
	                                  .setUrl(
	                                      "https://storage.googleapis.com/actionsresources/logo_assistant_2x_64dp.png")
	                                  .setAccessibilityText("Google Home"))
	                          .setOptionInfo(
	                              new OptionInfo()
	                                  .setSynonyms(
	                                      Arrays.asList(
	                                          "Google Home Assistant",
	                                          "Assistant on the Google Home"))
	                                  .setKey("SELECTION_KEY_GOOGLE_HOME")),
	                      new ListSelectListItem()
	                          .setTitle("Google Pixel")
	                          .setDescription("Pixel. Phone by Google.")
	                          .setImage(
	                              new Image()
	                                  .setUrl(
	                                      "https://storage.googleapis.com/actionsresources/logo_assistant_2x_64dp.png")
	                                  .setAccessibilityText("Google Pixel"))
	                          .setOptionInfo(
	                              new OptionInfo()
	                                  .setKey("SELECTION_KEY_GOOGLE_PIXEL")))));
	  return responseBuilder.build();
	}
	@ForIntent("List - OPTION")
	public ActionResponse listSelected(ActionRequest request) {
	  ResponseBuilder responseBuilder = getResponseBuilder(request);
	  String selectedItem = request.getSelectedOption();
	  String response;

	  if (selectedItem.equals("SELECTION_KEY_ONE")) {
	    response = "You selected the first item";
	  } else if (selectedItem.equals("SELECTION_KEY_GOOGLE_HOME")) {
	    response = "You selected the Google Home!";
	  } else if (selectedItem.equals("SELECTION_KEY_GOOGLE_PIXEL")) {
	    response = "You selected the Google Pixel!";
	  } else {
	    response = "You did not select a valid item";
	  }
	  return responseBuilder.add(response).add("Which response would you like to see next?").build();
	}
}








