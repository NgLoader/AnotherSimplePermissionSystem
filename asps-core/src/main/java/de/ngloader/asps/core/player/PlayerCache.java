package de.ngloader.asps.core.player;

import com.google.common.cache.CacheLoader;

public class PlayerCache<Player> extends CacheLoader<Player, ASPSPlayer> {

	public PlayerCache(PlayerManager<Player> playerManager) {
	}

	@Override
	public ASPSPlayer load(Player key) throws Exception {
		return null;
	}
}