<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="java.io.*,java.util.*,com.newsextraction.*,java.sql.*, java.util.stream.Collectors"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Summary View</title>
		<script type="text/javascript">
			function changeTime() {
				var time = parseInt(document.getElementById("timeLeft").innerHTML);
				if(time>0)
					time--;
				document.getElementById("timeLeft").innerHTML = time;
			}
			function start() {
				setInterval(changeTime,1000);
				window.location.href = "file:///C:/Users/User/Desktop/8th Semester/Project/NewsSearch/Results/summary.html";
			}
		</script>
	</head>
	<body onload="start()">	
		<div style="text-align:center">
			<span>You will be redirected in</span>
			<span id="timeLeft">5</span>
			<span>second(s)</span>	
		</div>
		<%
		String searchQuery = request.getParameter("searchQuery");
		String orgSearchQuery = searchQuery;
		int newsCat = Integer.parseInt(request.getParameter("optNewsCat"));
		int newsSite = Integer.parseInt(request.getParameter("selNewsSite"));
		searchQuery = Stopwords.removeStemmedStopWords(searchQuery);
		String[][] synset = SynsetGenerator.generateAllSynsets(searchQuery);
		ArrayList<Integer> relevantAid = new DbConnector().getRelevantArticles(newsCat, newsSite, synset); 
		ArrayList<String> text = new ArrayList<String>();
		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> url = new ArrayList<String>();
		for(int i=0; i<relevantAid.size(); i++) {
			text.add(RepoConnector.getTextGivenId(relevantAid.get(i)));
			title.add(new DbConnector().getTitleGivenId(relevantAid.get(i)));
			url.add(new DbConnector().getUrlGivenId(relevantAid.get(i)));
			}
		SummaryGenerator.getSummary(title.toArray(new String[title.size()]), text.toArray(new String[text.size()]), url.toArray(new String[url.size()]), orgSearchQuery);
		%>
		<%= searchQuery %>
	</body>
</html>