package de.ngloader.asps.api.bukkit.protocol;

import java.util.Set;

import org.bukkit.entity.Player;

public interface ProtocolBase {

	void deleteInstance();

	ProtocolBase addToPlayers(Player player, Player... players);

	ProtocolBase removeFromPlayers(Player player, Player... players);

	Set<Player> getPlayers();

	ProtocolAdapter getAdapter();
}
