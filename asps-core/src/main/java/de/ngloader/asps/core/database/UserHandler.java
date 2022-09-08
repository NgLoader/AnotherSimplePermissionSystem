package de.ngloader.asps.core.database;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.database.model.Group;
import de.ngloader.asps.core.database.model.User;
import de.ngloader.asps.core.database.model.UserPermission;
import de.ngloader.asps.core.database.model.UserPermission_;
import de.ngloader.asps.core.database.model.User_;
import de.ngloader.asps.core.sql.SqlService;

public class UserHandler {

	public static void registerAnnotatedClasses(SqlService service) {
		service.addAnnotatedClass(User.class);
		service.addAnnotatedClass(UserPermission.class);
	}

	private final SqlService sqlService;

	public UserHandler(ASPS plugin) {
		this.sqlService = plugin.getSqlService();
	}

	public CompletionStage<List<User>> getUsers() {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return this.sqlService.createQuery(User.class, (builder, criteria, from) -> {
				criteria.select(from);
				return session.createQuery(criteria);
			}).getResultList();
		});
	}

	public CompletionStage<User> getUser(int userId) {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return session.find(User.class, userId);
		});
	}

	public CompletionStage<User> getUserByUUID(UUID uuid) {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return this.sqlService.createQuery(User.class, (builder, criteria, from) -> {
				criteria.select(from).where(builder.equal(from.get(User_.UUID), uuid));
				return session.createQuery(criteria);
			}).getSingleResultOrNull();
		});
	}

	public CompletionStage<Void> createUser(User user) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.persist(user);
		});
	}

	public CompletionStage<User> updateUser(User user) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.merge(user);
		});
	}

	public CompletionStage<Void> deleteUser(User user) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.find(User.class, user.userId).thenAccept(dbUser -> session.remove(dbUser));
		});
	}

	public CompletionStage<List<UserPermission>> getUserPermissions(User user) {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return this.sqlService.createQuery(UserPermission.class, (builder, criteria, from) -> {
				criteria.select(from).where(builder.equal(from.get(UserPermission_.USER_ID), user.userId));
				return session.createQuery(criteria);
			}).getResultList();
		});
	}

	public CompletionStage<Void> createUserPermission(UserPermission userPermission) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.persist(userPermission);
		});
	}

	public CompletionStage<UserPermission> updateUserPermission(UserPermission userPermission) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.merge(userPermission);
		});
	}

	public CompletionStage<Void> deleteUserPermission(UserPermission userPermission) {
		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
			return session.find(Group.class, userPermission.permission).thenAccept(dbPerission -> session.remove(dbPerission));
		});
	}

	public SqlService getSqlService() {
		return this.sqlService;
	}
}
