package de.ngloader.asps.core.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "asps_user_permission")
@IdClass(UserPermissionId.class)
public class UserPermission {

	@Id
	@Column(name = "user_id", columnDefinition = "INT(11) UNSIGNED")
	public int userId;

	@Id
	@Column(name = "permission_id", columnDefinition = "INT(11) UNSIGNED")
	public int permissionId;
}
