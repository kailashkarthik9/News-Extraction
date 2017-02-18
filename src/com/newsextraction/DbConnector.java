/* 	News Extraction and Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
*/
package com.newsextraction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbConnector {

	//Global JDBC parameters
	static Connection c;
	static Statement s;
	static ArrayList<Integer> arrayList;
	
	//Constructor establishes initial JDBC environment
	public DbConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/NewspaperExtraction?useSSL=false","root","btech");
			s = c.createStatement();	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
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
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return arrayList;
	}
}