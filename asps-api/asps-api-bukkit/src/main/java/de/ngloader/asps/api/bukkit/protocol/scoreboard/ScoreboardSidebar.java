package de.ngloader.asps.api.bukkit.protocol.scoreboard;

import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;
import net.md_5.bungee.api.chat.BaseComponent;

public interface ScoreboardSidebar extends ProtocolBase {

	ScoreboardSidebar fillLines(int from, int to);

	ScoreboardSidebar setLine(int line, String text);

	ScoreboardSidebar setLine(int line, BaseComponent text);

	ScoreboardSidebar deleteLine(int line);

}