package de.ngloader.asps.core.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "asps_group_permission")
@IdClass(GroupPermissionId.class)
public class GroupPermission {

	@Id
	@Column(name = "group_id", columnDefinition = "INT(11) UNSIGNED")
	public int groupId;

	@Id
	@Column(name = "permission_id", columnDefinition = "INT(11) UNSIGNED")
	public int permissionId;
}
