package de.ngloader.asps.bukkit.protocol;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

import de.ngloader.asps.api.bukkit.protocol.ProtocolAdapter;
import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;
import de.ngloader.asps.api.bukkit.protocol.scoreboard.ScoreboardManager;
import de.ngloader.asps.api.bukkit.protocol.sign.SignManager;
import de.ngloader.asps.api.bukkit.protocol.team.TeamManager;
import de.ngloader.asps.bukkit.protocol.scoreboard.SharedScoreboardManager;
import de.ngloader.asps.bukkit.protocol.sign.SharedSignManager;
import de.ngloader.asps.bukkit.protocol.team.SharedTeamManager;

public class SharedProtocolAdapter implements ProtocolAdapter, Runnable, Listener {

	private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

	private final List<SharedBase<?>> entries = new CopyOnWriteArrayList<>();

	private final Plugin plugin;

	private final SharedScoreboardManager scoreboardManager;
	private final SharedTeamManager teamManager;
	private final SharedSignManager signManager;

	public SharedProtocolAdapter(Plugin plugin) {
		this.plugin = plugin;

		this.scoreboardManager = new SharedScoreboardManager(this);
		this.teamManager = new SharedTeamManager(this);
		this.signManager = new SharedSignManager(this);
	}

	public void startup() {
		this.scoreboardManager.startup();

		Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0);
	}

	@Override
	public void run() {
		for (SharedBase<?> base : this.entries) {
			base.run();
		}
	}

	public void sendPacket(Set<Player> players, List<PacketContainer> packets) {
		for (Player player : players) {
			for (PacketContainer packet : packets) {
				this.protocolManager.sendServerPacket(player, packet);
			}
		}
	}

	public void sendPacket(Player player, List<PacketContainer> packets) {
		for (PacketContainer packet : packets) {
			this.protocolManager.sendServerPacket(player, packet);
		}
	}

	public void sendPacket(Player[] players, PacketContainer... packets) {
		for (Player player : players) {
			for (PacketContainer packet : packets) {
				this.protocolManager.sendServerPacket(player, packet);
			}
		}
	}

	public void sendPacket(Set<Player> players, PacketContainer... packets) {
		this.sendPacket(players, Arrays.asList(packets));
	}

	public void addPlayer(SharedBase<?> base, Player player, Player... players) {
		base.addPlayer(player);
		for (Player entry : players) {
			base.addPlayer(entry);
		}
	}

	public void removePlayer(SharedBase<?> base, Player player, Player... players) {
		base.removePlayer(player);
		for (Player entry : players) {
			base.removePlayer(entry);
		}
	}

	public void addBase(SharedBase<?> base) {
		this.entries.add(base);
	}

	@Override
	public SharedProtocolAdapter delete(ProtocolBase base) {
		this.entries.remove(base);
		if (base instanceof SharedBase<?> realBase) {
			realBase.delete();
		}
		return this;
	}

	public void shutdown() {
		for (ProtocolBase base : this.entries) {
			base.deleteInstance();
		}
	}

	@Override
	public ScoreboardManager getScoreboard() {
		return this.scoreboardManager;
	}

	@Override
	public TeamManager getTeam() {
		return this.teamManager;
	}

	@Override
	public SignManager getSign() {
		return this.signManager;
	}

	public Plugin getPlugin() {
		return this.plugin;
	}
}
