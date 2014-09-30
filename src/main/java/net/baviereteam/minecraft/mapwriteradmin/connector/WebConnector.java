package net.baviereteam.minecraft.mapwriteradmin.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.baviereteam.minecraft.mapwriteradmin.json.OperationResult;

public class WebConnector {
	private String host = "";
	private int port = 0;
	private Gson gson = new Gson();

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public OperationResult execute(String command, Map<String, String> parameters) throws Exception {
		String response = this.query(command, parameters);
		JsonObject json = gson.fromJson(response, JsonObject.class);
		OperationResult result = new OperationResult(json);
		
		return result;
	}

	private String query(String command, Map<String, String> parameters) throws IllegalStateException, Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost request = new HttpPost(this.getHost() + ":" + this.getPort() + "/" + command);
		String responseData = "";

		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().replace(":", "-")));
		}

		request.setEntity(new UrlEncodedFormEntity(postParams));
		CloseableHttpResponse response = httpclient.execute(request);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			responseData = this.getStringFromStream(entity.getContent());
			EntityUtils.consume(entity);
		}
		
		else {
			throw new IOException("Something went wrong: the server returned a " 
				+ response.getStatusLine().getStatusCode() + " code.");
		}
		
		response.close();
		return responseData;
	}
	
	private String getStringFromStream(InputStream stream) throws Exception {
		StringBuilder textBuilder = new StringBuilder();
	    try {
	    	Reader reader = new BufferedReader(
	    		new InputStreamReader(stream, Charset.forName( StandardCharsets.UTF_8.name() ))
	    	);
	        int c = 0;
	        while ((c = reader.read()) != -1) {
	            textBuilder.append((char) c);
	        }
	    }
	    
	    catch(Exception e) {
	    	throw new IOException("Could not extract the request result", e);
	    }
	    	
	    return textBuilder.toString();
	}
}
