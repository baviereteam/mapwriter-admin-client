package net.baviereteam.minecraft.mapwriteradmin.shell;

import net.baviereteam.minecraft.mapwriteradmin.ToolBag;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class MarkerCommands implements CommandMarker {
	@CliCommand(value="marker list")
	public String list() {
		return ToolBag.getInstance().getMarkerInterface().list();
	}
	
	@CliCommand(value="marker select")
	public String select(
			@CliOption(key = { "id" }, mandatory = true, help = "The marker ID")
			final long markerId) {
		
		return ToolBag.getInstance().getMarkerInterface().select(markerId);
	}
	
	@CliCommand(value="marker create")
	public String create(
			@CliOption(key = { "color" }, mandatory = true) final int color,
			@CliOption(key = { "dimension" }, mandatory = true) final int dimension,
			@CliOption(key = { "group" }, mandatory = true) final String group,
			@CliOption(key = { "name" }, mandatory = true) final String name,
			@CliOption(key = { "x" }, mandatory = true) final int x,
			@CliOption(key = { "y" }, mandatory = true) final int y,
			@CliOption(key = { "z" }, mandatory = true) final int z,
			@CliOption(key = { "picture" }, mandatory = false) final String picture) {
		
		return ToolBag.getInstance().getMarkerInterface().add(color, dimension, group, name, x, y, z, picture);
	}
	
	@CliCommand(value="marker edit")
	public String rename(
			@CliOption(key = { "color" }, mandatory = false) final Integer color,
			@CliOption(key = { "dimension" }, mandatory = false) final Integer dimension,
			@CliOption(key = { "group" }, mandatory = false) final String group,
			@CliOption(key = { "name" }, mandatory = false) final String name,
			@CliOption(key = { "x" }, mandatory = false) final Integer x,
			@CliOption(key = { "y" }, mandatory = false) final Integer y,
			@CliOption(key = { "z" }, mandatory = false) final Integer z,
			@CliOption(key = { "picture" }, mandatory = false) final String picture) {
		
		return ToolBag.getInstance().getMarkerInterface().edit(color, dimension, group, name, x, y, z, picture);
	}
	
	@CliCommand(value="marker delete")
	public String delete() {
		return ToolBag.getInstance().getMarkerInterface().delete();
	}
}
