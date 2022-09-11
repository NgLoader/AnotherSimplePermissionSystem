package de.ngloader.asps.api.bukkit.protocol.team;

import de.ngloader.asps.api.bukkit.protocol.ProtocolAdapter;
import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;

public interface TeamManager {

	Team createTeam(String name);

	TeamManager delete(ProtocolBase base);

	ProtocolAdapter getAdapter();
}