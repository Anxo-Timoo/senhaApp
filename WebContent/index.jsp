 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
       
<!DOCTYPE html>
<html>
<head>
<script>


</script>
<meta charset="ISO-8859-1">
<title>Recuperar Senha SAP</title>
</head>

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

<body>
<div class="container login-container">
     
        <div class="row">
              
          <div class="col-md-6 login-form-1">
             <h5>Verifique seu Login no SAP R3</h5>
                   
                <form action="<%= request.getContextPath() %>/register" method="post" onsubmit="return displayMsg()">
                   <div class="form-group">
                       <input type="text" class="form-control" placeholder=" Email *" value="" name="email" required/>
                    </div>
                        
			       <div class="form-group">
                       <input type="text" class="form-control" placeholder="Usuario *" name="usuario" value="" required/>
                   </div>
                    <div class="form-group">
                        <input type="submit" class="btnSubmit btn btn-primary" value="Recuperar Senha" />
                    </div>
<!--                         <div class="form-group"> -->
<!--                             <a href="#" class="ForgetPwd">Esqueceu sua Senha?</a> -->
<!--                         </div> -->
                    </form>
                   
                   <!-- <p class="text-primary" name="msg">${message}</p>-->
                   <div class="form-group">
                      
                    
                   </div>
                </div>
               
            </div>
        </div>
        <input id="message" type="hidden" value="${message}"/>
       <input id="subrc" type="hidden" value="${sysubrc}"/>
       
       
       <script>
          function displayMsg(){                    
            var msg = document.getElementById("message").value;
            var sysubrc = document.getElementById("subrc").value;
            
            if (sysubrc == "0")
         	//$("#message").val();
           
                alert(msg);
           
           }
                                  
       </script>
        
   </body>
</html>
