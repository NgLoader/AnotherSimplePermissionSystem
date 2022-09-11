package de.ngloader.asps.bukkit.protocol.team;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import de.ngloader.asps.api.bukkit.protocol.team.Team;
import de.ngloader.asps.api.bukkit.protocol.team.TeamRule;
import de.ngloader.asps.bukkit.protocol.SharedBase;
import de.ngloader.asps.bukkit.protocol.SharedProtocolAdapter;
import de.ngloader.asps.bukkit.util.ComponentUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class SharedTeam extends SharedBase<SharedTeam> implements Team {

	private final String name;

	public WrappedChatComponent displayNameWrapped = ComponentUtil.EMPTY_CHAT_COMPONENT;
	public WrappedChatComponent prefixWrapped = ComponentUtil.EMPTY_CHAT_COMPONENT;
	public WrappedChatComponent suffixWrapped = ComponentUtil.EMPTY_CHAT_COMPONENT;

	private BaseComponent displayName = ComponentUtil.EMPTY_BASE_COMPONENT;
	private BaseComponent prefix = ComponentUtil.EMPTY_BASE_COMPONENT;
	private BaseComponent suffix = ComponentUtil.EMPTY_BASE_COMPONENT;

	private ChatColor color = ChatColor.WHITE;

	private TeamRule nameTagVisiblity = TeamRule.ALWAYS;
	private TeamRule collisionRule = TeamRule.ALWAYS;

	private boolean canSeeFriendlyInvisible = true;
	private boolean isAllowFirendlyFire = true;

	private Set<String> entrys = new HashSet<>();
	private Set<String> dirtJoinEntrys = new HashSet<>();
	private Set<String> dirtLeaveEntrys = new HashSet<>();

	private boolean dirtyParameters = false;

	public SharedTeam(SharedProtocolAdapter protocolAdapter, String name) {
		super(protocolAdapter);
		this.name = name;
	}

	@Override
	public void createSpawnPackets(List<PacketContainer> packets) {
		packets.add(TeamProtocol.addTeamPacket(this));
	}

	@Override
	public void createDeletePackets(List<PacketContainer> packets) {
		packets.add(TeamProtocol.removeTeamPacket(this));
	}

	@Override
	public void createUpdatePackets(List<PacketContainer> packets) {
		if (this.dirtyParameters) {
			packets.add(TeamProtocol.changeTeamPacket(this));
			this.dirtyParameters = false;
		}

		if (!this.dirtJoinEntrys.isEmpty()) {
			packets.add(TeamProtocol.joinTeamPacket(this, this.dirtJoinEntrys));
			this.dirtJoinEntrys.clear();
		}
		if (!this.dirtLeaveEntrys.isEmpty()) {
			packets.add(TeamProtocol.leaveTeamPacket(this, this.dirtLeaveEntrys));
			this.dirtLeaveEntrys.clear();
		}
	}

	@Override
	public SharedTeam addToPlayers(Player player, Player... players) {
		return super.addToPlayers(player, players);
	}

	@Override
	public void delete() {
		super.delete();
		this.entrys.clear();
		this.dirtJoinEntrys.clear();
		this.dirtLeaveEntrys.clear();
	}

	@Override
	public BaseComponent getDisplayName() {
		return this.displayName;
	}

	@Override
	public SharedTeam setDisplayName(BaseComponent... displayName) {
		this.displayName = ComponentUtil.mergeBaseComponent(displayName);
		this.displayNameWrapped = WrappedChatComponent.fromJson(ComponentSerializer.toString(this.displayName));
		this.setDirtyParameters();
		return this;
	}

	@Override
	public SharedTeam setDisplayName(String displayName) {
		return this.setDisplayName(new TextComponent(TextComponent.fromLegacyText(displayName)));
	}

	@Override
	public BaseComponent getPrefix() {
		return this.prefix;
	}

	@Override
	public SharedTeam setPrefix(BaseComponent... prefix) {
		this.prefix = ComponentUtil.mergeBaseComponent(prefix);
		this.prefixWrapped = WrappedChatComponent.fromJson(ComponentSerializer.toString(this.prefix));
		this.setDirtyParameters();
		return this;
	}

	@Override
	public SharedTeam setPrefix(String prefix) {
		return this.setPrefix(new TextComponent(TextComponent.fromLegacyText(prefix)));
	}

	@Override
	public BaseComponent getSuffix() {
		return this.suffix;
	}

	@Override
	public SharedTeam setSuffix(BaseComponent... suffix) {
		this.suffix = ComponentUtil.mergeBaseComponent(suffix);
		this.suffixWrapped = WrappedChatComponent.fromJson(ComponentSerializer.toString(this.suffix));
		this.setDirtyParameters();
		return this;
	}

	@Override
	public SharedTeam setSuffix(String suffix) {
		return this.setSuffix(new TextComponent(TextComponent.fromLegacyText(suffix)));
	}

	@Override
	public ChatColor getColor() {
		return this.color;
	}

	@Override
	public SharedTeam setColor(ChatColor color) {
		this.color = color;
		this.setDirtyParameters();
		return this;
	}

	@Override
	public TeamRule getNameTagVisiblity() {
		return this.nameTagVisiblity;
	}

	@Override
	public SharedTeam setNameTagVisiblity(TeamRule nameTagVisiblity) {
		this.nameTagVisiblity = nameTagVisiblity;
		this.setDirtyParameters();
		return this;
	}

	@Override
	public TeamRule getCollisionRule() {
		return this.collisionRule;
	}

	@Override
	public SharedTeam setCollisionRule(TeamRule collisionRule) {
		this.collisionRule = collisionRule;
		this.setDirtyParameters();
		return this;
	}

	@Override
	public boolean isCanSeeFriendlyInvisible() {
		return this.canSeeFriendlyInvisible;
	}

	@Override
	public SharedTeam setCanSeeFriendlyInvisible(boolean canSeeFriendlyInvisible) {
		this.canSeeFriendlyInvisible = canSeeFriendlyInvisible;
		this.setDirtyParameters();
		return this;
	}

	@Override
	public boolean isAllowFirendlyFire() {
		return this.isAllowFirendlyFire;
	}

	@Override
	public SharedTeam setAllowFirendlyFire(boolean isAllowFirendlyFire) {
		this.isAllowFirendlyFire = isAllowFirendlyFire;
		this.setDirtyParameters();
		return this;
	}

	@Override
	public Set<String> getEntrys() {
		return Collections.unmodifiableSet(this.entrys);
	}

	@Override
	public SharedTeam addEntry(Entity entity) {
		return this.addEntry(entity instanceof Player ? entity.getName() : entity.getUniqueId().toString());
	}

	@Override
	public SharedTeam addEntry(String entry) {
		this.entrys.add(entry);
		this.dirtJoinEntrys.add(entry);
		this.dirtLeaveEntrys.remove(entry);
		this.setDirty();
		return this;
	}

	@Override
	public SharedTeam removeEntry(Entity entity) {
		return this.removeEntry(entity instanceof Player ? entity.getName() : entity.getUniqueId().toString());
	}

	@Override
	public SharedTeam removeEntry(String entry) {
		this.entrys.remove(entry);
		this.dirtJoinEntrys.remove(entry);
		this.dirtLeaveEntrys.add(entry);
		this.setDirty();
		return this;
	}

	@Override
	public SharedTeam clearEntrys() {
		this.dirtLeaveEntrys.addAll(this.entrys);
		this.dirtJoinEntrys.clear();
		this.entrys.clear();
		this.setDirty();
		return this;
	}

	@Override
	public SharedTeam setEntrys(Set<String> entrys) {
		this.dirtJoinEntrys.addAll(this.entrys.stream().filter(entry -> !entrys.contains(entry)).toList());
		this.entrys = entrys;
		this.dirtLeaveEntrys.removeAll(this.entrys);
		this.setDirty();
		return this;
	}

	@Override
	public boolean hasEntry(Entity entity) {
		return this.hasEntry(entity instanceof Player ? entity.getName() : entity.getUniqueId().toString());
	}

	@Override
	public boolean hasEntry(String entry) {
		return this.entrys.contains(entry);
	}

	@Override
	public String getName() {
		return this.name;
	}

	void setDirtyParameters() {
		this.dirtyParameters = true;
		this.setDirty();
	}
}
