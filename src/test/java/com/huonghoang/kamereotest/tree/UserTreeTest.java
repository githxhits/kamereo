package com.huonghoang.kamereotest.tree;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.huonghoang.kamereotest.data.Input;
import com.huonghoang.kamereotest.model.User;
import com.huonghoang.kamereotest.util.TestUtils;
import com.huonghoang.kamereotest.utils.CommonUitl;

import junit.framework.TestCase;

public class UserTreeTest extends TestCase {

	private Node<User> tree;
	private UserTree userTree;

	public UserTreeTest(String testName) {
		super(testName);
		Input input = TestUtils.initData();
		userTree = new UserTree();
		tree = userTree.createTree(input);
	}

	@Test
	public void testCreateTree() {
		User actualUser = tree.getData();
		assertEquals("user id must be 0", 0L, actualUser.getId());
	}

	@Test
	public void testCreateNodePermission() {
		User actualUser = tree.getData();
		assertEquals("permission must be A,F", "A,F", CommonUitl.setToString(actualUser.getPermissions()));
	}

	@Test
	public void testPostorder() {
		Node<User> actual = userTree.postOrder(tree);
		assertEquals("root permission after join must be A,B,C,D,E,F", "A,B,C,D,E,F", CommonUitl.setToString(actual.getData().getPermissions()));
	}

	@Test
	public void testPfs() {
		List<String> results = userTree.dfs(tree);
		assertReflectionEquals("the list must be the same", new ArrayList<>(Arrays.asList("A,F", "A,B", "A,C,E", "A", "D", "A,C", "A,B")), results);
	}

	@Test
	public void testSearchNode() {
		Node<User> node = userTree.searchNode(tree, "5");
		assertEquals(5L, node.getData().getId());
	}
}
