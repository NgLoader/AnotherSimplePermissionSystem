package de.ngloader.asps.bukkit.protocol.sign;

import java.util.List;

import org.bukkit.Location;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

import de.ngloader.asps.api.bukkit.protocol.sign.Sign;
import de.ngloader.asps.bukkit.protocol.SharedBase;
import de.ngloader.asps.bukkit.protocol.SharedProtocolAdapter;

public class SharedSign extends SharedBase<SharedSign> implements Sign {

	private Location location;
	private String[] lines = new String[4];

	public SharedSign(SharedProtocolAdapter manager) {
		super(manager);
	}

	@Override
	protected void createSpawnPackets(List<PacketContainer> packets) {
		packets.add(SignProtocol.buildUpdateSignPacket(this));
	}

	@Override
	protected void createUpdatePackets(List<PacketContainer> packets) {
		packets.add(SignProtocol.buildUpdateSignPacket(this));
	}

	@Override
	protected void createDeletePackets(List<PacketContainer> packets) {
		//TODO call block update
	}

	public BlockPosition getLocationAsBlockPos() {
		return new BlockPosition(this.location.getBlockX(), this.location.getBlockY(), this.location.getBlockZ());
	}

	@Override
	public void setLine(int line, String content) {
		if (line > 3 || 0 > line) {
			throw new IllegalArgumentException("Line number is not between zero and three.");
		}

		this.lines[line] = content;
	}

	@Override
	public void setLines(String[] lines) {
		for (int i = 0; i < this.lines.length; i++) {
			if (i >= lines.length) {
				break;
			}

			this.lines[i] = lines[i];
		}
	}

	@Override
	public String[] getLines() {
		return this.lines;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}
}