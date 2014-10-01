package net.baviereteam.minecraft.mapwriteradmin.connector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.baviereteam.minecraft.mapwriteradmin.domain.Marker;

public class MinecraftOverviewerPoiConnector {
	private StringBuilder sb;
	private List<Marker> markers;
	private List<String> groups = new ArrayList<String>();
	
	private void addGroup(String group) {
		if(!groups.contains(group)) {
			groups.add(group);
		}
	}
	
	private void writePois() {
		sb.append("pois = [\n");
		
		for(int i = 0; i < markers.size(); i++) {
			writePoi(markers.get(i));
			
			// If it's not the last POI, add a comma
			if(i < markers.size() - 1) {
				sb.append(",");
			}
			
			sb.append("\n");
		}
		
		sb.append("]\n");
	}
	private void writePoi(Marker marker) {
		// Before we start... record this marker's group to do the filters later.
		this.addGroup(marker.getGroup());
		
		// Let's write now !
		sb.append("\t{\n");

		sb.append( String.format("\t\t'id': '%d',\n", marker.getId()) );
		sb.append( String.format("\t\t'group': '%s',\n", marker.getGroup()) );
		sb.append( String.format("\t\t'name': '%s',\n", marker.getName()) );
		
		if(marker.getPicture() != null && !marker.getPicture().equals("")) {
			sb.append( String.format("\t\t'icon': '%s',\n", marker.getPicture()) );
		}
		
		sb.append( String.format("\t\t'x': '%d',\n", marker.getX()) );
		sb.append( String.format("\t\t'y': '%d',\n", marker.getY()) );
		
		// Last property: no comma
		sb.append( String.format("\t\t'z': '%d'\n", marker.getZ()) );
		
		sb.append("\t}");
	}
	
	private void writeFilters() {
		for(String group : this.groups) {
			this.writeFilter(group);
			sb.append("\n");
		}
	}
	private void writeFilter(String group) {
		// def filter_group(poi):
		sb.append( 
			String.format(
				"def filter_%s(poi):\n", 
				this.sanitize(group)
			)
		);
		
		//	try:
		sb.append("\t");
		sb.append("try:");
		sb.append("\n");
		
	    //		if poi['group'] == 'thisgroup':
		sb.append("\t\t");
		sb.append( String.format("if poi['group'] == '%s':", group) );
		sb.append("\n");
		
	    //			return "some text"
		sb.append("\t\t\t");
		sb.append("return poi['name']");
		sb.append("\n");
		
	    //	except KeyError:
		sb.append("\t");
		sb.append("except KeyError:");
		sb.append("\n");
		
	    //		return None
		sb.append("\t\t");
		sb.append("return None");
		sb.append("\n");
	}
	
	private void writeMarkerGroups() {
		sb.append("markers = [\n");
		
		for(int i = 0; i < this.groups.size(); i++) {
			sb.append("\t");
			this.writeMarkerGroup( this.groups.get(i) );
			
			// If it's not the last POI, add a comma
			if(i < this.groups.size() - 1) {
				sb.append(",");
			}
			
			sb.append("\n");
		}
		
		sb.append("]\n");
	}
	private void writeMarkerGroup(String group) {
		sb.append( 
			String.format(
				"dict(name='%s', filterFunction=filter_%s, icon='../custom-icons/%s.png')",
				group.replace("auto-", ""),
				this.sanitize(group),
				group
			)
		);
	}
	
	private String sanitize(String input) {
		StringBuilder result = new StringBuilder();
		
		for(int i = 0; i < input.length(); i++) {
		    char tmpChar = input.charAt( i );
		    if ( (tmpChar >= 65 && tmpChar <= 90) || (tmpChar >= 97 && tmpChar <= 122) ) {
		        result.append( tmpChar );
		    }
		}
		
		return result.toString();
	}
	
	private String saveToFile(String filePath) {
		try {
			FileOutputStream stream = new FileOutputStream(filePath, false);
			
			byte[] bytes = sb.toString().getBytes();
			stream.write(bytes, 0, bytes.length);
			stream.close();
			
			return "Done.";
			
		} catch (FileNotFoundException e) {
			return "File not found";
			
		} catch (IOException e) {
			return "Erreur d'écriture: " + e.getMessage();
		}
		
	}
	
	public String generateMarkerFile(List<Marker> markers, String path) {
		// Reset
		this.sb = new StringBuilder();
		this.groups = new ArrayList<String>();
		this.markers = markers;
		
		sb.append("# coding=utf-8\n");
		sb.append("## MAPWRITER-ADMIN-CLIENT\n");
		sb.append("## Generated POI file\n\n");
		
		sb.append("# Points of Interest (Server markers)\n");
		this.writePois();
		
		sb.append("\n# Filter functions\n");
		this.writeFilters();
		
		sb.append("\n# Markers group array\n");
		this.writeMarkerGroups();
		
		sb.append("\n## END");
		
		String result = this.saveToFile(path);
		return result;
	}
}
