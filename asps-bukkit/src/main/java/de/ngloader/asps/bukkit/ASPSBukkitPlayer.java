package de.ngloader.asps.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import de.ngloader.asps.core.database.model.User;
import de.ngloader.asps.core.player.ASPSPlayer;

public class ASPSBukkitPlayer extends ASPSPlayer<Player> {

	private final ASPSBukkitPlugin plugin;
	private final PermissionAttachment permissionAttachment;

	public ASPSBukkitPlayer(ASPSBukkitPlugin plugin, Player player, User user) {
		super(player, user);

		this.plugin = plugin;
		this.permissionAttachment = this.player.addAttachment(this.plugin);
	}

	@Override
	protected void setPermission(String permission, boolean state) {
		this.permissionAttachment.setPermission(permission, state);
	}

	@Override
	protected void unsetPermission(String permission) {
		this.permissionAttachment.unsetPermission(permission);
	}
}