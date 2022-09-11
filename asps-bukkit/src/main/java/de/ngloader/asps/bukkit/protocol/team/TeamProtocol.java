package de.ngloader.asps.bukkit.protocol.team;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.bukkit.ChatColor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.InternalStructure;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;

class TeamProtocol {

	//	ClientboundSetPlayerTeamPacket

	private static final ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();
	private static final Class<?> NMS_ENUM_CHAT_COLOR = MinecraftReflection.getMinecraftClass("EnumChatFormat");

	private static int createTeamOptions(SharedTeam team) {
		int mask = 0;
		if (team.isAllowFirendlyFire()) {
			mask |= 1;
		}
		if (team.isCanSeeFriendlyInvisible()) {
			mask |= 2;
		}
		return mask;
	}

	private static InternalStructure createTeamData(PacketContainer packet, SharedTeam team) {
		Optional<InternalStructure> optional = packet.getOptionalStructures().read(0);
		if (optional.isPresent()) {
			InternalStructure structure = optional.get();
			structure.getChatComponents().write(0, team.displayNameWrapped);
			structure.getChatComponents().write(1, team.prefixWrapped);
			structure.getChatComponents().write(2, team.suffixWrapped);
			structure.getStrings().write(0, team.getNameTagVisiblity().getVisibilityName());
			structure.getStrings().write(1, team.getCollisionRule().getCollisionName());
			structure.getEnumModifier(ChatColor.class, NMS_ENUM_CHAT_COLOR).write(0, team.getColor());
			structure.getIntegers().write(0, createTeamOptions(team));
			return structure;
		}
		return null;
	}

	private static PacketContainer buildTeamPacket(SharedTeam team, TeamMethod method, Set<String> entrys) {
		PacketContainer packet = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
		packet.getStrings().write(0, team.getName());
		packet.getIntegers().write(0, method.ordinal());
		if (method.hasParameters()) {
			packet.getOptionalStructures().write(0, Optional.ofNullable(createTeamData(packet, team)));
		}
		if (method.hasPlayerList()) {
			packet.getSpecificModifier(Collection.class).write(0, entrys != null ? entrys : team.getEntrys());
		}
		return packet;
	}

	public static PacketContainer addTeamPacket(SharedTeam team) {
		return buildTeamPacket(team, TeamMethod.ADD, null);
	}

	public static PacketContainer changeTeamPacket(SharedTeam team) {
		return buildTeamPacket(team, TeamMethod.CHANGE, null);
	}

	public static PacketContainer joinTeamPacket(SharedTeam team, Set<String> entrys) {
		return buildTeamPacket(team, TeamMethod.JOIN, entrys);
	}

	public static PacketContainer leaveTeamPacket(SharedTeam team, Set<String> entrys) {
		return buildTeamPacket(team, TeamMethod.LEAVE, entrys);
	}

	public static PacketContainer removeTeamPacket(SharedTeam team) {
		return buildTeamPacket(team, TeamMethod.REMOVE, null);
	}

	private enum TeamMethod {
		ADD,
		REMOVE,
		CHANGE,
		JOIN,
		LEAVE;

		public boolean hasPlayerList() {
			return switch (this.ordinal()) {
			case 0, 3, 4 -> true;
			default -> false;
			};
		}

		public boolean hasParameters() {
			return switch (this.ordinal()) {
			case 0, 2 -> true;
			default -> false;
			};
		}
	}
}
