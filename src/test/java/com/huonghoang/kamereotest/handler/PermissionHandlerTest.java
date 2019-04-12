package com.huonghoang.kamereotest.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.huonghoang.kamereotest.data.Input;
import com.huonghoang.kamereotest.model.Permission;
import com.huonghoang.kamereotest.model.User;
import com.huonghoang.kamereotest.tree.Node;
import com.huonghoang.kamereotest.tree.UserTree;
import com.huonghoang.kamereotest.util.TestUtils;
import com.huonghoang.kamereotest.utils.CommonUitl;

import junit.framework.TestCase;

public class PermissionHandlerTest extends TestCase {

	private Node<User> tree;
	private UserTree userTree;
	private PermissionHandler permissionHandler;

	public PermissionHandlerTest(String testName) {
		super(testName);
		Input input = TestUtils.initData();
		userTree = new UserTree();
		tree = userTree.createTree(input);
		permissionHandler = new PermissionHandler();
	}

	@Test
	public void testAddPermission() {
		List<String> permArr = Arrays.asList("Y");
		Node<User> root = permissionHandler.addPermission(tree, permArr, 0);
		assertEquals("the list must be the same", "A,F,Y", CommonUitl.setToString(root.getData().getPermissions()));
	}

	@Test
	public void testRemovePermissionDown() {
		List<String> permArr = Arrays.asList("B");
		Node<User> root = permissionHandler.removePermissionDown(tree, permArr, 0);
		assertEquals("the list must be the same", "A",CommonUitl.setToString(root.getChildren().get(0).getData().getPermissions()));
	}

	@Test
	public void testRemovePermissionUp() {
		Set<Permission> permSet = new HashSet<>();
		Permission perm = new Permission(0, "A");
		permSet.add(perm);
		Node<User> root = permissionHandler.removePermissionUp(tree, permSet, 0);
		assertEquals("the list must be the same", "F", CommonUitl.setToString(root.getData().getPermissions()));
	}

}
