package de.ngloader.asps.core.database.model;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class GroupUserId implements Serializable {

	protected int groupId;
	protected int userId;

	public GroupUserId() {
	}

	public GroupUserId(int groupId, int userId) {
		this.groupId = groupId;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.groupId, this.userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof GroupUserId))
			return false;
		GroupUserId other = (GroupUserId) obj;
		return this.groupId == other.groupId && this.userId == other.userId;
	}
}