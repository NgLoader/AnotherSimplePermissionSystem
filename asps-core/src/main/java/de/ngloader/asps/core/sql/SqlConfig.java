package de.ngloader.asps.core.sql;

/**
 * @author Ingrim4
 */
public class SqlConfig {

	public String host;
	public String database;
	public String username;
	public String password;
	public String debug;

	public SqlConfig() {
	}

	public SqlConfig(String host, String database, String username, String password, String debug) {
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
		this.debug = debug;
	}
}
