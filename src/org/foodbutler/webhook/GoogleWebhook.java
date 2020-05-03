package org.foodbutler.webhook;

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

import org.tinylog.Logger;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
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
}








