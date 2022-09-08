package de.ngloader.asps.core;

import de.ngloader.asps.core.database.GroupHandler;
import de.ngloader.asps.core.database.UserHandler;
import de.ngloader.asps.core.sql.SqlConfig;
import de.ngloader.asps.core.sql.SqlService;

public class ASPS {

	private SqlService sqlService;

	private UserHandler userHandler;
	private GroupHandler groupHandler;

	public ASPS(SqlConfig sqlConfig) {
		this.sqlService = new SqlService(sqlConfig);

		UserHandler.registerAnnotatedClasses(this.sqlService);
		GroupHandler.registerAnnotatedClasses(this.sqlService);

		this.sqlService.initialize();
	}

	public void enable() {
		this.userHandler = new UserHandler(this);
		this.groupHandler = new GroupHandler(this);
	}

	public void disable() {
		//TODO finish all transaction before!
		try {
			this.sqlService.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UserHandler getUserHandler() {
		return this.userHandler;
	}

	public GroupHandler getGroupHandler() {
		return this.groupHandler;
	}

	public SqlService getSqlService() {
		return this.sqlService;
	}
}
