/* 	News Extraction and Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
*/
package com.newsextraction;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class SummaryGenerator {

	static String template1 = new String();
	static String template2 = new String();
	static String template3 = new String();
	final static Charset ENCODING = StandardCharsets.UTF_8;
	static String baseLocation = "C:\\Users\\User\\Desktop\\8th Semester\\Project\\NewsSearch\\Template\\";
	
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
	
	//Function to read the template documents for summary generation
	public static void readFiles() {
		try {
			template1 = readFile(baseLocation+"template1.txt");
			template2 = readFile(baseLocation+"template2.txt");
			template3 = readFile(baseLocation+"template3.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Function to generate summary page given a list of relevant article information
	public static void getSummary(String[] titles, String[] texts, String[] urls, String query) {
		readFiles();
		//Edit the header template and add to output
		template1 = template1.replace("$Query", query.toUpperCase());
		String htmlText = template1;
		int i;
		//For each article, edit the body template and add to output
		for(i=0;i<titles.length;i++) {
			String templateText = template2;
			templateText = templateText.replace("$title", titles[i]);
			templateText = templateText.replace("$text", texts[i]);
			templateText = templateText.replace("$url", urls[i]);
			htmlText = htmlText + templateText;
		}
		//Edit the footer template and add to output
		htmlText = htmlText + template3;
		//Write the output to a summary html page
    	File  fileHtml = new File("C:\\Users\\User\\Desktop\\8th Semester\\Project\\NewsSearch\\Results\\summary.html");
    	Boolean fileCreated = false;
    	try {
    		if(fileHtml.exists())
    			fileHtml.delete();
    		fileCreated = fileHtml.createNewFile();
    		if(fileCreated) {
    			PrintWriter writer = new PrintWriter(fileHtml, "UTF-8");
    		    writer.println(htmlText);
    		    writer.close();
        	}
		} catch (IOException e) {				
			e.printStackTrace();
		}
	}
}