/* 	News Extraction and Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
*/
package com.newsextraction;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class RepoConnector {

	final static Charset ENCODING = StandardCharsets.UTF_8;
	static String filePath = "C:\\Users\\User\\Desktop\\8th Semester\\Project\\NewsHtmlFiles\\";
	
	//Function to read the contents of a file given its path
	static String readFile(String aFileName) throws IOException {
		String fileContents = new String();
		Path path = Paths.get(aFileName);
		try (Scanner scanner =  new Scanner(path, ENCODING.name())) {
			while (scanner.hasNextLine()){
				fileContents = fileContents.concat(scanner.nextLine());
			}      
		}
		return fileContents;
	}
	
	//Function to fetch the HTML file from file repository given the article ID
	public static String getHtmlGivenId(int aId) {
		String htmlFilePath = filePath + aId + ".html";
		try {
			return readFile(htmlFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//Function to fetch the text file from file repository given the article ID
	public static String getTextGivenId(int aId) {
		String htmlFilePath = filePath + aId + ".txt";
		try {
			return readFile(htmlFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//Function to fetch the resolved text file from file repository given the article ID
		public static String getResolvedTextGivenId(int aId) {
			String htmlFilePath = filePath + aId + "-resolved.txt";
			try {
				return readFile(htmlFilePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
}