 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
       
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">

window.onload = function() {
	  
	//var msg = document.getElementById("message").value;
    //var sysubrc = document.getElementById("subrc").value;
	////document.getElementById('alertDiv').style.display = 'none';
	//document.getElementById('sucesDiv').style.display = 'none';
	
}
</script>



<meta charset="ISO-8859-1">
<title>Recuperar Senha SAP</title>
</head>

<script>
       showDefaultMsg();
 </script>
 
<header>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-light bg-white">
    <div class="container-fluid">
      <button
        class="navbar-toggler"
        type="button"
        data-mdb-toggle="collapse"
        data-mdb-target="#navbarExample01"
        aria-controls="navbarExample01"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <i class="fas fa-bars"></i>
      </button>
      <div class="collapse navbar-collapse" id="navbarExample01">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item active">
            <a class="nav-link" aria-current="page" href="#">BRQ</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Setor SAP</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Política de Senhas</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Contatos</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <!-- Navbar -->
 
  <!-- Background image -->
  <div
    class="p-5 text-center bg-image"
    style="
      background-image: url('images/netweaver.png');
      height: 220px;
      width:550px;
      margin-left:120px;
      
    "
  >
  </div>
  <!-- Background image -->
</header>

<body onload="showDefaultMsg();">

<div class="container login-container">
     
        <div class="row">
              
          <div class="col-md-6 login-form-1">
             <h5>Verifique seu Login no SAP R3</h5>
                   
                <form id="form" action="<%= request.getContextPath() %>/register" method="post" >
                   <div class="form-group">
                       <input type="text" class="form-control" placeholder=" Email *" value="" name="email" required onchange="return hideDivs();"/>
                    </div>
                        
			       <div class="form-group">
                       <input type="text" class="form-control" placeholder="Usuário *" name="usuario" value="" required onchange="return hideDivs();"/>
                   </div>
                    <div class="form-group">
                        <input type="submit" class="btnSubmit btn btn-primary" value="Recuperar Senha" />
                    </div>
<!--                         <div class="form-group"> -->
<!--                             <a href="#" class="ForgetPwd">Esqueceu sua Senha?</a> -->
<!--                         </div> -->
                    </form>
                   
        <!-- TRATAMENTO DO RETORNO NA TELA - MENSAGENS ==============================================================
        ========================================================================================================== -->
                   <!-- <p class="text-primary" name="msg">${message}</p>-->
                   
                   <input id="message" type="hidden" value="${message}"/>
                   <input id="subrc" type="hidden" value="${sysubrc}"/>
                   
                   
                   <div class="form-group">
                     
<!--                     <div id="sucesDiv" class="alert alert-secondary" role="alert"  > -->
<!--                        Mensagens -->
<!--                       </div> -->
                    
                    <div id="alertDiv" class="alert alert-primary" role="alert"  >
                       ${message}
                    </div>
                    
                     
                   </div>
                </div>
               
            </div>
          </div>
         
                   
        <div class="container login-container"> 
         <div class="row">   
	         <div class="col-md-6 login-form-1">
	            <div id="load" style="background-image: url('images/loading.gif');width:250px;height:300px;">
	      				      					
	            </div>
	         </div>
	     </div>
	    </div>
	    
	    
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
