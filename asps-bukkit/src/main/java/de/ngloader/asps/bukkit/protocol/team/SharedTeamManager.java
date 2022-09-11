package de.ngloader.asps.bukkit.protocol.team;

import de.ngloader.asps.api.bukkit.protocol.ProtocolAdapter;
import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;
import de.ngloader.asps.api.bukkit.protocol.team.Team;
import de.ngloader.asps.api.bukkit.protocol.team.TeamManager;
import de.ngloader.asps.bukkit.protocol.SharedProtocolAdapter;

public class SharedTeamManager implements TeamManager {

	private SharedProtocolAdapter protocolAdapter;

	public SharedTeamManager(SharedProtocolAdapter protocolAdapter) {
		this.protocolAdapter = protocolAdapter;
	}

	@Override
	public Team createTeam(String name) {
		return new SharedTeam(this.protocolAdapter, name);
	}

	@Override
	public TeamManager delete(ProtocolBase base) {
		this.protocolAdapter.delete(base);
		return this;
	}

	@Override
	public ProtocolAdapter getAdapter() {
		return this.protocolAdapter;
	}	
}