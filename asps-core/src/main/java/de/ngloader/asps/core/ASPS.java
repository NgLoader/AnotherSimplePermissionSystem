package de.ngloader.asps.core;

import de.ngloader.asps.core.sql.SqlConfig;
import de.ngloader.asps.core.sql.SqlService;

public class ASPS {

	private SqlService sqlService;

	public ASPS(SqlConfig sqlConfig) {
		this.sqlService = new SqlService(sqlConfig);
	}

	public SqlService getSqlService() {
		return this.sqlService;
	}
}
