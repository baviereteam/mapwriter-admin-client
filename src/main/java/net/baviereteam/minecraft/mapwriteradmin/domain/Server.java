package net.baviereteam.minecraft.mapwriteradmin.domain;

import java.util.List;

public class Server {
	private int id;

	// Name of the server (a slug provided to the webservice to access some server)
	private String name;

	// A generated access key to give the users to avoid everyone messing with our points
	private String key;

	private List<Marker> markers;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Marker> getMarkers() {
		return markers;
	}

	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}

	@Override
	public String toString() {
		return id + ". " + name + " (" + key + ")";
	}

}