package brq.com.senha.util;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

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


public class SAPSystemConnect extends JCoDestinationManager{

	JCoDestination connectionString ;
	
	public JCoDestination getDestinationInstance(String par1,String par2) {
				
		return  connectionString;
	}
	
	public Properties getPropertiesForConnection(String ambiente) {
	
		String DESTINATION_NAME1 = ambiente;
		Properties connectProperties = new Properties();
	    
			if(DESTINATION_NAME1 == "ECD") 
			{
				
			    connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.2.0.155");
			    connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");
			    connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "120");
			    connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "rfcbrq");
			    connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "rfc@brq12");
			    connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "pt");
			    //createDestinationDataFile(DESTINATION_NAME1, connectProperties);
			}
			else if(DESTINATION_NAME1 == "ECQ")
			{
				 
				 connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.2.0.226");
				 connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "01");
				 connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "210");
				 connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "rfcbrq");
				 connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "rfc@brq12");
				 connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "pt");
			}
			else if(DESTINATION_NAME1 == "ECP")
			{
				 
				 connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.200.1.1");
				 connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");
				 connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "300");
				 connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "rfcbrq");
				 connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "rfc@brq12");
				 connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "pt");
			}
			
			return connectProperties;
	}
	
	public com.sap.conn.jco.JCoDestination getDestinationSAP(String ambiente){
		
		com.sap.conn.jco.JCoDestination ambienteSAP = null ;
		String DESTINATION_NAME1 = ambiente;
		Properties connectProperties = new Properties();
	    
			if(DESTINATION_NAME1 == "ECD") 
			{
				
			    connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.2.0.155");
			    connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");
			    connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "120");
			    connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "rfcbrq");
			    connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "rfc@brq12");
			    connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "pt");
			    //createDestinationDataFile(DESTINATION_NAME1, connectProperties);
			}
			else if(DESTINATION_NAME1 == "ECQ")
			{
				 
				 connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.2.0.226");
				 connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "01");
				 connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "210");
				 connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "rfcbrq");
				 connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "rfc@brq12");
				 connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "pt");
			}
			else if(DESTINATION_NAME1 == "ECP")
			{
				 
				 connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.200.1.1");
				 connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");
				 connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "300");
				 connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "rfcbrq");
				 connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "rfc@brq12");
				 connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "pt");
			}
		
			createDestinationDataFile(DESTINATION_NAME1, connectProperties);
	          
			
			return  ambienteSAP;
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
	
}
