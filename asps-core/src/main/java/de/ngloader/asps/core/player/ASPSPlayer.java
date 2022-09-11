package de.ngloader.asps.core.player;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import de.ngloader.asps.core.database.model.GroupUser;
import de.ngloader.asps.core.database.model.User;
import de.ngloader.asps.core.database.model.UserPermission;

public class ASPSPlayer<Player> {

	private final AtomicReference<Status> status = new AtomicReference<>(Status.INITIALIZE);

	private final Player player;
	private User user;

	public Set<GroupUser> groups;
	public Set<UserPermission> permissions;

	public ASPSPlayer(Player player) {
		this.player = player;
	}

	void loadUser(User user) {
		if (this.status.compareAndSet(Status.INITIALIZE, Status.LOADED)) {
			this.user = user;
		} else {
			throw new IllegalStateException("User is already loaded");
		}
	}

	public Player getPlayer() {
		return this.player;
	}

	public User getUser() {
		return this.user;
	}

	public enum Status {
		INITIALIZE,
		LOADED,
		UNLOADED
	}
}
