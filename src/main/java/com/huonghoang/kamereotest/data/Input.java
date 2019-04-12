package com.huonghoang.kamereotest.data;

import java.util.ArrayList;
import java.util.List;

public class Input {

	private List<String> users;
	private List<String> permissions;
	private List<String> queries;

	public Input() {
		users = new ArrayList<>();
		permissions = new ArrayList<>();
		queries = new ArrayList<>();
	}

	public Input(List<String> users, List<String> permissions, List<String> queries) {
		super();
		this.users = users;
		this.permissions = permissions;
		this.queries = queries;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public List<String> getQueries() {
		return queries;
	}

	public void setQueries(List<String> queries) {
		this.queries = queries;
	}
}
