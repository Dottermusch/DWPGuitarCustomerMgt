<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Customer</title>
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
          <a class="navbar-brand" href="#">Edit Customer</a>
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
		  <h1>Update Customer Detail</h1>
		  <p>
<!-- 		  <a class="btn btn-primary btn-lg float-left" href="editCustomer" role="button">Edit Customer</a> -->
<!-- 		  <a class="btn btn-primary btn-lg float-left margin_left" href="deleteCustomer" role="button">Delete Customer</a> -->
		  ${anchorTagEditString}
		  <span class="spacer float-left">&nbsp;</span>
		  ${anchorTagDeleteString}
		  </p><br><br>
<%--  		  <p>${custInformation}</p>  --%>
			<form action="editCustomer" method="post">
				<input type="hidden" name="isEdited" value="yes">
				<input type="hidden" name="custID" value="${custToModify.getCustomerId()}">
				<label for="firstName">First Name:</label>
				<input type="text" name="firstName" value="${custToModify.getFirstName()}" required ><br>
				<label for="lastName">Last Name:</label>
				<input type="text" name="lastName" value="${custToModify.getLastName()}" required ><br>
				<label for="email">Email Address:</label>
				<input type="text" name="email" value="${custToModify.getEmailAddress()}" readonly="readonly" required ><br><br>
				
<!-- 				Ship To Address -->
				
				
				<input type="hidden" name="shipToNo" value="${shipAddress.getAddressId()}">
				<label for="shipAddress">Ship Address:</label> 
 				<input type="text" name="shipAddress" value="${shipAddress.getLine1()}" required ><br> 
 				<label for="shipCity">Ship City:</label> 
 				<input type="text" name="shipCity" value="${shipAddress.getCity()}" required ><br>
 				<label for="shipState">Ship State:</label>
 				<input type="text" name="shipState" value="${shipAddress.getState()}" maxlength="2" required ><br> 
 				<label for="shipPostalCode">Ship Zip Code:</label> 
 				<input type="text" name="shipPostalCode" value="${shipAddress.getZipCode()}" maxlength="10" required ><br><br>		
 				 
<!-- 				Bill To Address -->

				<input type="hidden" name="billToNo" value="${billAddress.getAddressId()}">
				<label for="billingAddress">Billing Address:</label> 
 				<input type="text" name="billingAddress" value="${billAddress.getLine1()}" required ><br> 
 				<label for="billingCity">Billing City:</label> 
 				<input type="text" name="billingCity" value="${billAddress.getCity()}" required ><br>
 				<label for="billingState">Billing State:</label>
 				<input type="text" name="billingState" value="${billAddress.getState()}" maxlength="2" required ><br> 
 				<label for="billingPostalCode">Billing Zip Code:</label> 
 				<input type="text" name="billingPostalCode" value="${billAddress.getZipCode()}" maxlength="10" required ><br><br>	
				
				<!-- submit button to initiate the customer deletion -->
				<input type="submit" value="Update Record" class="btn btn-primary btn-lg">
			</form>
		</div>
		${subTitle}
	    <table class="table table-hover">
			${tableInfo}
		</table>
    </div><!-- /.container -->
</body>
</html>