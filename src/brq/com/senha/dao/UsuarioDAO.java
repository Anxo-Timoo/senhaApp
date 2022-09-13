package brq.com.senha.dao;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import brq.com.senha.model.Usuario;
import brq.com.senha.util.EmailUtil;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.ext.DestinationDataProvider;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class UsuarioDAO {
    
	private void GenerateNewPassword(Usuario usuario) {
	     
        String DESTINATION_NAME1 = "ECD";
        
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.2.0.155");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "120");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "rfcbrq");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "rfc@brq12");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "pt");
        createDestinationDataFile(DESTINATION_NAME1, connectProperties);

        // This will use that destination file to connect to SAP
        try {
            JCoDestination destination = JCoDestinationManager.getDestination("ECD");
            System.out.println("Attributes:");
            System.out.println(destination.getAttributes());
            System.out.println();
            destination.ping();
        } catch (JCoException e) {
            e.printStackTrace();
        }
        try{
        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
        JCoFunction function = destination.getRepository().getFunction("STFC_CONNECTION");
        
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
        
        function.getImportParameterList().setValue("REQUTEXT", "Hello SAP");
      
        try
        {
            function.execute(destination);
        }
        catch (AbapException e)
        {
            System.out.println(e.toString());
            return;
        }
        System.out.println("STFC_CONNECTION finished:");
        System.out.println(" Echo: " + function.getExportParameterList().getString("ECHOTEXT"));
        System.out.println(" Response: " + function.getExportParameterList().getString("RESPTEXT"));
        System.out.println();
        }
        catch (JCoException e)
        {
            System.out.println(e.toString());
            return;
        }
    }

	private static void createDestinationDataFile(String destinationName, Properties connectProperties) {
		// TODO
		File destCfg = new File(destinationName+".jcoDestination");
        try
        {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to create the destination files", e);
        }
		
	}		
	   
	
	private void SendMail(Usuario usuario) {
		
		final String fromEmail = "souza.marcelo9@gmail.com"; //requires valid gmail id
		final String password = "hmdohadmqyyuolxt"; // correct password for gmail id
		final String toEmail = usuario.getEmail(); // can be any email id 
		
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
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
	
    public int registerUserSAP(Usuario employee) throws ClassNotFoundException {
    	String message = "";
        String INSERT_USERS_SQL = "";
        
        
        INSERT_USERS_SQL = "INSERT INTO usuario" +
                "  (email, loginSap, senha,dataAlteracao) VALUES " +
                " (?, ?, ?,?);";
        int result = 0;
        Date d= new Date();  

        Class.forName("com.mysql.jdbc.Driver");

        try 
        {
        	
        	//Envia email para email brq
        	this.GenerateNewPassword(employee);
            this.SendMail(employee);
            
            
			/*
			 * (Connection connection = DriverManager
			 * .getConnection("jdbc:mysql://localhost:3306/dataapp?useSSL=false", "admin",
			 * "JadSuporte@2021");
			 * 
			 * // Step 2:Create a statement using connection object PreparedStatement
			 * preparedStatement = connection.prepareStatement(INSERT_USERS_SQL))
			 * //preparedStatement.setInt(1, 1); preparedStatement.setString(1,
			 * employee.getEmail()); preparedStatement.setString(2, employee.getLoginSap());
			 * employee.setSenha("Sap@brq2022"); preparedStatement.setString(3,
			 * employee.getSenha()); preparedStatement.setDate(4, new
			 * java.sql.Date(System.currentTimeMillis()));
			 * 
			 * System.out.println(preparedStatement); // Step 3: Execute the query or update
			 * query result = preparedStatement.executeUpdate();
			 */
        } 
        //catch (SQLException e) { 
        catch(Exception e) {
            // process sql exception
           /// printSQLException(e);
            //e.printStackTrace();
            message = e.getMessage();           
            
        }
        
        
        return result;
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