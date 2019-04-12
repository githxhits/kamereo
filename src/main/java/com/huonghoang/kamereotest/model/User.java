package com.huonghoang.kamereotest.model;

import java.util.Set;

public class User implements Cloneable {

	private long id;
	private Set<Permission> permissions;

	public User() {
	}

	public User(long id) {
		this.id = id;
	}

	public User(long id, Set<Permission> permissions) {
		super();
		this.id = id;
		this.permissions = permissions;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
