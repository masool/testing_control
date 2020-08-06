package bccontrol;

//*****************************************************************************************/
/* created by Irfan.Masool
 	This Method compares hashes of fileprofile data from input payload and from postgresql db
 	compare two respective hashes and if found differences then loop comparision logic and ommit result set
 	then result set will update into offchain(postgresql) and onchain(corda)
 	
 	if you find something broken - fix it !
 */

//*****************************************************************************************/

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.datadyn.flows.FileProfileDataFlow;
import com.datdyn.state.FileProfileDataState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.corda.core.contracts.StateAndRef;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.services.Vault.Page;
import net.corda.core.transactions.SignedTransaction;

public class OffchainToOnchain {
	
	private static String cordauser;
	private static String cordapassword;
	private static String cordanode0;

	public OffchainToOnchain(String cordauser, String cordapassword, String cordanode0) {
			this.cordauser = cordauser;
			this.cordapassword = cordapassword;
			this.cordanode0 = cordanode0;
	}
	
	Connection connection = null;
	
	 // Insert new records into Blockchain
	public List<FileProfile> InsertRecordintobc(List<FileProfile> ixFileProfileData, String remId, String time) throws NoSuchAlgorithmException, JsonProcessingException, InterruptedException, ExecutionException, SQLException, AuditTrailException {
			
			GetDB getDB = new GetDB();
			List<FileProfile> offchainSet = getDB.fetchFileProfileRecord(remId);
		
			for(int i=0; i<ixFileProfileData.size();i++) {
			
			//fetch hash value
            HashCode hash = new HashCode();
            ixFileProfileData.get(i).setHash(hash.getHashOfFileProfileData(ixFileProfileData.get(i)));
            
            String  UUID = "\""+ixFileProfileData.get(i).getId()+"\"";
//            ixFileProfileData.get(i).setId(UUID.randomUUID());
			String host1 = "O=Org1, L=London, C=GB";
//			String identifier = "12345--13-131414"; 
			String identifier = UUID;
			String timeStamp = time;
			SignedTransaction result;
		    CordaClient cordaclient  = new CordaClient();
			CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
			result = cordarpcops.startTrackedFlowDynamic(FileProfileDataFlow.class,ixFileProfileData.get(i).getFilename(),timeStamp,ixFileProfileData.get(i).getHash(), identifier,ixFileProfileData.get(i).getNashost(), ixFileProfileData.get(i).getSymlink(), ixFileProfileData.get(i).getUid(),ixFileProfileData.get(i).getUsername(),ixFileProfileData.get(i).getGid(),
					ixFileProfileData.get(i).getGroupname(),ixFileProfileData.get(i).getUnixuserperm(),ixFileProfileData.get(i).getUnixgroupperm(),ixFileProfileData.get(i).getUnixotherperm(),ixFileProfileData.get(i).getWinuserperms(),ixFileProfileData.get(i).getWingroupperms(),ixFileProfileData.get(i).getWinotherperms(),ixFileProfileData.get(i).getCtime(),ixFileProfileData.get(i).getAtime(),ixFileProfileData.get(i).getMtime(),ixFileProfileData.get(i).getBtime(),ixFileProfileData.get(i).getSize(),ixFileProfileData.get(i).getInode(),ixFileProfileData.get(i).getDevname(),ixFileProfileData.get(i).getDevtype(),
					ixFileProfileData.get(i).getDevmajor(),ixFileProfileData.get(i).getDevminor(),ixFileProfileData.get(i).getFstype(),ixFileProfileData.get(i).getPiientities(),host1).getReturnValue().get();
			ResponseEntity<String> res = ResponseEntity.status(HttpStatus.CREATED).body("Signed Tx_id: "+ result.getId());
			String body = res.getBody();
			System.out.println(body);
		}

	        return ixFileProfileData;
	    }
	
	//Compare hashes from offledger & input payload and find modified records and persist same records into Blockchain
	 public List<FileProfile> updateRecordOffchainOnchain(List<FileProfile> ixFileProfileData, String remId,String time) throws NoSuchAlgorithmException, JsonProcessingException, AuditTrailException, InterruptedException, ExecutionException, SQLException {

//		 	AuditTrail auditTrail = new AuditTrail();
			GetDB getDB = new GetDB();
			List<FileProfile> offchainSet = getDB.fetchFileProfileRecord(remId);
			
			for (int i = 0; i < ixFileProfileData.size(); i++) {
//				ixFileProfileData.get(i).setId(offchainSet.get(i).getId());
	            HashCode hash = new HashCode();
	            String hashValue = hash.getHashOfFileProfileData(ixFileProfileData.get(i));
	            ixFileProfileData.get(i).setHash(hashValue);
	            ixFileProfileData.get(i).setRemId(remId);
	            String  UUID = "\""+offchainSet.get(i).getId()+"\"";
	            System.out.println("uid value "+UUID);
	                
	           	 if(ixFileProfileData.get(i).getHash().equalsIgnoreCase(offchainSet.get(i).getHash())) {
	           		 ixFileProfileData.get(i).setHash("null");
	           		 
	           	 }
	           	 if(ixFileProfileData.get(i).getFilename().equalsIgnoreCase(offchainSet.get(i).getFilename())) {
	           		 ixFileProfileData.get(i).setFilename("null"); 
	           	 }

	           	 if(ixFileProfileData.get(i).getNashost().equalsIgnoreCase(offchainSet.get(i).getNashost())) {
	           		 ixFileProfileData.get(i).setNashost("null");;
	           	 }
	           	 if(ixFileProfileData.get(i).getSymlink().equalsIgnoreCase(offchainSet.get(i).getSymlink())) {
	           		 ixFileProfileData.get(i).setSymlink("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getUid().equalsIgnoreCase(offchainSet.get(i).getUid())) {
	           		 ixFileProfileData.get(i).setUid("null"); 
	           	 }
	           	 if(ixFileProfileData.get(i).getUsername().equalsIgnoreCase(offchainSet.get(i).getUsername())) {
	           		 ixFileProfileData.get(i).setUsername("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getGid().equalsIgnoreCase(offchainSet.get(i).getGid())) {
	           		 ixFileProfileData.get(i).setGid("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getGroupname().equalsIgnoreCase(offchainSet.get(i).getGroupname())) {
	           		 ixFileProfileData.get(i).setGroupname("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getUnixuserperm().equalsIgnoreCase(offchainSet.get(i).getUnixuserperm())) {
	           		 ixFileProfileData.get(i).setUnixuserperm("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getUnixgroupperm().equalsIgnoreCase(offchainSet.get(i).getUnixgroupperm())) {
	           		 ixFileProfileData.get(i).setUnixgroupperm("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getUnixotherperm().equalsIgnoreCase(offchainSet.get(i).getUnixotherperm())) {
	           		 ixFileProfileData.get(i).setUnixotherperm("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getWinuserperms().equalsIgnoreCase(offchainSet.get(i).getWinuserperms())) {
	           		 ixFileProfileData.get(i).setWinuserperms("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getWingroupperms().equalsIgnoreCase(offchainSet.get(i).getWingroupperms())) {
	           		 ixFileProfileData.get(i).setWingroupperms("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getWinotherperms().equalsIgnoreCase(offchainSet.get(i).getWinotherperms())) {
	           		 ixFileProfileData.get(i).setWinotherperms("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getCtime().equalsIgnoreCase(offchainSet.get(i).getCtime())) {
	           		 ixFileProfileData.get(i).setCtime("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getAtime().equalsIgnoreCase(offchainSet.get(i).getAtime())) {
	           		 ixFileProfileData.get(i).setAtime("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getMtime().equalsIgnoreCase(offchainSet.get(i).getMtime())) {
	           		 ixFileProfileData.get(i).setMtime("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getBtime().equalsIgnoreCase(offchainSet.get(i).getBtime())) {
	           		 ixFileProfileData.get(i).setBtime("null"); 
	           	 }
	           	 if(ixFileProfileData.get(i).getSize().equalsIgnoreCase(offchainSet.get(i).getSize())) {
	           		 ixFileProfileData.get(i).setSize("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getInode().equalsIgnoreCase(offchainSet.get(i).getInode())) {
	           		 ixFileProfileData.get(i).setInode("null"); 
	           	 }
	           	 if(ixFileProfileData.get(i).getDevname().equalsIgnoreCase(offchainSet.get(i).getDevname())) {
	           		 ixFileProfileData.get(i).setDevname("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getDevtype().equalsIgnoreCase(offchainSet.get(i).getDevtype())) {
	           		 ixFileProfileData.get(i).setDevtype("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getDevmajor().equalsIgnoreCase(offchainSet.get(i).getDevmajor())) {
	           		 ixFileProfileData.get(i).setDevmajor("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getDevminor().equalsIgnoreCase(offchainSet.get(i).getDevminor())) {
	           		 ixFileProfileData.get(i).setDevminor("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getFstype().equalsIgnoreCase(offchainSet.get(i).getFstype())) {
	           		 ixFileProfileData.get(i).setFstype("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getPiientities().equalsIgnoreCase(offchainSet.get(i).getPiientities())) {
	           		 ixFileProfileData.get(i).setPiientities("null");
	           	 }
//	           	 else {
	           	 //Corda insertion starts here
		           else{
			         	System.out.println("nochanges");
			         }
	 			String host1 = "O=Org1, L=London, C=GB";
	 			String identifier = UUID; 
	 			SignedTransaction result;
	 			String timeStamp = time;
	 		    CordaClient cordaclient  = new CordaClient();
	 			CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
	 			result = cordarpcops.startTrackedFlowDynamic(FileProfileDataFlow.class,ixFileProfileData.get(i).getFilename(), timeStamp,ixFileProfileData.get(i).getHash(),identifier,ixFileProfileData.get(i).getNashost(), ixFileProfileData.get(i).getSymlink(), ixFileProfileData.get(i).getUid(),ixFileProfileData.get(i).getUsername(),ixFileProfileData.get(i).getGid(),
	 					ixFileProfileData.get(i).getGroupname(),ixFileProfileData.get(i).getUnixuserperm(),ixFileProfileData.get(i).getUnixgroupperm(),ixFileProfileData.get(i).getUnixotherperm(),ixFileProfileData.get(i).getWinuserperms(),ixFileProfileData.get(i).getWingroupperms(),ixFileProfileData.get(i).getWinotherperms(),ixFileProfileData.get(i).getCtime(),ixFileProfileData.get(i).getAtime(),ixFileProfileData.get(i).getMtime(),ixFileProfileData.get(i).getBtime(),ixFileProfileData.get(i).getSize(),ixFileProfileData.get(i).getInode(),ixFileProfileData.get(i).getDevname(),ixFileProfileData.get(i).getDevtype(),
	 					ixFileProfileData.get(i).getDevmajor(),ixFileProfileData.get(i).getDevminor(),ixFileProfileData.get(i).getFstype(),ixFileProfileData.get(i).getPiientities(),host1).getReturnValue().get();
	 			ResponseEntity<String> res = ResponseEntity.status(HttpStatus.CREATED).body("Signed Tx_id: "+ result.getId());
	 			String body = res.getBody();
	 			System.out.println(body);
//	           	 }
//		           else{
//			         	System.out.println("nochanges");
//			         }

	        }
			return ixFileProfileData;

		}
	 
		//Compare hashes from offledger & input payload and find modified records and persist same records into Blockchain
	 public List<FileProfile> DeleteRecordOffchainOnchain(List<FileProfile> ixFileProfileData, String remId) throws NoSuchAlgorithmException, JsonProcessingException, AuditTrailException, InterruptedException, ExecutionException, SQLException {

//		 	AuditTrail auditTrail = new AuditTrail();
			GetDB getDB = new GetDB();
			List<FileProfile> offchainSet = getDB.fetchFileProfileRecord(remId);
			
			for (int i = 0; i < ixFileProfileData.size(); i++) {
//				ixFileProfileData.get(i).setId(offchainSet.get(i).getId());
	            HashCode hash = new HashCode();
	            String hashValue = hash.getHashOfFileProfileData(ixFileProfileData.get(i));
	            ixFileProfileData.get(i).setHash(hashValue);
	            ixFileProfileData.get(i).setRemId(remId);
	            String  UUID = "\""+offchainSet.get(i).getId()+"\"";
	            System.out.println("uid value "+UUID);
	                
	           	 if(ixFileProfileData.get(i).getHash().equalsIgnoreCase(offchainSet.get(i).getHash())) {
	           		 ixFileProfileData.get(i).setHash("null");
	           		 
	           	 }
	           	 if(ixFileProfileData.get(i).getFilename().equalsIgnoreCase(offchainSet.get(i).getFilename())) {
	           		 ixFileProfileData.get(i).setFilename("null"); 
	           	 }

	           	 if(ixFileProfileData.get(i).getNashost().equalsIgnoreCase(offchainSet.get(i).getNashost())) {
	           		 ixFileProfileData.get(i).setNashost("null");;
	           	 }
	           	 if(ixFileProfileData.get(i).getSymlink().equalsIgnoreCase(offchainSet.get(i).getSymlink())) {
	           		 ixFileProfileData.get(i).setSymlink("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getUid().equalsIgnoreCase(offchainSet.get(i).getUid())) {
	           		 ixFileProfileData.get(i).setUid("null"); 
	           	 }
	           	 if(ixFileProfileData.get(i).getUsername().equalsIgnoreCase(offchainSet.get(i).getUsername())) {
	           		 ixFileProfileData.get(i).setUsername("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getGid().equalsIgnoreCase(offchainSet.get(i).getGid())) {
	           		 ixFileProfileData.get(i).setGid("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getGroupname().equalsIgnoreCase(offchainSet.get(i).getGroupname())) {
	           		 ixFileProfileData.get(i).setGroupname("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getUnixuserperm().equalsIgnoreCase(offchainSet.get(i).getUnixuserperm())) {
	           		 ixFileProfileData.get(i).setUnixuserperm("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getUnixgroupperm().equalsIgnoreCase(offchainSet.get(i).getUnixgroupperm())) {
	           		 ixFileProfileData.get(i).setUnixgroupperm("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getUnixotherperm().equalsIgnoreCase(offchainSet.get(i).getUnixotherperm())) {
	           		 ixFileProfileData.get(i).setUnixotherperm("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getWinuserperms().equalsIgnoreCase(offchainSet.get(i).getWinuserperms())) {
	           		 ixFileProfileData.get(i).setWinuserperms("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getWingroupperms().equalsIgnoreCase(offchainSet.get(i).getWingroupperms())) {
	           		 ixFileProfileData.get(i).setWingroupperms("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getWinotherperms().equalsIgnoreCase(offchainSet.get(i).getWinotherperms())) {
	           		 ixFileProfileData.get(i).setWinotherperms("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getCtime().equalsIgnoreCase(offchainSet.get(i).getCtime())) {
	           		 ixFileProfileData.get(i).setCtime("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getAtime().equalsIgnoreCase(offchainSet.get(i).getAtime())) {
	           		 ixFileProfileData.get(i).setAtime("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getMtime().equalsIgnoreCase(offchainSet.get(i).getMtime())) {
	           		 ixFileProfileData.get(i).setMtime("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getBtime().equalsIgnoreCase(offchainSet.get(i).getBtime())) {
	           		 ixFileProfileData.get(i).setBtime("null"); 
	           	 }
	           	 if(ixFileProfileData.get(i).getSize().equalsIgnoreCase(offchainSet.get(i).getSize())) {
	           		 ixFileProfileData.get(i).setSize("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getInode().equalsIgnoreCase(offchainSet.get(i).getInode())) {
	           		 ixFileProfileData.get(i).setInode("null"); 
	           	 }
	           	 if(ixFileProfileData.get(i).getDevname().equalsIgnoreCase(offchainSet.get(i).getDevname())) {
	           		 ixFileProfileData.get(i).setDevname("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getDevtype().equalsIgnoreCase(offchainSet.get(i).getDevtype())) {
	           		 ixFileProfileData.get(i).setDevtype("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getDevmajor().equalsIgnoreCase(offchainSet.get(i).getDevmajor())) {
	           		 ixFileProfileData.get(i).setDevmajor("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getDevminor().equalsIgnoreCase(offchainSet.get(i).getDevminor())) {
	           		 ixFileProfileData.get(i).setDevminor("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getFstype().equalsIgnoreCase(offchainSet.get(i).getFstype())) {
	           		 ixFileProfileData.get(i).setFstype("null");
	           	 }
	           	 if(ixFileProfileData.get(i).getPiientities().equalsIgnoreCase(offchainSet.get(i).getPiientities())) {
	           		 ixFileProfileData.get(i).setPiientities("null");
	           	 }
//	           	 else {
	           	 //Corda insertion starts here
		           else{
			         	System.out.println("nochanges");
			         }
	 			String host1 = "O=Org1, L=London, C=GB";
	 			String identifier = UUID; 
	 			SignedTransaction result;
	 		    CordaClient cordaclient  = new CordaClient();
	 			CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
	 			result = cordarpcops.startTrackedFlowDynamic(FileProfileDataFlow.class,ixFileProfileData.get(i).getFilename(), ixFileProfileData.get(i).getHash(),identifier,ixFileProfileData.get(i).getNashost(), ixFileProfileData.get(i).getSymlink(), ixFileProfileData.get(i).getUid(),ixFileProfileData.get(i).getUsername(),ixFileProfileData.get(i).getGid(),
	 					ixFileProfileData.get(i).getGroupname(),ixFileProfileData.get(i).getUnixuserperm(),ixFileProfileData.get(i).getUnixgroupperm(),ixFileProfileData.get(i).getUnixotherperm(),ixFileProfileData.get(i).getWinuserperms(),ixFileProfileData.get(i).getWingroupperms(),ixFileProfileData.get(i).getWinotherperms(),ixFileProfileData.get(i).getCtime(),ixFileProfileData.get(i).getAtime(),ixFileProfileData.get(i).getMtime(),ixFileProfileData.get(i).getBtime(),ixFileProfileData.get(i).getSize(),ixFileProfileData.get(i).getInode(),ixFileProfileData.get(i).getDevname(),ixFileProfileData.get(i).getDevtype(),
	 					ixFileProfileData.get(i).getDevmajor(),ixFileProfileData.get(i).getDevminor(),ixFileProfileData.get(i).getFstype(),ixFileProfileData.get(i).getPiientities(),host1).getReturnValue().get();
	 			ResponseEntity<String> res = ResponseEntity.status(HttpStatus.CREATED).body("Signed Tx_id: "+ result.getId());
	 			String body = res.getBody();
	 			System.out.println(body);
//	           	 }
//		           else{
//			         	System.out.println("nochanges");
//			         }

	        }
			return ixFileProfileData;

		}
	
	//get All fileprofiles from Blockchain with given rem_id
		public ArrayList<FileProfile> getAllFileProfiles(){
	 
	 		CordaClient cordaclient = new CordaClient();
	 		CordaRPCOps rpcproxy = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
	 		ArrayList<FileProfile> fprofs = new ArrayList<FileProfile>();
	 		Page<FileProfileDataState> datastate = rpcproxy.vaultQuery(FileProfileDataState.class);
	 		Iterable<StateAndRef<FileProfileDataState>> allstates = datastate.getStates();
	 		Iterator<StateAndRef<FileProfileDataState>> statesiterator = allstates.iterator();
	 		while (statesiterator.hasNext()){
	 			FileProfile fprof = new FileProfile();
	 			StateAndRef<FileProfileDataState> thisstate = statesiterator.next();
	 			fprof.setFilename(thisstate.getState().getData().getFileName());
	 			fprof.setHash((thisstate.getState().getData().getOffChainHash()));
//	 			fprof.setId(UUID.fromString(thisstate.getState().getData().getIdentifier()));
	 			fprof.setIdentifier(thisstate.getState().getData().getIdentifier());
	 			fprof.setNashost(thisstate.getState().getData().getNasHost());
	 			fprof.setSymlink(thisstate.getState().getData().getSymlink());
	 			fprof.setUid(thisstate.getState().getData().getUid());
	 			fprof.setUsername(thisstate.getState().getData().getUserName());
	 			fprof.setGid(thisstate.getState().getData().getGid());
	 			fprof.setGroupname(thisstate.getState().getData().getGroupName());
	 			fprof.setUnixuserperm(thisstate.getState().getData().getUnixUserPerm());
	 			fprof.setUnixgroupperm(thisstate.getState().getData().getUnixGroupPerm());
	 			fprof.setUnixotherperm(thisstate.getState().getData().getUnixOtherPerm());
	 			fprof.setWinuserperms(thisstate.getState().getData().getWinUserPerms());
	 			fprof.setWingroupperms(thisstate.getState().getData().getWinGroupPerms());
	 			fprof.setWinotherperms(thisstate.getState().getData().getWinOtherPerms());
	 			fprof.setCtime(thisstate.getState().getData().getCTime());
	 			fprof.setAtime(thisstate.getState().getData().getATime());
	 			fprof.setMtime(thisstate.getState().getData().getMTime());
	 			fprof.setBtime(thisstate.getState().getData().getBTime());
	 			fprof.setSize(thisstate.getState().getData().getSize());
	 			fprof.setInode(thisstate.getState().getData().getINode());
	 			fprof.setDevname(thisstate.getState().getData().getDevName());
	 			fprof.setDevtype(thisstate.getState().getData().getDevType());
	 			fprof.setDevmajor(thisstate.getState().getData().getDevMajor());
	 			fprof.setDevminor(thisstate.getState().getData().getDevMinor());
	 			fprof.setDevtype(thisstate.getState().getData().getDevType());
	 			fprof.setFstype(thisstate.getState().getData().getFsType());
	 			fprof.setPiientities(thisstate.getState().getData().getPiiEntities());
	 			fprofs.add(fprof);
	 		}
	 		Collections.reverse(fprofs);
	 		return fprofs;
	 	}
	 }


	 
	 