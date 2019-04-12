package com.huonghoang.kamereotest.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.huonghoang.kamereotest.model.Permission;
import com.huonghoang.kamereotest.model.User;

public class CommonUitl {

	public static User createUser(String permStr, long id) {
		String[] permArrs = permStr.split(" ");
		Set<Permission> permSet = new HashSet();
		for (String item : permArrs) {
			Permission permission = new Permission(id, item);
			permSet.add(permission);
		}
		User user = new User(id, permSet);
		return user;
	}

	public static String setToString(Set<Permission> permSet) {
		String permStr = "";
		Set<String> results = new HashSet<>();
		Iterator<Permission> iterator = permSet.iterator();
		while (iterator.hasNext()) {
			Permission perm = iterator.next();
			results.add(perm.getName());
		}
		permStr = String.join(",", results);
		return permStr;
	}

}
