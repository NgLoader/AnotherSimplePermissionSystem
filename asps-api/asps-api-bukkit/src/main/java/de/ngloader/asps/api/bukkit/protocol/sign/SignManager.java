package de.ngloader.asps.api.bukkit.protocol.sign;

import de.ngloader.asps.api.bukkit.protocol.ProtocolAdapter;
import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;

public interface SignManager {

	SignManager delete(ProtocolBase base);

	ProtocolAdapter getAdapter();
}
