package de.ngloader.asps.core.database;

import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.database.model.User;
import de.ngloader.asps.core.database.model.UserPermission;
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

	public SqlService getSqlService() {
		return this.sqlService;
	}
}
