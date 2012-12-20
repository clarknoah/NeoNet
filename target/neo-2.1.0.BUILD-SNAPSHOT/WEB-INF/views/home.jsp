<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>BUSNET</title>
</head>
<body>
<h1>
	Welcome to BUNSET
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a href="/alt">Lets go to alt</a>

<form name="input" action="html_form_action.asp" method="get">
First name: <input type="text" name="firstname"><br>
Last name: <input type="text" name="lastname"><br>
Age: <input type="text" name ="age"><br>
<input type="submit" value="Submit">
</form>
</body>
</html>
