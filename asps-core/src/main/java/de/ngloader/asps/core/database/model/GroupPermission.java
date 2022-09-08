package de.ngloader.asps.core.database.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import de.ngloader.asps.core.database.Expireable;

@Entity
@Table(name = "asps_group_permission")
@IdClass(GroupPermissionId.class)
public class GroupPermission implements Expireable {

	@Id
	@Column(name = "group_id", columnDefinition = "INT(11) UNSIGNED")
	public int groupId;

	@Id
	@Column(name = "permission", columnDefinition = "VARCHAR(128)")
	public String permission;

	@Column(name = "state", columnDefinition = "TINYINT(1)")
	public boolean state;

	@Column(name = "expire", columnDefinition = "DATETIME", nullable = true)
	public OffsetDateTime expire;

	public GroupPermission() {
	}

	public GroupPermission(int groupId, String permission, boolean state, OffsetDateTime expire) {
		this.groupId = groupId;
		this.permission = permission;
		this.state = state;
		this.expire = expire;
	}

	@Override
	public OffsetDateTime getExpire() {
		return this.expire;
	}

	@Override
	public String toString() {
		return "GroupPermission [groupId=" + groupId + ", permission=" + permission + ", state=" + state + ", expire="
				+ expire + "]";
	}
}
