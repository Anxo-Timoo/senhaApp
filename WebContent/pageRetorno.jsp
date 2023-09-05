<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
       
<!DOCTYPE html>
<html>
<head>
<script>
window.onload = function() {
	displayMsg();
	
	var register = 'register';	
	var oldURL = document.referrer;
	
// 	if (oldURL.includes(register))
// 	{
		
// 		window.location.href = "login.jsp";
// 	}
// 	else
// 	{		
// 		window.location.href = "index.jsp";
// 	}
	
	
	//document.getElementById('alertDiv').style.display = 'none';
	//document.getElementById('sucesDiv').style.display = 'none';
	
}

</script>
<meta charset="ISO-8859-1">
<title>Recuperar Senha SAP</title>
</head>

<section class="vh-100 gradient-custom">
 
   <input id="message" type="hidden" value="${message}"/>
       <input id="subrc" type="hidden" value="${sysubrc}"/>
       
       
       <script>
          function displayMsg(){                    
            var msg = document.getElementById("message").value;
            var sysubrc = document.getElementById("subrc").value;        	         
                
                alert(msg);
                
	            if (sysubrc == "0"){
	            	window.location.href = "index.jsp";
	            	
	            }else{
	            	window.location.href = "login.jsp";
	            }
         	                         
           
           }
                                  
       </script>
</section>
</html>