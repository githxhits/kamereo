package com.huonghoang.kamereotest.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> implements Cloneable {

	private T data = null;
	private List<Node<T>> children = new ArrayList<>();
	private Node<T> parent = null;
	private boolean visited;

	public Node(T data) {
		this.data = data;
	}

	public Node<T> setChild(Node<T> child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Node<T>> children) {
		children.forEach(each -> each.setParent(this));
		this.children.addAll(children);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public Node<T> getParent() {
		return parent;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}