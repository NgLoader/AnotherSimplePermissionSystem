package de.ngloader.asps.api.bukkit.protocol.sign;

import org.bukkit.Location;

import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;

public interface Sign extends ProtocolBase {

	void setLine(int line, String content);

	void setLines(String[] lines);

	String[] getLines();

	Location getLocation();
}
