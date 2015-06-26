<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Orders</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	
	<!-- Local style sheet in case needed for local modifications -->
	<link rel="stylesheet" href="styles/main.css">
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Customer Summary List</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="index">Home</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<br />
<br />
<br />
    <div class="container">
	    ${message}
	    <div class="jumbotron">
		  <h1>Customer Summary List</h1>
		  <p>
<!-- 		  <a class="btn btn-primary btn-lg float-left" href="editCustomer" role="button">Edit Customer</a> -->
<!-- 		  <a class="btn btn-primary btn-lg float-left margin_left" href="deleteCustomer" role="button">Delete Customer</a> -->
		  ${anchorTagEditString}
		  <span class="spacer float-left">&nbsp;</span>
		  ${anchorTagDeleteString}
		  </p><br><br>
<%--  		  <p>${custInformation}</p>  --%>
			<form action="deleteCustomer" method="post">
				<input type="hidden" name="delete" value="${custWithOrders.customerId}">
				<label for="firstName">First Name:</label>
				<input type="text" readonly="readonly" class="inputTransparent" name="firstName" value="${custWithOrders.firstName}"><br>
				<label for="lastName">Last Name:</label>
				<input type="text" readonly="readonly" class="inputTransparent" name="lastName" value="${custWithOrders.lastName}"><br>
				<label for="email">Email Address:</label>
				<input type="text" readonly="readonly" class="inputTransparent" name="email" value="${custWithOrders.emailAddress}"><br>
				<label for="address">Ship Address:</label> 
 				<input type="text" readonly="readonly" class="inputTransparent" name="address" value="${custWithOrders.getAddresses().get(0).getLine1()}"><br> 
 				<label for="city">City:</label> 
 				<input type="text" readonly="readonly" class="inputTransparent" name="city" value="${custWithOrders.getAddresses().get(0).getCity()}"><br>
 				<label for="state">State:</label>
 				<input type="text" readonly="readonly" class="inputTransparent" name="state" value="${custWithOrders.getAddresses().get(0).getState()}"><br> 
 				<label for="postalCode">Zip Code:</label> 
 				<input type="text" readonly="readonly" class="inputTransparent" name="postalCode" value="${custWithOrders.getAddresses().get(0).getZipCode()}"><br> 
				
				<!-- submit button to initiate the customer deletion -->
<!-- 				<input type="submit" class="redText" value="Delete Record"> -->
			</form>
		</div>
		${subTitle}
	    <table class="table table-hover">
			${tableInfo}
		</table>
    </div><!-- /.container -->
</body>
</html>