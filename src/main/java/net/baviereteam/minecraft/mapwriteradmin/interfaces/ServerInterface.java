package net.baviereteam.minecraft.mapwriteradmin.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import net.baviereteam.minecraft.mapwriteradmin.ToolBag;
import net.baviereteam.minecraft.mapwriteradmin.domain.Server;
import net.baviereteam.minecraft.mapwriteradmin.json.OperationResult;

public class ServerInterface {
	private boolean deleteForReal = false;
	private Gson gson = new Gson();
	
	
	
	// Returns a list of all the servers
	public String list() {
		// Reset deletion security
		deleteForReal = false;
		
		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());
		
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
				"server/list", parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				sb.append("Execution success.\n");
				
				List servers = (List) result.getResultingObject(Server.class);
				if(servers == null) {
					sb.append("No results.");
				}
				
				else {
					sb.append(servers.size() + " results:\n");
					
					for(Object item : servers) {
						// Converting the inner object
						Server server = (Server) item;
						
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
	
	// Create a minecraft server
	public String create(String name) {
		// Reset deletion security
		deleteForReal = false;
				
		// Parameters are the name of the server and the master key.
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());
		parameters.put("name", name);
		
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
				"server/create", parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				sb.append("Execution success.\n");
				
				// Converting the inner object
				Server server = (Server) result.getResultingObject(Server.class);
				if(server == null) {
					sb.append("No results.");
				}
				
				else {
					sb.append("Result:\n");
					sb.append(server.toString());
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
	
	// Select (locally) a Server ID to work on.
	public String select(int serverId) {
		// Reset deletion security
		deleteForReal = false;
		
		ToolBag.getInstance().setSelectedServerId(serverId);
		return "Selected server with ID " + serverId;
	}
	
	// Rename the selected server
	public String rename(String name) {
		// Reset deletion security
		deleteForReal = false;
		
		if(ToolBag.getInstance().getSelectedServerId() == 0) {
			return "No server selected !";
		}
		
		// Parameters are the master key and the new name
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());
		parameters.put("name", name);
		
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
				"server/rename/" + ToolBag.getInstance().getSelectedServerId() , parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				sb.append("Execution success.\n");
				
				// Converting the inner object
				Server server = (Server) result.getResultingObject(Server.class);
				if(server == null) {
					sb.append("No results.");
				}
				
				else {
					sb.append("Result:\n");
					sb.append(server.toString());
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

	// Change the key of the selected server
	public String changekey() {
		// Reset deletion security
		deleteForReal = false;

		if(ToolBag.getInstance().getSelectedServerId() == 0) {
			return "No server selected !";
		}
		
		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());
		
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
				"server/changekey/" + ToolBag.getInstance().getSelectedServerId() , parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				sb.append("Execution success.\n");
				
				// Converting the inner object
				Server server = (Server) result.getResultingObject(Server.class);
				if(server == null) {
					sb.append("No results.");
				}
				
				else {
					sb.append("Result:\n");
					sb.append(server.toString());
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
	
	// Delete the selected server
	public String delete() {
		if(!deleteForReal) {
			deleteForReal = true;
			return "Please type the command again to confirm deletion of this server. There is no coming back !";
		}
		
		if(ToolBag.getInstance().getSelectedServerId() == 0) {
			return "No server selected !";
		}
		
		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());
		
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
				"server/delete/" + ToolBag.getInstance().getSelectedServerId() , parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				sb.append("Execution success.\n");
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
}
