package de.ngloader.asps.bukkit;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.ngloader.asps.api.bukkit.ASPSBukkit;
import de.ngloader.asps.api.bukkit.protocol.ProtocolAdapter;
import de.ngloader.asps.bukkit.protocol.SharedProtocolAdapter;
import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.sql.SqlConfig;

public class ASPSBukkitPlugin extends JavaPlugin implements ASPSBukkit {

	private ASPS asps;

	private SharedProtocolAdapter protocolAdapter;

	@Override
	public void onLoad() {
		FileConfiguration config = this.getConfig();
		config.addDefault("host", "127.0.0.1");
		config.addDefault("database", "asps");
		config.addDefault("username", "root");
		config.addDefault("password", "pa22w0rd");
		config.addDefault("debug", "false");
		config.options().copyDefaults(true);
		this.saveConfig();

		this.protocolAdapter = new SharedProtocolAdapter(this);
		this.asps = new ASPS(new SqlConfig(
				config.getString("host"),
				config.getString("database"),
				config.getString("username"),
				config.getString("password"),
				config.getString("debug")));
	}

	@Override
	public void onEnable() {
		this.protocolAdapter.startup();
		this.asps.enable();
	}

	@Override
	public void onDisable() {
		try {
			this.asps.disable();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.protocolAdapter.shutdown();
		}
	}

	@Override
	public ProtocolAdapter getProtocolAdapter() {
		return this.protocolAdapter;
	}
}