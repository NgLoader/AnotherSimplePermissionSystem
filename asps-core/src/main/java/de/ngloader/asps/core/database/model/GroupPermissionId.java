package de.ngloader.asps.core.database.model;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class GroupPermissionId implements Serializable {

	protected int permissionId;
	protected int groupId;

	public GroupPermissionId() {
	}

	public GroupPermissionId(int permissionId, int groupId) {
		this.permissionId = permissionId;
		this.groupId = groupId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.permissionId, this.groupId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof GroupPermissionId))
			return false;
		GroupPermissionId other = (GroupPermissionId) obj;
		return this.permissionId == other.permissionId && this.groupId == other.groupId;
	}
}