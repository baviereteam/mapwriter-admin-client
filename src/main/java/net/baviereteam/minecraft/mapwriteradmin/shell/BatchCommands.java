package net.baviereteam.minecraft.mapwriteradmin.shell;

import java.util.List;

import net.baviereteam.minecraft.mapwriteradmin.ToolBag;
import net.baviereteam.minecraft.mapwriteradmin.domain.Marker;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class BatchCommands implements CommandMarker {

	@CliCommand(value="generate poifile")
	public String generatePoiFile(
		@CliOption(key = { "host" }, mandatory = true, help = "The host to connect to") final String host,
		@CliOption(key = { "port" }, mandatory = true, help = "The port number to connect to") final int port,
		@CliOption(key = { "serverId" }, mandatory = true, help = "The server ID") final int serverId,
		@CliOption(key = { "key" }, mandatory = true, help = "Authentication key") final String key,
		@CliOption(key = { "file" }, mandatory = true, help = "File to write to") final String path) {
		
		// Select the server		
		if(host.contains("://")) {
			ToolBag.getInstance().getWebConnector().setHost(host);
		}
		
		else {
			ToolBag.getInstance().getWebConnector().setHost("http://" + host);
		}
		
		ToolBag.getInstance().getWebConnector().setPort(port);
		ToolBag.getInstance().setUsedKey(key);
		ToolBag.getInstance().getServerInterface().select(serverId);
		
		try {
			List<Marker> list = ToolBag.getInstance().getMarkerInterface().listAsMarkerList();
			return ToolBag.getInstance().getPoiConnector().generateMarkerFile(list, path);
		}
		
		catch(Exception ex) {
			return ex.getMessage();
		}
		
	}
	
}
