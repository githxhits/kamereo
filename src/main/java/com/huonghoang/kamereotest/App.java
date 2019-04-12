package com.huonghoang.kamereotest;

import static com.huonghoang.kamereotest.config.Configuration.fileInput;

import com.huonghoang.kamereotest.data.Input;
import com.huonghoang.kamereotest.handler.FileHandler;
import com.huonghoang.kamereotest.handler.PermissionHandler;
import com.huonghoang.kamereotest.model.User;
import com.huonghoang.kamereotest.tree.Node;

/**
 * 
 * @author HuongHoang
 *
 */
public class App {
	public static void main(String[] args) {
		FileHandler file = new FileHandler();
		Input input = file.readFromFile(fileInput);
		PermissionHandler handler = new PermissionHandler();
		Node<User> tree = handler.createPermissionTree(input);
		handler.joinPermissionTree(tree);
		handler.handleQuery(input, tree, file);
	}

}
