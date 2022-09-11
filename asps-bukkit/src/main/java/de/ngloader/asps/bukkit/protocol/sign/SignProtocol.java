package de.ngloader.asps.bukkit.protocol.sign;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

class SignProtocol {

	// ServerboundSignUpdatePacket

	private static final ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

	public static PacketContainer buildUpdateSignPacket(SharedSign sign) {
		PacketContainer packet = PROTOCOL_MANAGER.createPacket(PacketType.Play.Client.UPDATE_SIGN);
		packet.getBlockPositionModifier().write(0, sign.getLocationAsBlockPos());
		packet.getStringArrays().write(0, sign.getLines());
		return packet;
	}
}