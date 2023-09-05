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
	  
	//document.getElementById('alertDiv').style.display = 'none';
	//document.getElementById('sucesDiv').style.display = 'none';
	//executeAlert();
}
</script>
<meta charset="ISO-8859-1">
<title>Recuperar Senha SAP</title>
</head>


<section class="vh-100 gradient-custom">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-12 col-md-8 col-lg-6 col-xl-5">
        <div class="card bg-dark text-white" style="border-radius: 1rem;">
          <div class="card-body p-5 text-center">

            <div class="mb-md-5 mt-md-4 pb-5">

              <h2 class="fw-bold mb-2 text-uppercase">VALIDAÇÃO</h2>
              <p class="text-white-50 mb-5">Por favor digite o código recebido em seu email para reiniciar a senha.</p>

             <form id="form1" action="<%= request.getContextPath() %>/changepwd" method="post" >
              
              <div class="form-outline form-white mb-4">
                <input type="password" name="codigo" id="typePasswordX" class="form-control form-control-lg" required />
                <label class="form-label" for="typePasswordX">Código de 4 a 5 dígitos</label>
              </div>
              
              <input id="login" type="hidden" name="login" value="${login}"/>

<!--               <p class="small mb-5 pb-lg-2"> -->
<!--               <a class="text-white-50"  onclick="callServlet();">Não recebeu o código? Solicitar reenvio</a></p> -->

              <button class="btn btn-outline-light btn-lg px-5" type="submit">Confirmar</button>
              </form>
              
              <form id="form2" action="${pageContext.request.contextPath}/regenerate" method="post" onsubmit="return callServlet();" >
                 <input id="loginSAP" type="hidden" name="loginSAP" value="${login}"/>
                 <input id="emailBRQ" type="hidden" name="emailBRQ" value="${emailBRQ}"/>
                 
                 
                 <a class="text-white-50">Não recebeu o código?</a>
				 <p class="small mb-5 pb-lg-2">
              	 <button class="btn btn-primary btn-sm" type="submit">Reenviar</button>
                 
              </form>
              
              
              
              <div class="d-flex justify-content-center text-center mt-4 pt-1">
                <a href="#!" class="text-white"><i class="fab fa-facebook-f fa-lg"></i></a>
                <a href="#!" class="text-white"><i class="fab fa-twitter fa-lg mx-4 px-2"></i></a>
                <a href="#!" class="text-white"><i class="fab fa-google fa-lg"></i></a>
              </div>

            </div>

            <div>
              <p class="mb-0"><a href="#!" class="text-white-50 fw-bold"></a>
              </p>
            </div>
            

          </div>
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
            
            //if (sysubrc == "0")
         	//$("#message").val();
           
                alert(msg);
           
           }
          
          function executeAlert(){
        	  var login = document.getElementById("login").value;
        	  alert(login);
          }
          
          
          function callServlet() {
        	  //alert(document.forms['form2']);
              //document.forms['form2'].submit;
              alert("Sua solicitação está em processamento. Um novo código será gerado e enviado ao seu email.");
          }
                
                                  
       </script>
</section>
</html>