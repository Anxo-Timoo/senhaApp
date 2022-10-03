<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Recuperar Senha SAP</title>

</head>

 
<body onload="showDefaultMsg();">

<section class="vh-100">
  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-6 text-black">

        <div class="px-5 ms-xl-4" style="
	      background-image: url('images/netweaver.png');
	      height: 220px;
	      width:550px;
	      margin-left:10px;
	      margin-top:10px;">
          <i class="fas fa-crow fa-2x me-3 pt-5 mt-xl-4" style="color: #709085;"></i>
          <span class="h1 fw-bold mb-0" ></span>
          
        </div>

        <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

          <form id="form" style="width: 23rem;" action="<%= request.getContextPath() %>/register" method="post" >

            <h4 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Verifique seu Login no SAP R3</h4>

            <div class="form-outline mb-4">
              <input type="email" id="form2Example18" class="form-control form-control-lg" placeholder="Email BRQ" name="email" required onchange="return hideDivs();" />
              <label class="form-label" for="form2Example18"></label>
            </div>

            <div class="form-outline mb-4">
              <input type="text" id="form2Example28" class="form-control form-control-lg" placeholder="Usuário *" name="usuario" value="" required onchange="return hideDivs();" />
              <label class="form-label" for="form2Example28"></label>
            </div>

            <div class="pt-1 mb-4">
              <button class="btn btn-info btn-lg btn-block" type="submit" value="Recuperar Senha">Recuperar Senha</button>
              
              <div class="form-group">
                     
                    <p>
                    <div id="alertDiv" class="alert alert-primary" role="alert"  >
                       ${message}
                    </div>
                    
                     
                   </div>
            </div>

<!--             <p class="small mb-5 pb-lg-2"><a class="text-muted" href="#!">Forgot password?</a></p> -->
<!--             <p>Don't have an account? <a href="#!" class="link-info">Register here</a></p> -->

          </form>
          
          <input id="message" type="hidden" value="${message}"/>
          <input id="subrc" type="hidden" value="${sysubrc}"/>
                   
                   
                   

        </div>

      </div>
      <div class="col-sm-6 px-0 d-none d-sm-block">
<!--         <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/img3.webp" -->
<!--           alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;"> -->
          <div id="load" style="background-image: url('images/loading.gif');width:250px;height:300px;">
	      				      					
	      </div>
      </div>
    </div>
  </div>
</section>
       <%session.invalidate(); %>
       
       <script> 
          function displayMsg(){                    
            var msg = document.getElementById("message").value;
            var sysubrc = document.getElementById("subrc").value;
            
                       document.getElementById('load').style.display = 'none';     	        		      		  
	        		   document.getElementById('alertDiv').style.display = 'block';
	    	           document.getElementById('alertDiv').innerHTML = msg;
	    	           //alert(msg);
	    	           //document.getElementById('sucesDiv').innerHTML = '';
	    	           //document.getElementById('alertDiv').innerHTML = '';
	    	           	        	   	        	   	           	          
	           }
	           
           
          //displayMsg();   
          
          function hideDivs(){
        	  document.getElementById('alertDiv').style.display = 'none';
        	  document.getElementById('sucesDiv').style.display = 'none';
        	  
          }
          
          function showDivs(){
        	  document.getElementById('alertDiv').style.display = 'block';
        	  document.getElementById('sucesDiv').style.display = 'block';
        	  
          }
          
          function showDefaultMsg(){
        	  document.getElementById('load').style.display = 'none';
        	  document.getElementById('alertDiv').innerHTML = 'Por favor digite seu email da BRQ e seu usuário SAP R3 para recuperar seu login';
          }
          
          document.getElementById('form').addEventListener('submit', function(event){
              var gif = document.getElementById("load");  
                  
              if (gif.style.display !== "none") 
              {  
                  gif.style.display = "none";  
              }  
              else
              {  
                  gif.style.display = "block";  
              }     
              event.preventDefault();
              document.getElementById('form').submit();       
          })
       </script>
</body>
</html>