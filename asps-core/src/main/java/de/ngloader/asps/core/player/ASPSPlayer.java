package de.ngloader.asps.core.player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.ngloader.asps.core.database.Expireable;
import de.ngloader.asps.core.database.model.GroupUser;
import de.ngloader.asps.core.database.model.User;
import de.ngloader.asps.core.database.model.UserPermission;

public abstract class ASPSPlayer<Player> implements Runnable {

	protected final Player player;
	protected final User user;

	private Set<String> permissions = new HashSet<>();
	private Set<Expireable> expireable = Collections.newSetFromMap(new ConcurrentHashMap<>()); 

	public ASPSPlayer(Player player, User user) {
		this.player = player;
		this.user = user;
	}

	@Override
	public void run() {
		for (Iterator<Expireable> iterator = this.expireable.iterator(); iterator.hasNext(); ) {
			Expireable expireable = iterator.next();
			if (expireable.isExpired()) {
				iterator.remove();

				if (expireable instanceof UserPermission userPermission) {
					this.removePermission(userPermission.permission);
				} else if (expireable instanceof GroupUser groupUser) {
					this.removeGroup(groupUser.groupId);
				}
			}
		}
	}

	protected abstract void setPermission(String permission, boolean state);
	protected abstract void unsetPermission(String permission);

	public void setPermission(String permission, boolean state, long expire) {
	}

	public void removePermission(String permission) {
	}

	public void removeGroup(int groupId) {
		//TODO
	}

	public void removeGroup(String group) {
		//TODO
	}

	public Set<String> getPermissions() {
		return this.permissions;
	}

	public Player getPlayer() {
		return this.player;
	}

	public User getUser() {
		return this.user;
	}
}
