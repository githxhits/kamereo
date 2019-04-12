package com.huonghoang.kamereotest.util;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.huonghoang.kamereotest.data.Input;
import com.huonghoang.kamereotest.model.User;
import com.huonghoang.kamereotest.tree.Node;
import com.huonghoang.kamereotest.tree.UserTree;

public class TestUtils {

	public static UserTree userTree;
	public static Node<User> tree;
	
	static {
		Input input = initData();
		userTree = new UserTree();
		tree = userTree.createTree(input);
	}

	public static Input initData() {
		List<String> users = new ArrayList<>(Arrays.asList("CEO", "CEO", "1", "1", "1", "2"));
		List<String> permissions = new ArrayList<>(Arrays.asList("A F", "A B", "A C E", "A", "D", "A C", "A B"));
		List<String> queries = new ArrayList<>(Arrays.asList("Add 2 5", "QUERY 2", "REMOVE 2 A C E", "QUERY 2"));
		return new Input(users, permissions, queries);
	}
}
