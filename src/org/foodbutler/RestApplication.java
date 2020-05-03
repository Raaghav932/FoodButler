package org.foodbutler;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/food")
public class RestApplication extends Application {

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<String,Object>();
		
		properties.put("jersey.config.server.provider.packages", "org.foodbutler");
		properties.put("jersey.config.server.provider.classnames","org.glassfish.jersey.media.multipart.MultiPartFeature");
		
		return properties;
	}

	
}
