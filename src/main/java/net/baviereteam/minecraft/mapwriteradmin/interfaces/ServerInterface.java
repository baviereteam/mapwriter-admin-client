package net.baviereteam.minecraft.mapwriteradmin.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.baviereteam.minecraft.mapwriteradmin.ToolBag;
import net.baviereteam.minecraft.mapwriteradmin.domain.Server;
import net.baviereteam.minecraft.mapwriteradmin.json.OperationResult;

public class ServerInterface {
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
	
	// Returns a list of all the servers
	public String list() {
		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", this.getMasterKey());
		
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
				"server/list", parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				sb.append("Execution success.\n");
				
				List<Server> servers = (List<Server>) result.getResultingObject();
				if(servers == null) {
					sb.append("No results.");
				}
				
				else {
					sb.append(servers.size() + " results:\n");
					
					for(Server server : servers) {
						sb.append(server.toString());
						sb.append("\n");
					}
				}
			}
			
			// Operation failed on server
			else {
				sb.append("Execution failed.\n");
				sb.append("Server answered: ");
				sb.append(result.getErrorMessage());
			}
			
			return sb.toString();
		}
		
		catch(Exception e)  {
			return e.getMessage();
		}
	}
	
	public String create() {
		return "";
	}
	
	public String select(int serverId) {
		return "";
	}
	
	public String rename() {
		return "";
	}
	
	public String changekey() {
		return "";
	}
	
	public String delete() {
		return "";
	}
}
