package de.ngloader.asps.api.bukkit.protocol.team;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

import de.ngloader.asps.api.bukkit.protocol.ProtocolBase;
import net.md_5.bungee.api.chat.BaseComponent;

public interface Team extends ProtocolBase {

	BaseComponent getDisplayName();

	Team setDisplayName(BaseComponent... displayName);

	Team setDisplayName(String displayName);

	BaseComponent getPrefix();

	Team setPrefix(BaseComponent... prefix);

	Team setPrefix(String prefix);

	BaseComponent getSuffix();

	Team setSuffix(BaseComponent... suffix);

	Team setSuffix(String suffix);

	ChatColor getColor();

	Team setColor(ChatColor color);

	TeamRule getNameTagVisiblity();

	Team setNameTagVisiblity(TeamRule nameTagVisiblity);

	TeamRule getCollisionRule();

	Team setCollisionRule(TeamRule collisionRule);

	boolean isCanSeeFriendlyInvisible();

	Team setCanSeeFriendlyInvisible(boolean canSeeFriendlyInvisible);

	boolean isAllowFirendlyFire();

	Team setAllowFirendlyFire(boolean isAllowFirendlyFire);

	Set<String> getEntrys();

	Team addEntry(Entity entity);

	Team addEntry(String entry);

	Team removeEntry(Entity entity);

	Team removeEntry(String entry);

	Team clearEntrys();

	Team setEntrys(Set<String> entrys);

	boolean hasEntry(Entity entity);

	boolean hasEntry(String entry);

	String getName();

}