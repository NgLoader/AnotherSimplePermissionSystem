package de.ngloader.asps.core.database;

import java.util.List;
import java.util.concurrent.CompletionStage;

import de.ngloader.asps.core.database.model.Group;
import de.ngloader.asps.core.database.model.GroupInherit;
import de.ngloader.asps.core.database.model.GroupPermission;
import de.ngloader.asps.core.database.model.GroupUser;
import de.ngloader.asps.core.database.model.Permission;
import de.ngloader.asps.core.database.model.Permission_;
import de.ngloader.asps.core.database.model.User;
import de.ngloader.asps.core.database.model.UserPermission;
import de.ngloader.asps.core.sql.SqlService;

public class PermissionHandler {

	public static void registerAnnotatedClasses(SqlService service) {
		service.addAnnotatedClass(Permission.class);
		service.addAnnotatedClass(User.class);
		service.addAnnotatedClass(UserPermission.class);
		service.addAnnotatedClass(Group.class);
		service.addAnnotatedClass(GroupUser.class);
		service.addAnnotatedClass(GroupInherit.class);
		service.addAnnotatedClass(GroupPermission.class);
	}

	private final SqlService sqlService;

	public PermissionHandler(SqlService sqlService) {
		this.sqlService = sqlService;
	}

	public CompletionStage<List<Permission>> getPermissions() {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return this.sqlService.createQuery(Permission.class, (builder, criteria, from) -> {
				criteria.select(from);
				return session.createQuery(criteria);
			}).getResultList();
		});
	}

	public CompletionStage<Permission> getPermission(int permissionId) {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return session.find(Permission.class, permissionId);
		});
	}

	public CompletionStage<Permission> getPermissionByPermission(String permission) {
		return this.sqlService.getSessionFactory().withSession((session) -> {
			return this.sqlService.createQuery(Permission.class, (builder, criteria, from) -> {
				criteria.select(from).where(builder.equal(from.get(Permission_.PERMISSION), permission));
				return session.createQuery(criteria);
			}).getSingleResultOrNull();
		});
	}

	public SqlService getSqlService() {
		return this.sqlService;
	}
}
