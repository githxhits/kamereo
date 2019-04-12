package com.huonghoang.kamereotest.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.huonghoang.kamereotest.data.Input;

public class FileHandler {

	public Input readFromFile(String fileInput) {
		Input input = new Input();
		try {
			FileReader fileReader = new FileReader(new File(fileInput));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			long lineNumber = 0;
			long total = Long.parseLong(bufferedReader.readLine());
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber++;
				if (lineNumber <= (total + 1)) {
					input.getPermissions().add(line.trim());
				} else if (lineNumber <= (total * 2 + 1)) {
					input.getUsers().add(line.trim());
				} else {
					if(!line.isEmpty()) {
						input.getQueries().add(line.trim());
					}
				}
			}
			fileReader.close();
			return input;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeToFile(List<String> inputs, String fileOutput) {
		try {
			File file = new File(fileOutput);
			file.createNewFile();
			PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for (String value : inputs) {
				pr.println(value);
			}
			pr.close();
			System.out.println("Check result at: "+fileOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
