package de.ngloader.asps.core.database.model;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class UserPermissionId implements Serializable {

	protected int permissionId;
	protected int userId;

	public UserPermissionId() {
	}

	public UserPermissionId(int permissionId, int userId) {
		this.permissionId = permissionId;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.permissionId, this.userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UserPermissionId))
			return false;
		UserPermissionId other = (UserPermissionId) obj;
		return this.permissionId == other.permissionId && this.userId == other.userId;
	}
}