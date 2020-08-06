//package bccontrol;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.DateFormat.Field;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Iterator;
//
//import com.datadyn.flows.DataFlow;
//import org.springframework.beans.factory.annotation.Value;
//import com.datdyn.state.DataState;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import net.corda.core.contracts.StateAndRef;
//import net.corda.core.messaging.CordaRPCOps;
//import net.corda.core.messaging.FlowHandle;
//import net.corda.core.node.services.Vault.Page;
//import net.corda.core.node.services.vault.QueryCriteria;
//
//
///***********************************************************************
//*
//* This version is experimental, lots of hard coded stuff and quick
//* hacks as a trade-off for early POC delivery.
//*
//* It will get better in time, if you find something broken - fix it !
//*
//***********************************************************************/
//
//
//
//
//public class PIIOps {
//
//	private static String cordauser;
//	private static String cordapassword;
//	private static String cordanode0;
//	private static  String cordadevnode;
//	private static  String sampleDataPath;
//
//	public PIIOps(String cordauser, String cordapassword, String cordanode0, String sampleDataPath) {
//			this.cordauser = cordauser;
//			this.cordapassword = cordapassword;
//			this.cordanode0 = cordanode0;
//			this.sampleDataPath = sampleDataPath;
//	}
//
//	public ArrayList<PII> getAllPII(){
//
//		CordaClient cordaclient = new CordaClient();
//		CordaRPCOps rpcproxy = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//		ArrayList<PII> piis = new ArrayList<PII>();
//		Page<DataState> datastate = rpcproxy.vaultQuery(DataState.class);
//		Iterable<StateAndRef<DataState>> allstates = datastate.getStates();
//		Iterator<StateAndRef<DataState>> statesiterator = allstates.iterator();
//		while (statesiterator.hasNext()){
//			PII pii = new PII();
//			StateAndRef<DataState> thisstate = statesiterator.next();
//			pii.setName(thisstate.getState().getData().getName());
//			pii.setEmail(thisstate.getState().getData().getEmail());
//			pii.setAddress(thisstate.getState().getData().getAddress());
//			pii.setMobileNo(thisstate.getState().getData().getMobileNo());
//			pii.setGender(thisstate.getState().getData().getGender());
//			pii.setDob(thisstate.getState().getData().getDob());
//			pii.setProfession(thisstate.getState().getData().getProfession());
//			pii.setHashref(thisstate.getRef().getTxhash().toString());
//			piis.add(pii);
//		}
//		Collections.reverse(piis);
//		return piis;
//	}
//
//
//	public ArrayList<PII> getPIIByName(String name){
//		CordaClient cordaclient = new CordaClient();
//		CordaRPCOps rpcproxy = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//		ArrayList<PII> piis = new ArrayList<PII>();
//		Page<DataState> datastate = rpcproxy.vaultQuery(DataState.class);
//		Iterable<StateAndRef<DataState>> allstates = datastate.getStates();
//		Iterator<StateAndRef<DataState>> statesiterator = allstates.iterator();
//		while (statesiterator.hasNext()){
//			PII pii = new PII();
//			StateAndRef<DataState> thisstate = statesiterator.next();
//			if (name.equals(thisstate.getState().getData().getName())) {
//				pii.setName(thisstate.getState().getData().getName());
//				pii.setEmail(thisstate.getState().getData().getEmail());
//				pii.setAddress(thisstate.getState().getData().getAddress());
//				pii.setMobileNo(thisstate.getState().getData().getMobileNo());
//				pii.setGender(thisstate.getState().getData().getGender());
//				pii.setDob(thisstate.getState().getData().getDob());
//				pii.setProfession(thisstate.getState().getData().getProfession());
//				piis.add(pii);
//
//			}
//		}
//		return piis;
//	}
//
//
//	public ArrayList<PII> scanAndTransact(){
//		// build an array list of everything in the blockchain
//		ArrayList<PII> bcpii = this.getAllPII();
//		//Collections.reverse(bcpii);
//
//		// build an array listof all external PII
//		try {
//			ArrayList<PII> fspii = this.readPIIFromFS();
//			// iterate through array from fs, for each object verify if there is an object.name and object.dop in the array from bs
//			// if yes, then verify if all the fields are the same ... if yes, then discard, if no then add to blockchain
//
//			Iterator<PII> fsiterator = fspii.iterator();
//			while (fsiterator.hasNext()) {
//
//				PII thisfspii = fsiterator.next();
//				String piifoundinbc = "n";
//
//				Iterator<PII> bciterator = bcpii.iterator();
//				while(bciterator.hasNext()) {
//					PII thisbcpii = bciterator.next();
//
//
//					if (thisfspii.getName().equals(thisbcpii.getName()) && thisfspii.getDob().equals(thisbcpii.getDob())) {
//						piifoundinbc = "y";
//						String piiinfstobetransacted = "n";
//						// name and dob are the same for the record in fs and bc
//						// now verify if any of the other fields have changed
//
//
//						// verify address change
//
//						if (thisfspii.getAddress().equals(thisbcpii.getAddress())) {
//
//						} else {
//							// mark object for transaction
//							piiinfstobetransacted = "y";
//						}
//
//						// verify phone number
//						if (thisfspii.getMobileNo().equals(thisbcpii.getMobileNo())) {
//
//						} else {
//							// mark object
//							piiinfstobetransacted = "y";
//						}
//
//						// verify email address
//						if (thisfspii.getEmail().equals(thisbcpii.getEmail())) {
//
//						} else {
//							// mark object for transaction
//							piiinfstobetransacted = "y";
//						}
//
//
//						// verify mobile phone number
//						if (thisfspii.getMobileNo().equals(thisbcpii.getMobileNo())) {
//
//						} else {
//							// mark object for transaction
//							piiinfstobetransacted = "y";
//						}
//
//						// verify profession
//						if (thisfspii.getProfession().equals(thisbcpii.getProfession())) {
//
//						} else {
//							// mark object for transaction
//							piiinfstobetransacted = "y";
//						}
//
//
//						// at this point the name is the same but we may have found changes
//						// if we found changes in any of the other fields then we transact
//						if (piiinfstobetransacted.equals("y")) {
//							this.transactSinglePII(thisfspii);
//						}
//
//						// at this point we do not want to search any further in the blockchain, we already found the latest state with this name
//						// therefore we will break out of this loop so that we can move to the next PII in the data on the file system
//						break;
//					}
//				}
//
//				// if we have not found the entry in the block chain then we need to transact a new object here
//				if (piifoundinbc.equals("n")) {
//					this.transactSinglePII(thisfspii);
//				}
//
//
//			}
//
//
//
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return this.getAllPII();
//	}
//
//
//
//	public ArrayList<PII> createAuditTrail(String name){
//		ArrayList<PII> piis = this.getPIIByName(name);
//		ArrayList<PII> outpiis = new ArrayList<PII>();
//		PII prevpii = new PII();
//		Iterator<PII> iterator = piis.iterator();
//		while (iterator.hasNext()) {
//			PII currentpii = iterator.next();
//			if (prevpii.getName() == null) {
//				prevpii = currentpii;
//				currentpii.setAuditdescr("The record for client "+ currentpii.getName() + " has been newly discovered and locked in the blockchain");
//				outpiis.add(currentpii);
//			} else {
//
//				// compare address change
//				if (! prevpii.getAddress().equals(currentpii.getAddress())){
//					currentpii.setAuditdescr("Client "+ currentpii.getName() + " had an address change. "+ currentpii.getName() + " moved from: <b>"+ prevpii.getAddress()+ "</b> to <b> " + currentpii.getAddress()+ "</b>." );
//					outpiis.add(currentpii);
//				}
//
//				// compare email change
//				if (! prevpii.getEmail().equals(currentpii.getEmail())){
//					currentpii.setAuditdescr("Client "+ currentpii.getName() + " had an email address change. "+ currentpii.getName() + "'s email changed from: <b>"+ prevpii.getEmail()+ "</b> to <b> " + currentpii.getEmail()+ "</b>." );
//					outpiis.add(currentpii);
//				}
//
//				// compare phone change
//				if (! prevpii.getMobileNo().equals(currentpii.getMobileNo())){
//					currentpii.setAuditdescr("Client "+ currentpii.getName() + " had a mobile phone number change. "+ currentpii.getName() + "'s phone number changed from: <b>"+ prevpii.getMobileNo()+ "</b> to <b> " + currentpii.getMobileNo()+ "</b>." );
//					outpiis.add(currentpii);
//				}
//
//				// compare profession change
//				if (! prevpii.getProfession().equals(currentpii.getProfession())){
//					currentpii.setAuditdescr("Client "+ currentpii.getName() + " has changed jobs. "+ currentpii.getName() + "'s job description changed from: <b>"+ prevpii.getProfession()+ "</b> to <b> " + currentpii.getProfession()+ "</b>." );
//					outpiis.add(currentpii);
//				}
//
//
//			}
//			prevpii = currentpii;
//		}
//		Collections.reverse(outpiis);
//		return outpiis;
//	}
//
//
//
//	public PII transactSinglePII(PII pii) {
//
//		FlowHandle flowhandle = null;
//	    CordaClient cordaclient  = new CordaClient();
//		CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//	    flowhandle = cordarpcops.startFlowDynamic(DataFlow.class, pii.getName() ,pii.getAddress(), pii.getEmail(), pii.getMobileNo(), pii.getGender(), pii.getDob(), pii.getProfession());
//	    return null;
//	}
//
//
//	public ArrayList<PII> readPIIFromFS() throws JsonParseException, JsonMappingException, IOException{
//		ArrayList<PII> piis = new ArrayList<PII>();
//
//		// Creates an array in which we will store the names of files and directories
//	    String[] pathnames;
//
//	    // Creates a new File instance by converting the given pathname string
//	    // into an abstract pathname
//
//	    File f = new File(this.sampleDataPath);
//
//	    // Populates the array with names of files and directories
//	    pathnames = f.list();
//
//	    // For each pathname in the pathnames array
//	    for (String pathname : pathnames) {
//
//	    	//create ObjectMapper instance
//	        ObjectMapper objectMapper = new ObjectMapper();
//	        //read json file and convert to customer object
//	    	PII piidata = objectMapper.readValue(new File(sampleDataPath+"/"+pathname), PII.class);
//	        piis.add(piidata);
//	    }
//		return  piis;
//	}
//
//}
//
//
//
//
//
//
