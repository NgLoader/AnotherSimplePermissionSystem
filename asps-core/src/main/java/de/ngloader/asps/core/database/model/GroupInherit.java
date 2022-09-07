package de.ngloader.asps.core.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "asps_group_inherit")
@IdClass(GroupInheritId.class)
public class GroupInherit {

	@Id
	@Column(name = "group_id", columnDefinition = "INT(11) UNSIGNED")
	public int groupId;

	@Id
	@Column(name = "inherit_group_id", columnDefinition = "INT(11) UNSIGNED")
	public int inheritGroupId;
}
