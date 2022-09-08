package de.ngloader.asps.core.database;

import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.sql.SqlService;

public class PermissionHandler {

	public static void registerAnnotatedClasses(SqlService service) {
//		service.addAnnotatedClass(Permission.class);
	}

	private final SqlService sqlService;

	public PermissionHandler(ASPS plugin) {
		this.sqlService = plugin.getSqlService();
	}

//	public CompletionStage<List<Permission>> getPermissions() {
//		return this.sqlService.getSessionFactory().withSession((session) -> {
//			return this.sqlService.createQuery(Permission.class, (builder, criteria, from) -> {
//				criteria.select(from);
//				return session.createQuery(criteria);
//			}).getResultList();
//		});
//	}
//
//	public CompletionStage<Permission> getPermission(int permissionId) {
//		return this.sqlService.getSessionFactory().withSession((session) -> {
//			return session.find(Permission.class, permissionId);
//		});
//	}
//
//	public CompletionStage<Permission> getPermissionByPermission(String permission) {
//		return this.sqlService.getSessionFactory().withSession((session) -> {
//			return this.sqlService.createQuery(Permission.class, (builder, criteria, from) -> {
//				criteria.select(from).where(builder.equal(from.get(Permission_.PERMISSION), permission));
//				return session.createQuery(criteria);
//			}).getSingleResultOrNull();
//		});
//	}
//
//	public CompletionStage<Void> createPermission(Permission permission) {
//		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
//			return session.persist(permission);
//		});
//	}
//
//	public CompletionStage<Permission> updatePermission(Permission permission) {
//		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
//			return session.merge(permission);
//		});
//	}
//
//	public CompletionStage<Void> deletePermission(Permission permission) {
//		return this.sqlService.getSessionFactory().withTransaction((session, transaction) -> {
//			return session.remove(permission).thenAccept(v -> session.flush());
////			return session.find(Permission.class, permission.permissionId).thenAccept(permission2 -> session.remove(permission2));
//		});
//	}

	public SqlService getSqlService() {
		return this.sqlService;
	}
}
