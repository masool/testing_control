//package bccontrol;
//
//import java.io.InputStream;
//import java.security.NoSuchAlgorithmException;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import com.datadyn.flows.FileProfileDataFlow;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelExec;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;
//
//import net.corda.core.messaging.CordaRPCOps;
//import net.corda.core.transactions.SignedTransaction;
//
//public class NASServer {
//	
//	public void NASServerConnect(String path) throws NoSuchAlgorithmException, JsonProcessingException, InterruptedException, ExecutionException, SQLException, AuditTrailException {
//	    String host="51.140.205.10";
//	    String user="irfan";
//	    String password="Irf@n2020";
////	    String path="cd /home/ddctrl01/encryption/kk.txt && pwd";
////	    String path="find /home/ddctrl01/encryption/kk1.txt";
//	    String command="find "+path;
//	    try{
//	    	
//	    	java.util.Properties config = new java.util.Properties(); 
//	    	config.put("StrictHostKeyChecking", "no");
//	    	JSch jsch = new JSch();
//	    	Session session=jsch.getSession(user, host, 22);
//	    	session.setPassword(password);
//	    	session.setConfig(config);
//	    	session.connect();
//	    	System.out.println("Connected");
//	    	
//	    	Channel channel=session.openChannel("exec");
//	        ((ChannelExec)channel).setCommand(command);
//	        channel.setInputStream(null);
//	        ((ChannelExec)channel).setErrStream(System.err);
//	        
//	        InputStream in=channel.getInputStream();
//	        channel.connect();
//	        byte[] tmp=new byte[1024];
//	        while(true){
//	          while(in.available()>0){
//	            int i=in.read(tmp, 0, 1024);
//	            if(i<0)break;
//	            System.out.print(new String(tmp, 0, i));
//	          }
//	          if(channel.isClosed()){
//	            System.out.println("exit-status: "+channel.getExitStatus());
//	            break;
//	          }
//	          try{Thread.sleep(1000);}catch(Exception ee){}
//	        }
//	        channel.disconnect();        
//	        session.disconnect();
//	        System.out.println("DONE");
//	    }catch(Exception e){
//	    	e.printStackTrace();
//	    }
//    }
//
//}
