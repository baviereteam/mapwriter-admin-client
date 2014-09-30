package net.baviereteam.minecraft.mapwriteradmin;

import net.baviereteam.minecraft.mapwriteradmin.connector.WebConnector;
import net.baviereteam.minecraft.mapwriteradmin.interfaces.ServerInterface;

public class ToolBag {
	private static ToolBag instance = null;
	public static ToolBag getInstance() {
		if(instance == null) {
			instance = new ToolBag();
		}
		
		return instance;
	}
	
	private WebConnector webConnector = new WebConnector();
	private ServerInterface serverInterface = new ServerInterface();
	
	public WebConnector getWebConnector() {
		return webConnector;
	}
	public ServerInterface getServerInterface() {
		return serverInterface;
	}
}
