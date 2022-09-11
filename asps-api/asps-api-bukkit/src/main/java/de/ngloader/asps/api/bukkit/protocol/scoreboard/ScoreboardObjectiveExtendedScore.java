package de.ngloader.asps.api.bukkit.protocol.scoreboard;

import net.md_5.bungee.api.chat.BaseComponent;

public interface ScoreboardObjectiveExtendedScore extends ScoreboardObjectiveScore {

	ScoreboardObjectiveExtendedScore setDisplayName(BaseComponent name);

	ScoreboardObjectiveExtendedScore setDisplayName(String name);

}