package net.baviereteam.minecraft.mapwriteradmin.shell;

import net.baviereteam.minecraft.mapwriteradmin.ToolBag;
import net.baviereteam.minecraft.mapwriteradmin.interfaces.ServerInterface;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class ServerCommands implements CommandMarker {
	
	@CliCommand(value="auth")
	public String auth(
		@CliOption(key = { "key" }, mandatory = true, help = "The master key")
		final String key) {
		
		ToolBag.getInstance().getServerInterface().setMasterKey(key);
		return "Using key " + key;
	}
	
	@CliCommand(value="server list")
	public String list() {
		return ToolBag.getInstance().getServerInterface().list();
	}
	
	@CliCommand(value="server select")
	public String select(
			@CliOption(key = { "id" }, mandatory = true, help = "The server ID")
			final int serverId) {
		return ToolBag.getInstance().getServerInterface().select(serverId);
	}
	
	@CliAvailabilityIndicator({ "server rename", 
		"server changekey", "server delete"})
	public boolean serverSelected() {
		return ToolBag.getInstance().getServerInterface().getSelectedServerId() > 0;
	}
	
	@CliCommand(value="server create")
	public String create(
			@CliOption(key = { "name" }, mandatory = true, help = "The server name")
			final String name) {
		return ToolBag.getInstance().getServerInterface().create(name);
	}
	
	@CliCommand(value="server rename")
	public String rename(
			@CliOption(key = { "name" }, mandatory = true, help = "The server name")
			final String name) {
		return ToolBag.getInstance().getServerInterface().rename(name);
	}
	
	@CliCommand(value="server changekey")
	public String changekey() {
		return ToolBag.getInstance().getServerInterface().changekey();
	}
	
	@CliCommand(value="server delete")
	public String delete() {
		return ToolBag.getInstance().getServerInterface().delete();
	}
}
