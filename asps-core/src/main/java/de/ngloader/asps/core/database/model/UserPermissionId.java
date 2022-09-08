package de.ngloader.asps.core.database.model;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class UserPermissionId implements Serializable {

	public int userId;

	public String permission;

	public UserPermissionId() {
	}

	public UserPermissionId(int userId, String permission) {
		this.userId = userId;
		this.permission = permission;
	}

	@Override
	public int hashCode() {
		return Objects.hash(permission, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UserPermissionId))
			return false;
		UserPermissionId other = (UserPermissionId) obj;
		return Objects.equals(permission, other.permission) && userId == other.userId;
	}
}