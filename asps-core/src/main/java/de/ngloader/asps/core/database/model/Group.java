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
import javax.persistence.Table;

@Entity
@Table(name = "asps_group")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int groupId;

	@Column(name = "name", length = 64, nullable = false)
	public String name;

	@Column(name = "prefix", length = 64, nullable =  true)
	public String prefix;

	@Column(name = "standard", columnDefinition = "TINYINT(1)")
	public boolean standard;

	@OneToMany(mappedBy = "groupId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<GroupUser> users;

	@OneToMany(mappedBy = "groupId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<GroupPermission> permissions;

	public Group() {
	}

	public Group(String name, String prefix) {
		this.name = name;
		this.prefix = prefix;
	}

	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", name=" + name + ", prefix=" + prefix + "]";
	}
}