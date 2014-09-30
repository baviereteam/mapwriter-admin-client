package net.baviereteam.minecraft.mapwriteradmin.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class JsonConverter {
	Gson gson = new Gson();
	
	public Object getAsObject(JsonElement element, Class<?> expectedType) {
		Object output = null;
		
		if(element.isJsonArray()) {
			output = this.getAsList(element, expectedType);
		}
		
		else if(element.isJsonObject()) {
			output = this.getAsSingle(element, expectedType);
		}
		
		// should never happen in this case
		else if(element.isJsonPrimitive()) {
			output = element.getAsJsonPrimitive().getAsString();
		}
		
		return output;
	}
	
	private List getAsList(JsonElement source, Class<?> expectedType) {
		JsonArray array = source.getAsJsonArray();
		List result = new ArrayList();
		
		for(JsonElement element : array) {
			result.add( getAsSingle(element, expectedType) );
		}
		
		return result;
	}
	
	private Object getAsSingle(JsonElement element, Class<?> expectedType) {
		return gson.fromJson(element, expectedType);
	}
}
