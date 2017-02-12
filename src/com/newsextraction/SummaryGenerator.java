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
	
	//For the urls array, remove the ' ' in the url string. It is showing some error
	public static void getSummary(String[] titles, String[] texts, String[] urls, String query) {
		readFiles();
		template1 = template1.replace("$Query", query);
		String htmlText = template1;
		int i;
		for(i=0;i<titles.length;i++) {
			String templateText = template2;
			templateText = templateText.replace("$title", titles[i]);
			templateText = templateText.replace("$text", texts[i]);
			templateText = templateText.replace("$url", urls[i]);
			htmlText = htmlText + templateText;
		}
		htmlText = htmlText + template3;
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
