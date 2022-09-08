package de.ngloader.asps.core.database.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import de.ngloader.asps.core.database.Expireable;

@Entity
@Table(name = "asps_group_user")
@IdClass(GroupUserId.class)
public class GroupUser implements Expireable {

	@Id
	@Column(name = "group_id", columnDefinition = "INT(11) UNSIGNED")
	public int groupId;

	@Id
	@Column(name = "user_id", columnDefinition = "INT(11) UNSIGNED")
	public int userId;

	@Column(name = "expire", columnDefinition = "DATETIME", nullable = true)
	public OffsetDateTime expire;

	public GroupUser() {
	}

	public GroupUser(int groupId, int userId, OffsetDateTime expire) {
		this.groupId = groupId;
		this.userId = userId;
		this.expire = expire;
	}

	@Override
	public OffsetDateTime getExpire() {
		return this.expire;
	}

	@Override
	public String toString() {
		return "GroupUser [groupId=" + groupId + ", userId=" + userId + ", expire=" + expire + "]";
	}
}
