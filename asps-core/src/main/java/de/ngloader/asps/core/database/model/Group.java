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

	@OneToMany(mappedBy = "groupId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Group> inherit;

	@OneToMany(mappedBy = "groupId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<User> users;
}