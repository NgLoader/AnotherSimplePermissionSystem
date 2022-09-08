package de.ngloader.asps.core.database.model;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class GroupPermissionId implements Serializable {

	public int groupId;

	public String permission;

	public GroupPermissionId() {
	}

	public GroupPermissionId(int groupId, String permission) {
		this.groupId = groupId;
		this.permission = permission;
	}

	@Override
	public int hashCode() {
		return Objects.hash(permission, groupId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof GroupPermissionId))
			return false;
		GroupPermissionId other = (GroupPermissionId) obj;
		return Objects.equals(permission, other.permission) && groupId == other.groupId;
	}
}