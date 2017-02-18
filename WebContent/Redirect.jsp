<!-- 	News Extraction and Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="java.io.*,java.util.*,com.newsextraction.*,java.sql.*, java.util.stream.Collectors"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Summary View</title>
		<link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
		<script type="text/javascript">
			//Function called every second to update countdown timer
			function changeTime() {
				var time = parseInt(document.getElementById("timeLeft").innerHTML);
				if(time>0)
					time--;
				else {
					window.setTimeout(function(){
				        // Redirect to Query page once countdown timer expires
				        window.location.href = "http://localhost:8080/NewsExtraction/Query.jsp";
				    }, 1000);
				}					
				document.getElementById("timeLeft").innerHTML = time;
			}
			//Create an interval to update time every second
			function start() {
				setInterval(changeTime,1000);	
			}
		</script>
	</head>
	<body onload="start()">	
		<!--
			Page Header
		-->
		<div class="jumbotron">
			<div class="row">
				<div class="col-sm-12" style="text-align:center">
					<h1>News Summary</h1>
				</div>
			</div>
		</div>
		<!-- 
			Countdown timer
		-->
		<div style="text-align:center">
			<span>You results will be ready in</span>
			<span id="timeLeft">3</span>
			<span>second(s)</span>	
		</div>
		<%
		//Get parameters from GET request
		String searchQuery = request.getParameter("searchQuery");
		String orgSearchQuery = searchQuery;
		int newsCat = Integer.parseInt(request.getParameter("optNewsCat"));
		int newsSite = Integer.parseInt(request.getParameter("selNewsSite"));
		//Remove stop words from the user query
		searchQuery = Stopwords.removeStemmedStopWords(searchQuery);
		//Stem the words in the user query
		searchQuery = Stemmer.stemSentence(searchQuery);
		//Generate synset for each of the user query keyword
		String[][] synset = SynsetGenerator.generateAllSynsets(searchQuery);
		//Fetch all the relevant articles given the category, newspaper and query parameters
		ArrayList<Integer> relevantAid = new DbConnector().getRelevantArticles(newsCat, newsSite, synset); 
		ArrayList<String> text = new ArrayList<String>();
		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> url = new ArrayList<String>();
		DbConnector dbConnector = new DbConnector();
		String stemmedTitleString = new String();
		//For each relevant artivle fetch the title, news text and URL
		for(int i=0; i<relevantAid.size(); i++) {
			title.add(dbConnector.getTitleGivenId(relevantAid.get(i)));
			text.add(TfIdf.getSummary(RepoConnector.getTextGivenId(relevantAid.get(i)), synset));
			url.add(dbConnector.getUrlGivenId(relevantAid.get(i)));
			}
		//Call summary generator function
		SummaryGenerator.getSummary(title.toArray(new String[title.size()]), text.toArray(new String[text.size()]), url.toArray(new String[url.size()]), orgSearchQuery);
		%>
	</body>
</html>