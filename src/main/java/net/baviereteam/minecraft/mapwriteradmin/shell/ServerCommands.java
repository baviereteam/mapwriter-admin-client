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
	public String select() {
		return "";
	}
	
	@CliAvailabilityIndicator({"server create", "server rename", 
		"server changekey", "server delete"})
	public boolean serverSelected() {
		return ToolBag.getInstance().getServerInterface().getSelectedServerId() > 0;
	}
	
	@CliCommand(value="server create")
	public String create() {
		return "";
	}
	
	@CliCommand(value="server rename")
	public String rename() {
		return "";
	}
	
	@CliCommand(value="server changekey")
	public String changekey() {
		return "";
	}
	
	@CliCommand(value="server delete")
	public String delete() {
		return "";
	}
}
