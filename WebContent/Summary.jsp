<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="java.io.*,java.util.*,com.newsextraction.*,java.sql.*"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Summary View</title>
	</head>
	<body>		
		<%
		String searchQuery = request.getParameter("searchQuery");
		String newsCat = request.getParameter("optNewsCat");
		String newsSite = request.getParameter("selNewsSite");
		searchQuery = Stopwords.removeStemmedStopWords(searchQuery);
		%>
		<%= searchQuery %>
	</body>
</html>