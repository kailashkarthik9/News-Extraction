<!-- 	News Extraction and Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html lang="en">
	<head>
		<link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
		<!--
			Embedded JavaScript to handle click event and errors
		-->
		<script type="text/javascript">
			//Global variables
			var query;
			var searchCategory;
			var searchSource;
			//To handle click event for Submit button
			function buttonSubmit() {
				//Get Query
				var queryBox = document.getElementById("searchQuery");
				query = queryBox.value;
				//Check for NULL query error
				if(query == "") {
					document.getElementById("queryAlert").style.display = 'block';
					return false;
				}
				else {
					document.getElementById("queryAlert").style.display = 'none';
				}
				//Get news source preference
				var list = document.getElementById("selNewsSite");
				searchSource = list.options[list.selectedIndex].value;
				//Get news category preference
				var categories = document.getElementsByName('optNewsCat');
				for(var i = 0; i < categories.length; i++){
					if(categories[i].checked){
						searchCategory = categories[i].value;
					}
				}
				return true;
			}
		</script>
	</head>
	<body>
		<!-- 
			Alert for displaying empty query error
		-->
		<div class="alert alert-danger" id="queryAlert" style="display: none">
		  <strong>Error!</strong> Query should not be empty
		</div>
		<!-- 
			Header of Page
		-->
		<div class="jumbotron">
			<div class="row">
				<div class="col-sm-12" style="text-align:center">
					<h1>News Summary</h1>
				</div>
			</div>
		</div>
		<!-- 
			Form for user preferences
		 -->
		<form id="submitForm" action="Redirect.jsp" onsubmit="return buttonSubmit()">			
			<!-- 
				Search Box for the user Query
			-->		
			<div class="row" style="margin-top:20px; padding:20px">
				<div class="col-md-6 col-sm-6 col-sm-offset-3 col-md-offset-3">
					<div class="input-group">
					  <span class="input-group-addon">Search</span>
					  <input id="searchQuery" type="text" class="form-control" placeholder="Enter Query" name="searchQuery">
					</div>
				</div>
			</div>
			<!-- 
				User Search Options
			-->
			<div class="row" style="margin-top:20px; padding:20px">
				<!-- 
					Radio Group for selecting News category
				-->
				<div class="col-md-3 col-sm-3 col-sm-offset-2 col-md-offset-2">				
					<h5 style="text-align:center">News Category</h5>
					<div class="radio">
					  <label><input type="radio" name="optNewsCat" id="optNewsAll" value="0" checked>All</label>
					</div>
					<div class="radio">
					  <label><input type="radio" name="optNewsCat" id="optNewsCatEntertainment" value="1">Entertainment</label>
					</div>
					<div class="radio">
					  <label><input type="radio" name="optNewsCat" id="optNewsCatSports" value="2">Sports</label>
					</div>
					<div class="radio ">
					  <label><input type="radio" name="optNewsCat" id="optNewsCatBusiness" value="3">Business</label>
					</div>
				</div>
				<!-- 
					Drop down menu to select source of news
				-->
				<div class="col-md-3 col-sm-3 col-sm-offset-2 col-md-offset-2">
					<div class="form-group" style="text-align:center;">
					  <label for="selNewsSite">News Site</label>
					  <select class="form-control" id="selNewsSite" name="selNewsSite" style="margin:auto;display:block;">
						<option value="0">All</option>
						<option value="1">The Hindu</option>
						<option value="2">Deccan Chronicle</option>
						<option value="3">Times of India</option>
					  </select>
					</div>				
				</div>
			</div>	
			<!-- 
				Submit button to perform news extraction and summarization
			-->		
			<div class="row">
				<div class="col-md-12">				
					<button type="submit" class="btn btn-primary" style="margin:auto;display:block;" id="btnSubmit">Submit</button>				
				</div>
			</div>
		</form>
	</body>
</html>