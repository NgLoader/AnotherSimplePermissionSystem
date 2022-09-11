package de.ngloader.asps.api.bukkit.protocol;

import de.ngloader.asps.api.bukkit.protocol.scoreboard.ScoreboardManager;
import de.ngloader.asps.api.bukkit.protocol.sign.SignManager;
import de.ngloader.asps.api.bukkit.protocol.team.TeamManager;

public interface ProtocolAdapter {

	ScoreboardManager getScoreboard();

	TeamManager getTeam();

	SignManager getSign();

	ProtocolAdapter delete(ProtocolBase base);
}