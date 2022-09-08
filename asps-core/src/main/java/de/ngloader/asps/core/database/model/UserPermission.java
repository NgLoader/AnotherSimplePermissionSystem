package de.ngloader.asps.core.database.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import de.ngloader.asps.core.database.Expireable;

@Entity
@Table(name = "asps_user_permission")
@IdClass(UserPermissionId.class)
public class UserPermission implements Expireable {

	@Id
	@Column(name = "user_id", columnDefinition = "INT(11) UNSIGNED")
	public int userId;

	@Id
	@Column(name = "permission", columnDefinition = "VARCHAR(128)")
	public String permission;

	@Column(name = "state", columnDefinition = "TINYINT(1)")
	public boolean state;

	@Column(name = "expire", columnDefinition = "DATETIME", nullable = true)
	public OffsetDateTime expire;

	public UserPermission() {
	}

	public UserPermission(int userId, String permission, boolean state, OffsetDateTime expire) {
		this.userId = userId;
		this.permission = permission;
		this.state = state;
		this.expire = expire;
	}

	public UserPermission(User user, String permission, boolean state, OffsetDateTime expire) {
		this(user.userId, permission, state, expire);
	}

	@Override
	public OffsetDateTime getExpire() {
		return this.expire;
	}

	@Override
	public String toString() {
		return "UserPermission [userId=" + userId + ", permission=" + permission + ", state=" + state + ", expire="
				+ expire + "]";
	}
}
