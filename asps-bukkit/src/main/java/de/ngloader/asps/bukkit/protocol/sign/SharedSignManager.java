package de.ngloader.asps.bukkit.protocol.sign;

import de.ngloader.asps.api.bukkit.protocol.ProtocolAdapter;
import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;
import de.ngloader.asps.api.bukkit.protocol.sign.SignManager;
import de.ngloader.asps.bukkit.protocol.SharedProtocolAdapter;

public class SharedSignManager implements SignManager {

	private final SharedProtocolAdapter protocolAdapter;

	public SharedSignManager(SharedProtocolAdapter protocolAdapter) {
		this.protocolAdapter = protocolAdapter;
	}

	@Override
	public SignManager delete(ProtocolBase base) {
		this.protocolAdapter.delete(base);
		return this;
	}

	@Override
	public ProtocolAdapter getAdapter() {
		return this.protocolAdapter;
	}
}