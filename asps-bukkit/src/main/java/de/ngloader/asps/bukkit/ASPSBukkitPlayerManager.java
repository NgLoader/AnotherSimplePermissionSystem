package de.ngloader.asps.bukkit;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.database.model.User;
import de.ngloader.asps.core.player.ASPSPlayer;
import de.ngloader.asps.core.player.PlayerManager;

public class ASPSBukkitPlayerManager extends PlayerManager<Player> implements Listener {

	private ASPSBukkitPlugin plugin;

	public ASPSBukkitPlayerManager(ASPS asps, ASPSBukkitPlugin plugin) {
		super(asps);
		this.plugin = plugin;
	}

	@Override
	public UUID getUUID(Player player) {
		return player.getUniqueId();
	}

	@Override
	protected ASPSPlayer<Player> constructASPSPlayer(Player player, User user) {
		return new ASPSBukkitPlayer(this.plugin, player, user);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
	}
}