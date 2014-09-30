package net.baviereteam.minecraft.mapwriteradmin.shell;

import java.util.HashMap;
import java.util.Map;

import net.baviereteam.minecraft.mapwriteradmin.ToolBag;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class ConnectionCommands implements CommandMarker {
	
	/*@CliCommand(value="test")
	public String test() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userKey", "hello");
		
		try {
			return ToolBag.getInstance().getWebConnector().execute("server/list", parameters).toString();
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}*/
	
	@CliCommand(value="connect")
	public String setMapwriterServer(
		@CliOption(key = { "host" }, mandatory = true, help = "The host name")
		final String host,
		@CliOption(key = { "port" }, mandatory = true, help = "The port number")
		final int port) {
		
		if(host.contains("://")) {
			ToolBag.getInstance().getWebConnector().setHost(host);
		}
		
		else {
			ToolBag.getInstance().getWebConnector().setHost("http://" + host);
		}
		
		ToolBag.getInstance().getWebConnector().setPort(port);
		
		return "Using MapWriter-Server at " 
			+ ToolBag.getInstance().getWebConnector().getHost() + ":" + port;
	}
}
