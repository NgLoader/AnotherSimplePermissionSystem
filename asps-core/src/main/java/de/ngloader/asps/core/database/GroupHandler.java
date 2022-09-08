package de.ngloader.asps.core.database;

import java.util.List;
import java.util.concurrent.CompletionStage;

import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.database.model.Group;
import de.ngloader.asps.core.database.model.GroupPermission;
import de.ngloader.asps.core.database.model.GroupPermission_;
import de.ngloader.asps.core.database.model.GroupUser;
import de.ngloader.asps.core.database.model.Group_;
import de.ngloader.asps.core.sql.SqlService;

public class GroupHandler {

	public static void registerAnnotatedClasses(SqlService service) {
		service.addAnnotatedClass(Group.class);
		service.addAnnotatedClass(GroupUser.class);
		service.addAnnotatedClass(GroupPermission.class);
	}

	private final SqlService sqlService;

	public GroupHandler(ASPS plugin) {
		this.sqlService = plugin.getSqlService();
	}

	public CompletionStage<List<Group>> getGroups() {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return this.sqlService.createQuery(Group.class, (builder, criteria, from) -> {
				criteria.select(from);
				return session.createQuery(criteria);
			}).getResultList();
		});
	}

	public CompletionStage<Group> getGroup(int groupId) {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return session.find(Group.class, groupId);
		});
	}

	public CompletionStage<Group> getGroupByName(String name) {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return this.sqlService.createQuery(Group.class, (builder, criteria, from) -> {
				criteria.select(from).where(builder.equal(from.get(Group_.NAME), name));
				return session.createQuery(criteria);
			}).getSingleResultOrNull();
		});
	}

	public CompletionStage<Void> createGroup(Group group) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.persist(group);
		});
	}

	public CompletionStage<Group> updateGroup(Group group) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.merge(group);
		});
	}

	public CompletionStage<Void> deleteGroup(Group group) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.find(Group.class, group.groupId).thenAccept(dbGroup -> session.remove(dbGroup));
		});
	}

	public CompletionStage<Void> createGroupUser(GroupUser groupUser) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.persist(groupUser);
		});
	}

	public CompletionStage<GroupUser> updateGroupUser(GroupUser groupUser) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.merge(groupUser);
		});
	}

	public CompletionStage<Void> deleteGroupUser(GroupUser groupUser) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.find(Group.class, groupUser.groupId).thenAccept(dbUser -> session.remove(dbUser));
		});
	}

	public CompletionStage<List<GroupPermission>> getGroupPermissions(Group group) {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return this.sqlService.createQuery(GroupPermission.class, (builder, criteria, from) -> {
				criteria.select(from).where(builder.equal(from.get(GroupPermission_.GROUP_ID), group.groupId));
				return session.createQuery(criteria);
			}).getResultList();
		});
	}

	public CompletionStage<Void> createGroupPermission(GroupPermission groupPermission) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.persist(groupPermission);
		});
	}

	public CompletionStage<GroupPermission> updateGroupPermission(GroupPermission groupPermission) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.merge(groupPermission);
		});
	}

	public CompletionStage<Void> deleteGroupPermission(GroupPermission groupPermission) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.find(Group.class, groupPermission.groupId).thenAccept(dbPermission -> session.remove(dbPermission));
		});
	}

	public SqlService getSqlService() {
		return this.sqlService;
	}
}
