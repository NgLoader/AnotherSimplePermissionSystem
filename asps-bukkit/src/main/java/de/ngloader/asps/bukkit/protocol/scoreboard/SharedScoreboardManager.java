package de.ngloader.asps.bukkit.protocol.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;

import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;
import de.ngloader.asps.api.bukkit.protocol.scoreboard.Scoreboard;
import de.ngloader.asps.api.bukkit.protocol.scoreboard.ScoreboardManager;
import de.ngloader.asps.bukkit.protocol.SharedProtocolAdapter;

public class SharedScoreboardManager implements ScoreboardManager, Listener {

	private final SharedScoreboard globalScoreboard = new SharedScoreboard(this);

	private final SharedProtocolAdapter protocolAdapter;

	public SharedScoreboardManager(SharedProtocolAdapter protocolAdapter) {
		this.protocolAdapter = protocolAdapter;
	}

	public void startup() {
		Bukkit.getPluginManager().registerEvents(this, this.protocolAdapter.getPlugin());

		for (Player player : Bukkit.getOnlinePlayers()) {
			this.globalScoreboard.addToPlayers(player);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		this.globalScoreboard.addToPlayers(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		this.globalScoreboard.removeFromPlayers(event.getPlayer());
	}

	private SharedScoreboardManager unassignDisplaySlot(DisplaySlot[] slots, Player[] players) {
		for (DisplaySlot slot : slots) {
			if (slot != null) {
				this.protocolAdapter.sendPacket(players, ScoreboardProtocol.setObjectiveDisplayPacket(null, slot));
			}
		}
		return this;
	}

	@Override
	public SharedScoreboardManager unassignDisplaySlot(DisplaySlot slot, Player... players) {
		return this.unassignDisplaySlot(slot, null, null, players);
	}

	@Override
	public SharedScoreboardManager unassignDisplaySlot(DisplaySlot slot, DisplaySlot slot2, Player... players) {
		return this.unassignDisplaySlot(slot, slot2, null, players);
	}

	@Override
	public SharedScoreboardManager unassignDisplaySlot(DisplaySlot slot, DisplaySlot slot2, DisplaySlot slot3,
			Player... players) {
		return this.unassignDisplaySlot(new DisplaySlot[] { slot, slot2, slot3 }, players);
	}

	@Override
	public Scoreboard createScoreboard() {
		return new SharedScoreboard(this);
	}

	@Override
	public SharedSidebar createSidebar(String name) {
		SharedSidebar sidebar = new SharedSidebar(this, name);
		this.protocolAdapter.addBase(sidebar);
		return sidebar;
	}

	@Override
	public SharedObjective createObjective(String name) {
		SharedObjective objective = new SharedObjective(this, name);
		this.protocolAdapter.addBase(objective);
		return objective;
	}

	@Override
	public SharedScoreboardManager delete(ProtocolBase base) {
		this.protocolAdapter.delete(base);
		return this;
	}

	@Override
	public Scoreboard getGlobalScoreboard() {
		return this.globalScoreboard;
	}

	@Override
	public SharedProtocolAdapter getAdapter() {
		return this.protocolAdapter;
	}
}