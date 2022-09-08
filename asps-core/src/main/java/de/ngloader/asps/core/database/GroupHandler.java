package de.ngloader.asps.core.database;

import de.ngloader.asps.core.ASPS;
import de.ngloader.asps.core.database.model.Group;
import de.ngloader.asps.core.database.model.GroupPermission;
import de.ngloader.asps.core.database.model.GroupUser;
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

	public SqlService getSqlService() {
		return this.sqlService;
	}
}
