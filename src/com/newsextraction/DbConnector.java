package com.newsextraction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbConnector {

	static Connection c;
	static Statement s;
	static ArrayList<Integer> arrayList;
	
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
	
	public ArrayList<Integer> getRelevantArticles(int userCid, int userNid, String[] titleWords) throws SQLException{
		ResultSet r = s.executeQuery("Select aId,title From Articles where cId=userCid and nId=userNid "); 
		while(r.next()) {
			int aId = r.getInt("aId");
			String title = r.getString("title");
			
		int flag=0;
		for(int i=0; i<titleWords.length; i++)
		{
			if(!title.toLowerCase().contains(titleWords[i].toLowerCase())){
				flag =1;
				break;
				}
			
		}
		if(flag==0)
		{
			arrayList.add(aId);
		}
		}
		return arrayList;
	}
}
