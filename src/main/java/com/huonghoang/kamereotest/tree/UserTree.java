package com.huonghoang.kamereotest.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.huonghoang.kamereotest.data.Input;
import com.huonghoang.kamereotest.model.User;
import com.huonghoang.kamereotest.utils.CommonUitl;
import com.huonghoang.kamereotest.utils.ConstantUtil;

public class UserTree {

	/**
	 * Create tree
	 * 
	 * @param input
	 * @return Node<User>
	 */
	public Node<User> createTree(Input input) {
		Node<User> root = new Node<>(CommonUitl.createUser(input.getPermissions().get(0), 0L));
		Map<Integer, Node<User>> nodes = new HashMap<>();
		int i = 1;
		for (String user : input.getUsers()) {
			if (ConstantUtil.CEO.equalsIgnoreCase(user)) {
				Node<User> ceoNode = new Node<User>(
						CommonUitl.createUser(input.getPermissions().get(i), Long.valueOf(i)));
				Node<User> ceo = root.setChild(ceoNode);
				nodes.put(i, ceo);
				i++;
			} else {
				int id = Integer.valueOf(user);
				Node<User> node = nodes.get(id);
				if (null != node) {
					Node<User> newNode = new Node<User>(
							CommonUitl.createUser(input.getPermissions().get(i), Long.valueOf(i)));
					Node<User> staff = node.setChild(newNode);
					nodes.put(i, staff);
					i++;
				}
			}
		}
		return root;
	}

	/**
	 * join permission at root node.
	 * 
	 * @param node
	 * @return Node<User>
	 */
	public Node<User> postOrder(Node<User> node) {
		User entity = node.getData();
		for (Node<User> childNode : node.getChildren()) {
			postOrder(childNode);
			entity.getPermissions().addAll(childNode.getData().getPermissions());
		}
		return node;
	}

	/**
	 * traversal dfs.
	 * 
	 * @param node
	 * @return List<String>
	 */
	public List<String> dfs(Node<User> node) {
		List<String> results = new ArrayList<>();
		Queue<Node<User>> queue = new LinkedList<Node<User>>();
		queue.add(node);
		node.setVisited(true);
		while (!queue.isEmpty()) {
			Node<User> element = queue.remove();
			String permStr = CommonUitl.setToString(element.getData().getPermissions());
			results.add(permStr);
			List<Node<User>> neighbours = element.getChildren();
			for (int i = 0; i < neighbours.size(); i++) {
				Node<User> n = neighbours.get(i);
				if (n != null && !n.isVisited()) {
					queue.add(n);
					n.setVisited(true);
				}
			}
		}
		return results;
	}

	/**
	 * search node by node name.
	 * 
	 * @param tree
	 * @param nodeName
	 * @return Node<User>
	 */
	public Node<User> searchNode(Node<User> tree, String nodeName) {
		Node<User> result = null;
		User user = tree.getData();
		if (String.valueOf(user.getId()).equals(nodeName)) {
			result = tree;
		} else {
			List<Node<User>> child = tree.getChildren();
			for (Node<User> item : child) {
				result = searchNode(item, nodeName);
				if (result != null) {
					break;
				}
			}
		}
		return result;
	}

}
