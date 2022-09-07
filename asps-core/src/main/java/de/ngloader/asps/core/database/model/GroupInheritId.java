package de.ngloader.asps.core.database.model;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class GroupInheritId implements Serializable {

	protected int groupId;
	protected int inheritGroupId;

	public GroupInheritId() {
	}

	public GroupInheritId(int groupId, int inheritGroupId) {
		this.groupId = groupId;
		this.inheritGroupId = inheritGroupId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.groupId, this.inheritGroupId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof GroupInheritId))
			return false;
		GroupInheritId other = (GroupInheritId) obj;
		return this.groupId == other.groupId && this.inheritGroupId == other.inheritGroupId;
	}
}