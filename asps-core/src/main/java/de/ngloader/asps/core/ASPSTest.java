package de.ngloader.asps.core;

import de.ngloader.asps.core.sql.SqlConfig;

public class ASPSTest extends ASPS {

	public static void main(String[] args) {
		SqlConfig sqlConfig = new SqlConfig();
		sqlConfig.database = "asps";
		sqlConfig.host = "127.0.0.1";
		sqlConfig.username = "root";
		sqlConfig.password = "1234";
		new ASPS(sqlConfig);
	}

	public ASPSTest(SqlConfig sqlConfig) {
		super(sqlConfig);

		this.enable();
	}
}