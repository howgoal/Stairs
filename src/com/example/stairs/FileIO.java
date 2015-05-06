package com.example.stairs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.R.integer;

public class FileIO {
	String readData = "";

	public void writeFile(String fileName, ArrayList<Integer> data) {
		try {
			FileWriter fw = new FileWriter("/sdcard/" + fileName, false);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < data.size(); i++) {
				bw.write(data.get(i)+",");
			}
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readFile(String fileName) {
		try {
			FileReader fr = new FileReader("/sdcard/" + fileName);
			BufferedReader br = new BufferedReader(fr);
			while (br.ready()) {
				readData += br.readLine() + " ";
			}
			fr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return readData;
	}
}
