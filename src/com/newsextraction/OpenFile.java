package com.newsextraction;
import java.awt.*;
import java.io.*;

public class OpenFile {

	public static void openSummary() {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop desktop = Desktop.getDesktop();
				File myFile = new File("C:\\Users\\User\\Desktop\\8th Semester\\Project\\NewsSearch\\Results\\summary.html");
				desktop.open(myFile);
			} catch (IOException ex) {
				
			}
		}
	}
}