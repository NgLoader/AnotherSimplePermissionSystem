package de.ngloader.asps.core.player;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.database.UserHandler;
import de.ngloader.asps.core.database.model.User;

public abstract class PlayerManager<Player> implements Runnable {

	private final UserHandler userHandler;

	private final Map<Player, ASPSPlayer<Player>> loadedPlayers = new WeakHashMap<>();
	private final Map<Player, CompletableFuture<ASPSPlayer<Player>>> loadingPlayers = new WeakHashMap<>();

	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	public PlayerManager(ASPS plugin) {
		this.userHandler = plugin.getUserHandler();
	}

	public abstract UUID getUUID(Player player);

	protected abstract ASPSPlayer<Player> constructASPSPlayer(Player player, User user);

	@Override
	public void run() {
		for (Iterator<ASPSPlayer<Player>> iterator = this.loadedPlayers.values().iterator(); iterator.hasNext(); ) {
			ASPSPlayer<Player> player = iterator.next();
			player.run();
		}
	}

	private void loadPlayer(Player player, final CompletableFuture<ASPSPlayer<Player>> future) {
		UUID uuid = this.getUUID(player);
		this.userHandler.getUserByUUID(uuid).whenComplete((user, error) -> {
			if (error != null) {
				future.completeExceptionally(error);
				return;
			}

			if (user == null) {
				this.userHandler.createUser(new User(uuid, null)).whenComplete((createdUser, error2) -> {
					if (error2 != null) {
						future.completeExceptionally(error2);
						return;
					}

					this.loadPlayer(player, future);
				});
			} else {
				ASPSPlayer<Player> aspsPlayer = this.constructASPSPlayer(player, user);
				this.loadedPlayers.put(player, aspsPlayer);
				this.loadingPlayers.remove(player);
				future.complete(aspsPlayer);
			}
		});
	}

	public CompletionStage<ASPSPlayer<Player>> getPlayer(Player player) {
		try {
			this.lock.readLock().lock();

			ASPSPlayer<Player> loadedPlayer = this.loadedPlayers.get(player);
			if (loadedPlayer != null) {
				return CompletableFuture.completedFuture(loadedPlayer);
			}
		} finally {
			this.lock.readLock().unlock();
		}

		try {
			this.lock.writeLock().lock();

			CompletableFuture<ASPSPlayer<Player>> future = this.loadingPlayers.get(player);
			if (future != null) {
				future = new CompletableFuture<>();
				this.loadingPlayers.put(player, future);

				this.loadPlayer(player, future);
			}
			return future;
		} finally {
			this.lock.writeLock().unlock();
		}
	}

	public CompletableFuture<Void> invalidatePlayer(Player player) {
		try {
			this.lock.writeLock().lock();

			CompletableFuture<ASPSPlayer<Player>> loadingFuture = this.loadingPlayers.remove(player);
			loadingFuture.cancel(true);

			this.loadedPlayers.remove(player);
		} finally {
			this.lock.writeLock().unlock();
		}
		return CompletableFuture.completedFuture(null);
	}
}