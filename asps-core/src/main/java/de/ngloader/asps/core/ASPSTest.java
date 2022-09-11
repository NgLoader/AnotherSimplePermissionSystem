package de.ngloader.asps.core;

import java.util.Locale;
import java.util.UUID;

import de.ngloader.asps.core.database.model.User;
import de.ngloader.asps.core.database.model.UserPermission;
import de.ngloader.asps.core.sql.SqlConfig;

public class ASPSTest extends ASPS {

	public static void main(String[] args) {
		SqlConfig sqlConfig = new SqlConfig();
		sqlConfig.database = "asps";
		sqlConfig.host = "127.0.0.1";
		sqlConfig.username = "root";
		sqlConfig.password = "1234";

		new ASPSTest(sqlConfig);
	}

	public ASPSTest(SqlConfig sqlConfig) {
		super(sqlConfig);

		this.enable();

		UUID uuid = UUID.randomUUID();
		User user = new User(uuid, Locale.GERMANY);
		this.getUserHandler().createUser(user).whenComplete((result, error) -> {
			System.out.println("FINISHED 1" + result + error);
			this.getUserHandler().createUserPermission(new UserPermission(user.userId, "asps.*", true, null)).whenComplete((result2, error2) -> {
				System.out.println("FINISHED 2" + result2 + error2);
			});
		});
	}
}