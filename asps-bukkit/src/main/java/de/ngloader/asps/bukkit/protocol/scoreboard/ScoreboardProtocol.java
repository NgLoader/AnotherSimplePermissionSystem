package de.ngloader.asps.bukkit.protocol.scoreboard;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.RenderType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;

class ScoreboardProtocol {

	//	ClientboundSetDisplayObjectivePacket
	//	ClientboundSetObjectivePacket
	//	ClientboundSetScorePacket

	private static final ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

	private static PacketContainer buildObjectivePacket(SharedObjective objective, ObjectiveMethod method) {
		PacketContainer packet = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
		packet.getStrings().write(0, objective.getName());
		packet.getIntegers().write(0, method.ordinal());
		if (method.hasParameters()) {
			packet.getChatComponents().write(0, objective.titleWrapped);
			packet.getEnumModifier(RenderType.class, 2).write(0, objective.getRenderType());
		}
		return packet;
	}

	private static PacketContainer buildObjectiveScorePacket(SharedObjectiveScore score, ScoreboardAction action) {
		PacketContainer packet = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.SCOREBOARD_SCORE);
		packet.getStrings().write(0, score.getName());
		packet.getScoreboardActions().write(0, action);
		packet.getStrings().write(1, score.getObjective().getName());
		if (action != ScoreboardAction.REMOVE) {
			packet.getIntegers().write(0, score.getScore());
		}
		return packet;
	}

	public static PacketContainer addObjectivePacket(SharedObjective objective) {
		return buildObjectivePacket(objective, ObjectiveMethod.ADD);
	}

	public static PacketContainer changeObjectivePacket(SharedObjective objective) {
		return buildObjectivePacket(objective, ObjectiveMethod.CHANGE);
	}

	public static PacketContainer removeObjectivePacket(SharedObjective objective) {
		return buildObjectivePacket(objective, ObjectiveMethod.REMOVE);
	}

	public static PacketContainer changeObjectiveScorePacket(SharedObjectiveScore score) {
		return buildObjectiveScorePacket(score, ScoreboardAction.CHANGE);
	}

	public static PacketContainer removeObjectiveScorePacket(SharedObjectiveScore score) {
		return buildObjectiveScorePacket(score, ScoreboardAction.REMOVE);
	}

	public static PacketContainer setObjectiveDisplayPacket(SharedObjective objective, DisplaySlot slot) {
		PacketContainer packet = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
		packet.getStrings().write(0, objective == null ? "" : objective.getName());
		packet.getIntegers().write(0, switch (slot) {
		case PLAYER_LIST -> 0;
		case SIDEBAR -> 1;
		case BELOW_NAME -> 2;
		default -> throw new IllegalArgumentException("Unexpected value: " + slot); //TODO https://wiki.vg/Protocol checkout Display Objective and add color support
		});
		return packet;
	}

	private enum ObjectiveMethod {
		ADD,
		REMOVE,
		CHANGE;

		public boolean hasParameters() {
			return switch (this.ordinal()) {
			case 0, 2 -> true;
			default -> false;
			};
		}
	}
}
