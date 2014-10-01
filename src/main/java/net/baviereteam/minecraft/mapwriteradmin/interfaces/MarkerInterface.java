package net.baviereteam.minecraft.mapwriteradmin.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.baviereteam.minecraft.mapwriteradmin.ToolBag;
import net.baviereteam.minecraft.mapwriteradmin.domain.Marker;
import net.baviereteam.minecraft.mapwriteradmin.domain.Server;
import net.baviereteam.minecraft.mapwriteradmin.json.OperationResult;

public class MarkerInterface {
	private long selectedMarkerId = 0;
	private boolean deleteForReal = false;
	private Gson gson = new Gson();
	private String lastGetError = "";
	
	public String list() {
		if (ToolBag.getInstance().getSelectedServerId() == 0) {
			return "No server selected !";
		}

		// Reset deletion security
		deleteForReal = false;

		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());

		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
					"markers/" + ToolBag.getInstance().getSelectedServerId() + "/list", parameters);

			StringBuilder sb = new StringBuilder();

			// Operation success
			if (result.getResult()) {
				sb.append("Execution success.\n");

				List markers = (List) result.getResultingObject(Marker.class);
				if (markers == null) {
					sb.append("No results.");
				}

				else {
					sb.append(markers.size() + " results:\n");

					for (Object item : markers) {
						// Converting the inner object
						Marker marker = (Marker) item;

						sb.append(marker.toString());
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

		catch (Exception e) {
			return e.getMessage();
		}
	}

	public List listAsMarkerList() throws Exception {
		if (ToolBag.getInstance().getSelectedServerId() == 0) {
			throw new Exception ("No server selected !");
		}

		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());

		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
					"markers/" + ToolBag.getInstance().getSelectedServerId() + "/list", parameters);

			StringBuilder sb = new StringBuilder();

			// Operation success
			if (result.getResult()) {
				sb.append("Execution success.\n");

				List markers = (List) result.getResultingObject(Marker.class);
				return markers;
			}

			// Operation failed on server
			else {
				sb.append("Execution failed.\n");
				sb.append("Server answered: ");
				sb.append(result.getErrorMessage());
			}

			throw new Exception(sb.toString());
		}

		catch (Exception e) {
			throw e;
		}
	}
	
	
	public String select(long markerId) {
		selectedMarkerId = markerId;
		return "Selected marker with ID " + markerId;
	}
	
	private Marker get() {
		this.lastGetError = "";
		
		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());
		
		Marker marker = null;
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
				"markers/get/" + selectedMarkerId, parameters);
		
			// Operation success
			if(result.getResult()) {
				marker = (Marker) result.getResultingObject(Marker.class);
			}
		}
		
		catch(Exception e)  {
			this.lastGetError = e.getMessage();
		}
		
		return marker;
	}
	
	public String add(int color, int dimension, String group, String name, int x, int y, int z, String picture) {
		// Reset deletion security
		deleteForReal = false;
			
		if (ToolBag.getInstance().getSelectedServerId() == 0) {
			return "No server selected !";
		}
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());
		parameters.put("color", ((Integer)color).toString() );
		parameters.put("dimension", ((Integer)dimension).toString() );
		parameters.put("group", group);											
		parameters.put("name", name);
		parameters.put("x", ((Integer)x).toString() );
		parameters.put("y", ((Integer)y).toString() );
		parameters.put("z", ((Integer)z).toString() );
		
		if(picture != null) {
			parameters.put("picture", picture);
		}
		
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
					"markers/" + ToolBag.getInstance().getSelectedServerId() + "/add", parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				sb.append("Execution success.\n");
				
				// Converting the inner object
				Marker marker = (Marker) result.getResultingObject(Marker.class);
				if(marker == null) {
					sb.append("No results.");
				}
				
				else {
					sb.append("Result:\n");
					sb.append(marker.toString());
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
	
	public String edit(Integer color, Integer dimension, String group, String name, Integer x, Integer y, Integer z, String picture) {
		// Reset deletion security
		deleteForReal = false;
		
		if(selectedMarkerId == 0) {
			return "No marker selected !";
		}
		
		// Extract the previous marker
		Marker marker = this.get();
		if(marker == null) {
			return "Could not extract the selected marker: " + this.lastGetError;
		}
		
		// Map properties. Null parameters will keep the previous values. All others will be replaced.
		if(color != null) {
			marker.setColor(color);
		}
		
		if(dimension != null) {
			marker.setDimension(dimension);
		}
		
		if(group != null) {
			marker.setGroup(group);
		}
		
		if(name != null) {
			marker.setName(name);
		}
		
		if(picture != null) {
			marker.setPicture(picture);
		}
		else {
			// Avoid null here (would crash)
			marker.setPicture("");
		}
		
		if(x != null) {
			marker.setX(x);
		}
		
		if(y != null) {
			marker.setY(y);
		}
		
		if(z != null) {
			marker.setZ(z);
		}
		
		// Business starts now
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());
		parameters.put("color", ((Integer) marker.getColor()).toString() );
		parameters.put("dimension", ((Integer) marker.getDimension()).toString() );
		parameters.put("group", marker.getGroup());											
		parameters.put("name", marker.getName());
		parameters.put("x", ((Integer) marker.getX()).toString() );
		parameters.put("y", ((Integer) marker.getY()).toString() );
		parameters.put("z", ((Integer) marker.getZ()).toString() );
		parameters.put("picture", marker.getPicture());
		
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
					"markers/edit/" + selectedMarkerId, parameters);
		
			StringBuilder sb = new StringBuilder();
			
			// Operation success
			if(result.getResult()) {
				sb.append("Execution success.\n");
				
				// Converting the inner object
				Marker newMarker = (Marker) result.getResultingObject(Marker.class);
				if(newMarker == null) {
					sb.append("No results.");
				}
				
				else {
					sb.append("Result:\n");
					sb.append(newMarker.toString());
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
	
	public String delete() {
		if(!deleteForReal) {
			deleteForReal = true;
			return "Please type the command again to confirm deletion of this marker. There is no coming back !";
		}
		
		if(selectedMarkerId == 0) {
			return "No marker selected !";
		}
		
		// Only parameter required is the master key
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", ToolBag.getInstance().getUsedKey());
		
		try {
			// Execute the server command
			OperationResult result = ToolBag.getInstance().getWebConnector().execute(
				"markers/delete/" + selectedMarkerId , parameters);
		
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
