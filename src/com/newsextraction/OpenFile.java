/* 	Contextual Query-Driven News Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
*/
package com.newsextraction;
import java.awt.*;
import java.io.*;

public class OpenFile {

	//Function to open summary page in a new browser tab
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