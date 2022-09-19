package de.ngloader.asps.core.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.database.GroupHandler;
import de.ngloader.asps.core.database.model.Group;

public class GroupManager implements Runnable {

	private final GroupHandler groupHandler;

	private final List<ASPSGroup> groups = new ArrayList<>();
	private final Map<String, ASPSGroup> groupByName = new ConcurrentHashMap<>();
	private final Map<Integer, ASPSGroup> groupById = new ConcurrentHashMap<>();

	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	public GroupManager(ASPS plugin) {
		this.groupHandler = plugin.getGroupHandler();
	}

	@Override
	public void run() {
		for (Iterator<ASPSGroup> iterator = this.groups.iterator(); iterator.hasNext(); ) {
			ASPSGroup group = iterator.next();
			group.run();
		}
	}

	public CompletionStage<Void> loadAllGroups() {
		CompletableFuture<Void> future = new CompletableFuture<>();
		this.groupHandler.getGroups().whenComplete((groups, error) -> {
			if (error != null) {
				future.completeExceptionally(error);
				return;
			}

			try {
				this.lock.writeLock().lock();

				for (Group group : groups) {
					ASPSGroup aspsGroup	= new ASPSGroup(this, group);
					this.groups.add(aspsGroup);
					this.groupById.put(group.groupId, aspsGroup);
					this.groupByName.put(group.name.toLowerCase(), aspsGroup);
				}
			} finally {
				this.lock.writeLock().unlock();
			}
		});
		return future;
	}

	public CompletionStage<ASPSGroup> getGroup(String groupName) {
		try {
			this.lock.readLock().lock();
			return CompletableFuture.completedFuture(this.groupByName.get(groupName));
		} finally {
			this.lock.readLock().unlock();
		}
	}

	public CompletionStage<ASPSGroup> getGroup(int groupId) {
		try {
			this.lock.readLock().lock();
			return CompletableFuture.completedFuture(this.groupById.get(groupId));
		} finally {
			this.lock.readLock().unlock();
		}
	}
}