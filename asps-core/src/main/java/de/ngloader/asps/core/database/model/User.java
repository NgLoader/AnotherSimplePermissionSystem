package de.ngloader.asps.core.database.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "asps_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", columnDefinition = "INT(11) UNSIGNED")
	public int userId;

	@Column(name = "uuid", columnDefinition = "BINARY(16)", nullable = false)
	public UUID uuid;

	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<GroupUser> groups;

	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<UserPermission> permissions;

	public User() {
	}

	public User(int userId, UUID uuid, Set<GroupUser> groups, Set<UserPermission> permissions) {
		this.userId = userId;
		this.uuid = uuid;
		this.groups = groups;
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", uuid=" + uuid + "]";
	}
}
