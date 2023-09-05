package brq.com.senha.dao;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import brq.com.senha.model.Usuario;
import brq.com.senha.util.EmailUtil;
import brq.com.senha.util.SAPSystemConnect;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import javax.mail.Session;
import javax.xml.bind.ValidationException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import brq.com.senha.util.Database;

public class UsuarioDAO {
	
	SAPSystemConnect conSAP = new SAPSystemConnect();
	Database db = new Database();
	
	 private boolean checkUserAuthenticity(Usuario usuario)  {
		
	    // VAI NO SAP E VERIFICA SE O USUÁRIO EXISTE, E SE OS DADOS INFORMADOS NA TELA PROCEDEM
		String emailDB = "";
		boolean autenticado = true;
        String DESTINATION_NAME1 = "ECP";
        
        createDestinationDataFile(DESTINATION_NAME1, conSAP.getPropertiesForConnection(DESTINATION_NAME1));

        // This will use that destination file to connect to SAP
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
            System.out.println("Attributes:");
            System.out.println(destination.getAttributes());
            System.out.println();
            destination.ping();
            
            
        } 
        catch (JCoException e) {
        	throw new RuntimeException("Erro de conexão com o SAP R3. Por favor verifique se você está conectado à rede da BRQ.");
        }
        
        try
        {
        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
        JCoFunction function = destination.getRepository().getFunction("BAPI_USER_GET_DETAIL");
        
        
	        if (function == null)
	            throw new RuntimeException("Not found in SAP");
	        
	        function.getImportParameterList().setValue("USERNAME", usuario.getLoginSap());
	      
	        try
	        {
	            function.execute(destination);
	            JCoStructure returnStructure = function.getExportParameterList().getStructure("ADDRESS");
		        
		        emailDB = returnStructure.getString("E_MAIL");
		        //for (JCoField field : returnStructure) {
		           // System.out.println(field.getName() + ":\t" + field.getString());
		        //}
		        
		        if(!emailDB.equals(usuario.getEmail())) { 
		          
		          throw new RuntimeException("O email e/ou usuário informado(s) não coincide(m)."); 
		        }
	        }
	        catch (AbapException e)
	        {
	            System.out.println(e.toString());
	            autenticado = false;
	        }
	        System.out.println("STFC_CONNECTION finished:");
	       // System.out.println(" Echo: " + function.getExportParameterList().getStructure("ADRESS"));
	        
	       
	        }
	        catch (JCoException e)
	        {
	            System.out.println(e.toString());
	            //throw new RuntimeException(e.getMessage());
	            autenticado = false;
	            
	        }
            
        return autenticado;
	}
    
	 private boolean generateNewPassword(Usuario usuario) throws JCoException {
	     
		String DESTINATION_NAME1 = "ECP";
        boolean senhaGerada = false;
		
        //createDestinationDataFile(DESTINATION_NAME1, connectProperties);
        createDestinationDataFile(DESTINATION_NAME1, conSAP.getPropertiesForConnection(DESTINATION_NAME1));

        // This will use that destination file to connect to SAP
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
            System.out.println("Attributes:");
            System.out.println(destination.getAttributes());
            System.out.println();
            destination.ping();
        } catch (JCoException e) {
            e.printStackTrace();
        }
        try
        {
	        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
	        JCoFunction function = destination.getRepository().getFunction("BAPI_USER_CHANGE");
	        
				/*
				 * CALL FUNCTION 'BAPI_USER_UNLOCK' EXPORTING username = tbl_users-bname TABLES
				 * return = tbl_ret.
				 * 
				 * 
				 * CALL FUNCTION 'BAPI_TRANSACTION_COMMIT' EXPORTING WAIT = IMPORTING return =
				 * tbl_ret.
				 * 
				 * SUSR_USER_CHANGE_PASSWORD_RFC
				 * S_WAP_USER_PASSWORD_RESET
				 * SUSR_INTERNET_PASSWORD_RESET
				 * RSEC_GENERATE_PASSWORD
				 * BAPI_USER_CHANGE
				 * 
				 * 
				 * 
				 * 
				 */
	        
	        if (function == null)
	            throw new RuntimeException("Not found in SAP");
	       
	        //SENHA PADRÃO SAP Sap@brq + o ano corrente
	        Date date = new Date(); 
	        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
			cal.setTime(date);
			int ano = cal.get(Calendar.YEAR);
			String type = "I";
			String bapiReturn = "";
	        //function.getImportParameterList().setValue("REQUTEXT", "Hello SAP");
	        function.getImportParameterList().setValue("USERNAME", usuario.getLoginSap());
	        
	        JCoStructure sPassword = function.getImportParameterList().getStructure("PASSWORD");
	        sPassword.setValue("BAPIPWD","Sap@brq"+Integer.toString(ano));
	        //function.getImportParameterList().setValue("PASSWORD","Sap@brq"+Integer.toString(ano));
	        JCoStructure sPasswordx = function.getImportParameterList().getStructure("PASSWORDX");
	        //function.getImportParameterList().setValue("PASSWORDX","X");
	        sPasswordx.setValue("BAPIPWD","X");
	        
	        function.execute(destination);
	            //JCoStructure returnStructure = function.getExportParameterList().getStructure("ADDRESS");
	            JCoTable table = function.getTableParameterList().getTable("RETURN");
	            
	            for (int i = 0; i < table.getNumRows(); i++, table.nextRow()) {
	
	            	type = table.getValue("TYPE").toString();
	            	bapiReturn = table.getValue("MESSAGE").toString();
	            	
	            }
	            
	            if(type == "E") {
	            	throw new RuntimeException("Não foi possível alterar a senha do usuário. Tente novamente por favor");
	            }
	            //dando tudo certo
	            senhaGerada = true;
                        
        }
        catch (AbapException e)
        {
           throw e;
        }
                
        catch (JCoException e)
        {
            throw e;
            
        }
        catch(Exception ex)
        {
        	throw ex;
        }
        return senhaGerada;
    }
	
	 private void unlockUser(Usuario usuario) 
	 {
	 String DESTINATION_NAME1 = "ECP";
     boolean unlocked = false;
     String type = "S";
     		
     createDestinationDataFile(DESTINATION_NAME1, conSAP.getPropertiesForConnection(DESTINATION_NAME1));

     // This will use that destination file to connect to SAP
     try {
         JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
         System.out.println("Attributes:");
         System.out.println(destination.getAttributes());
         System.out.println();
         destination.ping();
     } catch (JCoException e) {
         e.printStackTrace();
     }
     try
     {
     JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
     JCoFunction function = destination.getRepository().getFunction("BAPI_USER_UNLOCK");
     
			/*
			 * CALL FUNCTION 'BAPI_USER_UNLOCK' EXPORTING username = tbl_users-bname TABLES
			 * return = tbl_ret.
			 */
			
     
     if (function == null)
         throw new RuntimeException("Not found in SAP");
    
     
     function.getImportParameterList().setValue("USERNAME", usuario.getLoginSap());
     
     //JCoStructure sPassword = function.getImportParameterList().getStructure("PASSWORD");
     
     //function.getImportParameterList().setValue("PASSWORD","Sap@brq"+Integer.toString(ano));
     //function.getImportParameterList().setValue("PASSWORDX","X");
   
     try
     {
         function.execute(destination);
         //JCoStructure returnStructure = function.getExportParameterList().getStructure("ADDRESS");
       
         
         //if(type == "E") {
         	//throw new RuntimeException("Não foi possível alterar a senha do usuário. Tente novamente por favor");
         //}
         //dando tudo certo
         unlocked = true;
                     
     }
     catch (AbapException e)
     {
        throw e;
     }
     System.out.println("STFC_CONNECTION finished:");
     
     
     }
     catch (JCoException e)
     {
        
         
     }
    
 }

	 private static void createDestinationDataFile(String destinationName, Properties connectProperties) {
		// TODO
		File destCfg = new File(destinationName+".jcoDestination");
        try
        {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "Conexão ok !");
            fos.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to create the destination files", e);
        }
		
	}		
	   
	
	 private void sendMailWithValidCode(Usuario usuario) {
		
		final String fromEmail = "suportesap@brq.com"; //requires valid email
		final String password = "EmailAdminSAP@999"; // correct password for gmail id
		final String toEmail = usuario.getEmail(); // can be any email id 
		
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		
		props.put("mail.smtp.host", "smtp.office365.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		Session session = Session.getInstance(props, auth);
		
		EmailUtil.sendEmail(session, toEmail,"SAP R3 - Esqueci Minha senha", 
				                             "<html><body><FONT face=\"Arial\"><table><tr><td><p>"
				                             + "Prezado(a) "+ usuario.getLoginSap() +",</td></tr></p>"
				                             + "<tr><td>Por favor utilize o código: " + usuario.getSenha() +" para recuperar a sua senha.</td></tr>"
				                            + "<tr><td>Sua senha deve conter uma letra maiúscula, um caractere especial,um número e no mínimo oito letras.</td></tr>"
				                            + "<tr><td><p></p></td></tr><tr><td><img src='https://blog.brq.com/wp-content/uploads/2022/08/logo_brq_2022.png' width=\"120\" height=\"80\"></td></tr></table></body></html>");
		
	}
	
     private void sendMail(Usuario usuario) {
		
		final String fromEmail = "suportesap@brq.com"; //requires valid gmail id
		final String password = "EmailAdminSAP@999"; // correct password for gmail id
		final String toEmail = usuario.getEmail(); // can be any email id 
		
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		
		props.put("mail.smtp.host", "smtp.office365.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		Session session = Session.getInstance(props, auth);
		
		EmailUtil.sendEmail(session, toEmail,"SAP R3 - Esqueci Minha senha", 
				                             "Sua nova senha é Sap@brq2022. Faça login no sistema e altere sua senha.");
		
	}
	
     public int registerUserSAP(Usuario employee) throws Exception {
    	
    	String message = "";
        String INSERT_USERS_SQL = "";
        //Random r = new Random();
       // PreaparedStatement 
        
        INSERT_USERS_SQL = "INSERT INTO usuario" +
                "  (email, loginSap, senha,dataAlteracao) VALUES " +
                " (?, ?, ?,?);";
        int result = 0;        

        Class.forName("com.mysql.jdbc.Driver");

        try 
        {
        	
        	//Envia email para email brq
        	//this.GenerateNewPassword(employee);
            //
            result = 0;
            
			
			  Connection connection = db.getStrConnection();
			  
			  
			  // Step 2:Create a statement using connection object PreparedStatement
			  PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
			  preparedStatement.setInt(1, 1); preparedStatement.setString(1,employee.getEmail()); 
			  preparedStatement.setString(2, employee.getLoginSap());
			  
			  //short randomNumber = (short)r.nextInt(Short.MAX_VALUE + 1);
			  
			  //employee.setSenha(String.valueOf(randomNumber)); 
			  preparedStatement.setString(3,employee.getSenha()); 
			  preparedStatement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			  
			  System.out.println(preparedStatement); // Step 3: Execute the query or updatequery 
			  
			  
			  boolean autenticado = this.checkUserAuthenticity(employee);
			  
			  if (! autenticado) {
				  result = 0;
				  throw new RuntimeException("Email não corresponde ao usuário digitado.");
				  
			  }
			  result = preparedStatement.executeUpdate();
			  
			  if(result > 0) {
			     this.sendMailWithValidCode(employee);
			  }
			 
        } 
        catch (SQLException e) { 
        	printSQLException(e);
        	result = 0;
        	
        }
        catch(Exception e) {
            // process sql exception
           /// printSQLException(e);
            //e.printStackTrace();
        	result = 0;
        	throw e;
            //message = e.getMessage();           
            
        }
        
        
        return result;
    }
    
    
     public boolean checkValidationCode(Usuario employee) throws Exception {
    	
    	String message = "";
        boolean validado = true;
        String SELECT_SQL = "";
        Usuario usuarioBD = new Usuario();
       // PreaparedStatement 
        
        SELECT_SQL = "SELECT * FROM usuario WHERE loginSap = ? "
        		+ "AND idUsuario = (SELECT MAX(idUsuario) FROM usuario WHERE loginSap = ?);";
        int result = 0;        

        Class.forName("com.mysql.jdbc.Driver");

        try 
        {
        	
            result = 0;
            
			  Connection connection = db.getStrConnection();
			  //
			  PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
			  //Parametros
			  ps.setString(1,employee.getLoginSap());
			  ps.setString(2,employee.getLoginSap());
			// process the results
			    ResultSet rs = ps.executeQuery();
			    
			    while ( rs.next() )
			    {
			      usuarioBD.setDataAlteracao(rs.getDate("dataAlteracao"));
			      usuarioBD.setEmail(rs.getString("email"));
			      usuarioBD.setIdUsuario(rs.getInt("idUsuario"));
			      usuarioBD.setLoginSap(rs.getString("loginSap"));
			      usuarioBD.setSenha(rs.getString("senha"));
			      
			    }
			    rs.close();
			    ps.close();
			    
			    if(!(usuarioBD.getSenha().equals(employee.getSenha()))) 
			    {
			    	validado = false;
			    }
			   			
        } 
        catch (SQLException e) { 
        	printSQLException(e);
        	result = 0;
        	
        }
        catch(Exception e) {
            // process sql exception
           /// printSQLException(e);
            //e.printStackTrace();
        	result = 0;
        	throw e;
            //message = e.getMessage();           
            
        }
        
        
        return validado;
    }
    
     public boolean changeUserPwd(Usuario employee) throws Exception {
    	
    	String message = "";
        boolean codigoValido = false;
        boolean atualizado = false;
        
       //CHECA SE O CÓDIGO DIGITADO FOI VÁLIDO 
        try {
          codigoValido = this.checkValidationCode(employee);
	       // PreaparedStatement 
	        if(!codigoValido)
	        	throw new ValidationException("O código de validação não confere.");
        
	  //DESBLOQUEIA O USUÁRIO
	        
	        this.unlockUser(employee); 
      //======================================================
      //ALTERA A SENHA DO USUÁRIO      
      //==========================================================
	        atualizado  = this.generateNewPassword(employee);
        
        }
        catch(Exception ex) {
           throw ex;
        }
        return atualizado;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}