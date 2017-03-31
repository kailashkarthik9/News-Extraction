/* 	News Extraction and Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
*/
package com.newsextraction;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

public class DbConnector {

	//Global JDBC parameters
	static Connection c;
	static Statement s;
	static Statement s2;
	static ArrayList<Integer> arrayList;
	
	//Constructor establishes initial JDBC environment
	public DbConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/NewspaperExtraction?useSSL=false","root","btech");
			s = c.createStatement();
			s2 = c.createStatement();
			arrayList = new ArrayList<>();
		} catch (ClassNotFoundException | SQLException e) {			
			e.printStackTrace();
		}
	}
	
	//Given Article fetch the title of the article corresponding to it
	public String getTitleGivenId(int aId) {
		ResultSet r;
		try {
			r = s.executeQuery("Select title From Articles where aid=" + aId);
			while(r.next()) {
				return r.getString("title");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	//Given Article Title fetch the ID of the article corresponding to it
	public int getIdGivenTitle(String title) {
		ResultSet r;
		try {
			r = s.executeQuery("Select aid From Articles where title='" + title + "'");
			while(r.next()) {
				return r.getInt("aId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; 
	}
	
	//Given Article fetch the title of the article corresponding to it
	public ArrayList<String> getAllArticles() {
		ResultSet r;
		ArrayList<String> titleList = new ArrayList<>();
		try {
			r = s.executeQuery("Select title From Articles");
			while(r.next()) {
				titleList.add(r.getString("title"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return titleList; 
	}
	
	//Given Article fetch the url of the article corresponding to it
	public String getUrlGivenId(int aId) {
		ResultSet r;
		try {
			r = s.executeQuery("Select url From Articles where aid=" + aId);
			while(r.next()) {
				return r.getString("url");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	//Given Article fetch the tags of the article corresponding to it
	public String getTagsGivenId(int aId) {
		ResultSet r;
		try {
			r = s2.executeQuery("Select tagText From Tags where aid=" + aId);
			while(r.next()) {
				return r.getString("tagText");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ""; 
	}
	
	//Split tags from tag array
	public String[] separateTags(String tagSet) {
		String[] tags = tagSet.split(",");
		for(int i=0;i<tags.length;i++) {
			tags[i] = tags[i].trim();
		}
		return tags;
	}
	
	//Function to merge two String arrays
	public static String[] merge(String[] arr1, String[] arr2) {
		String[] arr3 = new String[arr1.length + arr2.length];
		for(int j=0;j<arr2.length;j++) {
			if(arr2[j].isEmpty()) {
				arr2[j] = "qwerty";
			}
		}
		for(int i =0;i<arr3.length;i++)
		    arr3[i] = (i<arr1.length)?arr1[i]:arr2[i-arr1.length];
		return arr3;
	}
		
	//Get a list of article IDs relevant to the user query parameters
	public ArrayList<Integer> getRelevantArticles(int userCid, int userNid, String[][] synset) {
		ResultSet r;
		try {
			if(userCid==0) {
				if(userNid==0) {
					r = s.executeQuery("Select aId,title From Articles");
				}
				else {
					r = s.executeQuery("Select aId,title From Articles where nId=" + userNid );
				}
			}
			else {
				if(userNid == 0) {
					r = s.executeQuery("Select aId,title From Articles where cId=" + userCid);
				}
				else {
					r = s.executeQuery("Select aId,title From Articles where cId=" + userCid + " and  nId=" + userNid );
				}
			}
			while(r.next()) {
				int aId = r.getInt("aId");
				String title = r.getString("title");
				String tset = getTagsGivenId(aId);
				title = title + tset.replaceAll(",", " ");
				String[] tags = this.separateTags(tset);
				int flag1=0;
				int flag2=1;
				for(int i=0; i<synset.length; i++) {
					flag1=0;
					for(int j=0; j<synset[i].length; j++) {
						if(title.toLowerCase().contains(synset[i][j].toLowerCase())) {
							flag1 =1;
							break;
						}
					}
					if(flag1==0) {
						flag2=0;
						break;
					}
				}
				if(flag2 ==1) {
					arrayList.add(aId);
					for(int i=0;i<synset.length;i++) {
						synset[i] = merge(synset[i], tags);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return arrayList;
	}
	
	//Function to perform test on system
	public static void getTestResults() throws ClientProtocolException, IOException {
		ArrayList<String> titleList = new DbConnector().getAllArticles();
		//For every article, perform a HTTP GET request with article title as query parameter
		for(String title: titleList) {
			Request.Get("http://localhost:8080/NewsExtraction/Redirect.jsp?searchQuery="+ URLEncoder.encode(title, "UTF-8") +"&optNewsCat=0&selNewsSite=0").execute().returnContent();
		}
	}

	//Main method for getting test results
	public static void main(String[] args) throws ClientProtocolException, IOException {
		getTestResults();
	}
}