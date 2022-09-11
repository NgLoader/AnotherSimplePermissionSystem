package de.ngloader.asps.api.bukkit.protocol.scoreboard;

import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;

public interface ScoreboardObjectiveScore extends ProtocolBase {

	String getName();

	int getScore();

	ScoreboardObjectiveScore setScore(int score);

	ScoreboardObjective getObjective();

}