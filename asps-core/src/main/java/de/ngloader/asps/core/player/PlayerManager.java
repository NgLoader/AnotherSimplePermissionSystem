package de.ngloader.asps.core.player;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.database.GroupHandler;
import de.ngloader.asps.core.database.UserHandler;

public abstract class PlayerManager<Player> {

	private final UserHandler userHandler;
	private final GroupHandler groupHandler;

//	private final CacheLoader<Player, ASPSPlayer> cacheLoader;
//	private final LoadingCache<Player, ASPSPlayer> playerCache;

	private final Map<Player, ASPSPlayer> playerCache = new WeakHashMap<>();
	private final Map<Player, ReentrantLock> playerLock = new ConcurrentHashMap<>();

	public PlayerManager(ASPS plugin) {
		this.userHandler = plugin.getUserHandler();
		this.groupHandler = plugin.getGroupHandler();

//		this.cacheLoader = new PlayerCache<>(this);
//		this.playerCache = CacheBuilder.newBuilder()
//				.expireAfterAccess(10, TimeUnit.MINUTES)
//				.build(this.cacheLoader);
	}

	public abstract UUID getUUID(Player player);

	public CompletionStage<ASPSPlayer> getPlayer(Player player) {
		ReentrantLock lock = this.playerLock.getOrDefault(player, new ReentrantLock());
//		ASPSPlayer aspsPlayer = this.playerCache.getIfPresent(player);
//		if (aspsPlayer != null) {
//			return CompletableFuture.completedStage(aspsPlayer);
//		}

		CompletableFuture<ASPSPlayer> future = new CompletableFuture<>();
		//TODO load player from cache
		return future;
	}

	public CompletableFuture<Void> invalidatePlayer(Player player) {
		CompletableFuture<Void> future = new CompletableFuture<>();
		return future;
	}
}