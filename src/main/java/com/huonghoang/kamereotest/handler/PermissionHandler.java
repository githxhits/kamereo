package com.huonghoang.kamereotest.handler;

import static com.huonghoang.kamereotest.config.Configuration.fileOutputPermission;
import static com.huonghoang.kamereotest.config.Configuration.fileOutputQuerySelect;
import static com.huonghoang.kamereotest.utils.ConstantUtil.QUERY_ADD;
import static com.huonghoang.kamereotest.utils.ConstantUtil.QUERY_REMOVE;
import static com.huonghoang.kamereotest.utils.ConstantUtil.QUERY_SELECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.huonghoang.kamereotest.data.Input;
import com.huonghoang.kamereotest.model.Permission;
import com.huonghoang.kamereotest.model.User;
import com.huonghoang.kamereotest.tree.Node;
import com.huonghoang.kamereotest.tree.UserTree;
import com.huonghoang.kamereotest.utils.CommonUitl;

public class PermissionHandler {

	private UserTree permissionTree = new UserTree();

	public Node<User> createPermissionTree(Input input) {
		Node<User> tree = permissionTree.createTree(input);
		return tree;
	}

	public void joinPermissionTree(Node<User> tree) {
		permissionTree.postOrder(tree);
	}

	public List<String> getResults(Node<User> tree) {
		List<String> results = permissionTree.dfs(tree);
		return results;
	}

	private void removePermission(Node<User> root, String nodeName, List<String> permArr) {
		Node<User> node = permissionTree.searchNode(root, nodeName);
		if (node != null) {
			Set<Permission> permSet = new HashSet<>();
			for (String permItem : permArr) {
				List<Permission> permList = node.getData().getPermissions().stream()
						.filter(p -> p.getName().equals(permItem)).collect(Collectors.toList());
				permSet.addAll(permList);
			}
			removePermissionDown(node, permArr, node.getData().getId());
			removePermissionUp(node, permSet, node.getData().getId());
		}
	}

	private void addPermission(Node<User> root, String nodeName, List<String> permArr) {
		Node<User> node = permissionTree.searchNode(root, nodeName);
		if (node != null) {
			addPermission(node, permArr, node.getData().getId());
		}
	}

	private String getQueryResult(Node<User> root, String nodeName) {
		Node<User> node = permissionTree.searchNode(root, nodeName);
		if (node != null) {
			return CommonUitl.setToString(node.getData().getPermissions());
		}
		return "";
	}

	public void handleQuery(Input input, Node<User> root, FileHandler file) {
		if (!input.getQueries().isEmpty()) {
			List<String> results = new ArrayList<String>();
			for (String query : input.getQueries()) {
				List<String> operators = Arrays.asList(query.split(" "));
				String operator = operators.get(0).trim().toUpperCase();
				String nodeName = operators.get(1).trim();
				List<String> permissions = operators.subList(2, operators.size());
				switch (operator) {
				case QUERY_ADD:
					addPermission(root, nodeName, permissions);
					break;
				case QUERY_REMOVE:
					removePermission(root, nodeName, permissions);
					break;
				case QUERY_SELECT:
					results.add(getQueryResult(root, nodeName));
					break;
				}
			}
			if (!results.isEmpty()) {
				file.writeToFile(results, fileOutputQuerySelect);
				
			}
		}
		file.writeToFile(getResults(root), fileOutputPermission);
	}

	public Node<User> removePermissionUp(Node<User> node, Set<Permission> permArr, long id) {
		Set<Permission> permSet = node.getData().getPermissions();
		List<Permission> permList = new ArrayList<Permission>(permArr);
		for (Permission permStr : permList) {
			Optional<Permission> isPresent = permSet.stream().filter(p -> p.getName().equals(permStr.getName()) && permStr.getUserId() == p.getUserId()).findFirst();
			if (isPresent.isPresent()) {
				permSet.remove(isPresent.get());
			}
		}
		node.getData().setPermissions(permSet);
		Node<User> root = node.getParent();
		if (root == null) {
			return node;
		}
		return removePermissionUp(root, new HashSet<>(permList), id);
	}

	public Node<User> removePermissionDown(Node<User> node, List<String> permArr, long id) {
		if (node.getChildren().isEmpty()) {
			return node;
		}
		node.getChildren().forEach(nodeItem -> {
			Set<Permission> permSet = nodeItem.getData().getPermissions();
			for (String perm : permArr) {
				List<Permission> perms = permSet.stream().filter(p -> p.getName().equals(perm))
						.collect(Collectors.toList());
				if (!perms.isEmpty()) {
					permSet.removeAll(perms);
				}
			}
			nodeItem.getData().setPermissions(permSet);
			removePermissionDown(nodeItem, permArr, id);
		});
		return node;
	}

	public Node<User> addPermission(Node<User> node, List<String> permArr, long id) {
		Set<Permission> permSet = node.getData().getPermissions();
		List<Permission> permList = new ArrayList<Permission>(permSet);
		List<Permission> permIdList = permList.stream().filter(p -> p.getUserId() == id).collect(Collectors.toList());
		if (!permIdList.isEmpty()) {
			for (String permStr : permArr) {
				boolean isPresent = permIdList.stream().filter(p -> p.getName().equals(permStr)).findFirst().isPresent();
				if (!isPresent) {
					Permission perm = new Permission(id, permStr);
					permList.add(perm);
				}
			}
		} else {
			for (String permStr : permArr) {
				permList.add(new Permission(id, permStr));
			}
		}
		permSet = new HashSet<Permission>(permList);
		node.getData().setPermissions(permSet);
		Node<User> root = node.getParent();
		if (root == null) {
			return node;
		}
		return addPermission(root, permArr, id);
	}

}
