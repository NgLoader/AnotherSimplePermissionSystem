package de.ngloader.asps.core.database.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "asps_permission")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asps_user_permission_permission_sequence")
	@SequenceGenerator(name = "asps_user_permission_permission_sequence", sequenceName = "asps_user_permission_permission_sequence", allocationSize = 20)
	@Column(name = "permission_id", columnDefinition = "INT(11) UNSIGNED")
	public int permissionId;

	@Column(name = "permission", columnDefinition = "VARCHAR(512)", nullable = false)
	public String permission;

	@OneToMany(mappedBy = "groupId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<GroupPermission> groups;

	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<UserPermission> users;
}