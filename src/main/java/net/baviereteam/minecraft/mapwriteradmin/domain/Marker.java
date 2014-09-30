package net.baviereteam.minecraft.mapwriteradmin.domain;

public class Marker {
	private long id;
	private String group;
	private String name;
	private int x;
	private int y;
	private int z;
	private int dimension;

	// The color we will use to represent this marker in MapWriter
	private int color;

	// The picture we will use to represent this marker in Minecraft Overviewer
	private String picture;

	private Server server;

	public long getId() {
		return id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Marker() {
	}
	
	@Override
	public String toString() {
		return id + ".  " + name + "(in " + group + " at " + x + "," + y + "," + z + ")";
	}
}
