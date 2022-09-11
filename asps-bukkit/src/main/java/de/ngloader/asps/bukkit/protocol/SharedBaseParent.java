package de.ngloader.asps.bukkit.protocol;

import java.util.ArrayList;
import java.util.List;

import com.comphenix.protocol.events.PacketContainer;

public abstract class SharedBaseParent<This extends SharedBaseParent<This, ?>, Entry extends SharedBase<?>> extends SharedBase<This> {

	private List<Entry> entries = new ArrayList<>();

	public SharedBaseParent(SharedProtocolAdapter manager) {
		super(manager);
	}

	@Override
	public void generateSpawnPackets(List<PacketContainer> packets) {
		super.generateSpawnPackets(packets);

		for (Entry entry : this.entries) {
			entry.generateSpawnPackets(packets);
		}
	}

	@Override
	public void generateUpdatePackets(List<PacketContainer> packets) {
		super.generateUpdatePackets(packets);

		for (Entry entry : this.entries) {
			entry.generateUpdatePackets(packets);
		}
	}

	@Override
	public void generateDeletePackets(List<PacketContainer> packets) {
		super.generateDeletePackets(packets);

		for (Entry entry : this.entries) {
			entry.generateDeletePackets(packets);
		}
	}

	public boolean addEntry(Entry entry) {
		if (this.entries.add(entry)) {
			entry.createSpawnPackets(this.updatePacket);
			return true;
		}
		return false;
	}

	public boolean removeEntry(Entry entry) {
		if (this.entries.remove(entry)) {
			entry.createDeletePackets(this.updatePacket);
			return true;
		}
		return false;
	}

	public Entry getEntry(int index) {
		return index > -1 && this.entries.size() > index ? this.entries.get(index) : null;
	}

	public List<Entry> getEntries() {
		return this.entries;
	}
}
