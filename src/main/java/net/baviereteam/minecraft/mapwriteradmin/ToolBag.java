package net.baviereteam.minecraft.mapwriteradmin;

import net.baviereteam.minecraft.mapwriteradmin.connector.WebConnector;
import net.baviereteam.minecraft.mapwriteradmin.interfaces.MarkerInterface;
import net.baviereteam.minecraft.mapwriteradmin.interfaces.ServerInterface;
import net.baviereteam.minecraft.mapwriteradmin.json.JsonConverter;

public class ToolBag {
	private static ToolBag instance = null;
	public static ToolBag getInstance() {
		if(instance == null) {
			instance = new ToolBag();
		}
		
		return instance;
	}
	
	private String masterKey = "";
	private int selectedServerId = 0;
	
	public String getMasterKey() {
		return masterKey;
	}
	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}
	
	public int getSelectedServerId() {
		return selectedServerId;
	}
	public void setSelectedServerId(int selectedServerId) {
		this.selectedServerId = selectedServerId;
	}
	
	private WebConnector webConnector = new WebConnector();
	private ServerInterface serverInterface = new ServerInterface();
	private MarkerInterface markerInterface = new MarkerInterface();
	private JsonConverter jsonConverter = new JsonConverter();
	
	public WebConnector getWebConnector() {
		return webConnector;
	}
	public ServerInterface getServerInterface() {
		return serverInterface;
	}
	public MarkerInterface getMarkerInterface() {
		return markerInterface;
	}
	public JsonConverter getJsonConverter() {
		return jsonConverter;
	}
}
