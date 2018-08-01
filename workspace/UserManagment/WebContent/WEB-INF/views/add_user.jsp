<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Users</title>
</head>
<body>
	<div class="container">
	<div class="title">***Add Users***</div>
	<fieldset>
	<legend>User Details</legend>
	<form action="UserService/users" method="post">
	
	<div class="inputfield">
		<label for="id" class="inputlabel" >Id:</label> 
		<input name="id" type="text"></input>
	</div>	
	
	<div class="inputfield">
		<label for="name" class="inputlabel" >name:</label> 
		<input name="name" type="text"></input>
	</div>	
	
	<div class="inputfield">
		<label for=profession class="inputlabel" >profession:</label> 
		<input name="profession" type="text"></input>
	</div>	
	
	<div class="inputfield" id="submitfield">
		<input id="submitBtn" type="submit" value="Submit"></input>
	</div>
	
	
	</form>
	</fieldset>
	
	<fieldset>
	<legend>Download Users File</legend>
	<form action="UserService/getUsersFile" method="get">
	<div class="inputfield" id="downloadfield">
		<input id="submitBtn" type="submit" value="Download"></input>
	</div>
	</form>
	</fieldset>
	
	<fieldset>
	<legend>Upload Users File</legend>
	<form action="UserService/upload" method="post" enctype="multipart/form-data">
	<div class="inputfield" id="uploadfield">
		<input type="file" name="file"></input>
		<input type="submit" name="Upload"></input>
	</div>
	</form>
	</fieldset>
	
	</div>
</body>
</html>