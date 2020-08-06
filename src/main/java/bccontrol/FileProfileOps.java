//package bccontrol;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//
//import com.datadyn.flows.FileProfileDataFlow;
//import com.datdyn.schema.FileProfileDataSchemaV1;
//import com.datdyn.state.DataState;
//import net.corda.core.messaging.DataFeed;
//import net.corda.core.node.services.Vault;
//import net.corda.core.node.services.vault.*;
//import com.datdyn.state.FileProfileDataState;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import net.corda.core.contracts.StateAndRef;
//import net.corda.core.messaging.CordaRPCOps;
//import net.corda.core.messaging.FlowHandle;
//import net.corda.core.node.services.Vault.Page;
//import org.springframework.beans.factory.annotation.Autowired;
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
//public class FileProfileOps {
//
//	private static String cordauser;
//	private static String cordapassword;
//	private static String cordanode0;
//	private static  String cordadevnode;
//	private static  String sampleDataPath;
//
//	public FileProfileOps(String cordauser, String cordapassword, String cordanode0, String sampleDataPath) {
//			this.cordauser = cordauser;
//			this.cordapassword = cordapassword;
//			this.cordanode0 = cordanode0;
//			this.sampleDataPath = sampleDataPath;
//	}
//
//	public ArrayList<FileProfile> getAllFileProfiles(){
//
//		CordaClient cordaclient = new CordaClient();
//		CordaRPCOps rpcproxy = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//		ArrayList<FileProfile> fprofs = new ArrayList<FileProfile>();
//		Page<FileProfileDataState> datastate = rpcproxy.vaultQuery(FileProfileDataState.class);
//		Iterable<StateAndRef<FileProfileDataState>> allstates = datastate.getStates();
//		Iterator<StateAndRef<FileProfileDataState>> statesiterator = allstates.iterator();
//		while (statesiterator.hasNext()){
//			FileProfile fprof = new FileProfile();
//			StateAndRef<FileProfileDataState> thisstate = statesiterator.next();
//			fprof.setFilename(thisstate.getState().getData().getFileName());
//			fprof.setNashost(thisstate.getState().getData().getNasHost());
//			fprof.setFilepath(thisstate.getState().getData().getFilePath());
//			fprof.setSymlink(thisstate.getState().getData().getSymlink());
//			fprof.setUid(thisstate.getState().getData().getUid());
//			fprof.setUsername(thisstate.getState().getData().getUserName());
//			fprof.setGid(thisstate.getState().getData().getGid());
//			fprof.setGroupname(thisstate.getState().getData().getGroupName());
//			fprof.setUnixuserperm(thisstate.getState().getData().getUnixUserPerm());
//			fprof.setUnixgroupperm(thisstate.getState().getData().getUnixGroupPerm());
//			fprof.setUnixotherperm(thisstate.getState().getData().getUnixOtherPerm());
//			fprof.setWinuserperms(thisstate.getState().getData().getWinUserPerms());
//			fprof.setWingroupperms(thisstate.getState().getData().getWinGroupPerms());
//			fprof.setWinotherperms(thisstate.getState().getData().getWinOtherPerms());
//			fprof.setCtime(thisstate.getState().getData().getCTime());
//			fprof.setAtime(thisstate.getState().getData().getATime());
//			fprof.setMtime(thisstate.getState().getData().getMTime());
//			fprof.setBtime(thisstate.getState().getData().getBTime());
//			fprof.setSize(thisstate.getState().getData().getSize());
//			fprof.setInode(thisstate.getState().getData().getINode());
//			fprof.setDevname(thisstate.getState().getData().getDevName());
//			fprof.setDevtype(thisstate.getState().getData().getDevType());
//			fprof.setDevmajor(thisstate.getState().getData().getDevMajor());
//			fprof.setDevminor(thisstate.getState().getData().getDevMinor());
//			fprof.setDevtype(thisstate.getState().getData().getDevType());
//			fprof.setFstype(thisstate.getState().getData().getFsType());
//			fprofs.add(fprof);
//		}
//		Collections.reverse(fprofs);
//		return fprofs;
//	}
//
///*
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
//*/
//
//
//
//	public ArrayList<FileProfile> scanAndTransactFileProfiles(){
//		// build an array list of everything in the blockchain
//		ArrayList<FileProfile> bcfileprofile = this.getAllFileProfiles();
//		//Collections.reverse(bcpii);
//
//		// build an array list of all external FileProfiles
//		try {
//			ArrayList<FileProfile> fsfileprofile = this.readFileProfileFromFS();
//			// iterate through array from fs, for each object verify if there is an object.filepath from bc
//			// if yes, then verify if all the fields are the same ... if yes, then discard, if no then add to blockchain
//
//			Iterator<FileProfile> fsiterator = fsfileprofile.iterator();
//			while (fsiterator.hasNext()) {
//
//				FileProfile thisfsfileprofile = fsiterator.next();
//				String fileprofilefoundinbc = "n";
//
//				Iterator<FileProfile> bciterator = bcfileprofile.iterator();
//				while(bciterator.hasNext()) {
//					FileProfile thisbcfileprofile = bciterator.next();
//
//
//					if (thisfsfileprofile.getFilepath().equals(thisbcfileprofile.getFilepath()) ) {
//						fileprofilefoundinbc = "y";
//						String fileprofileinfstobetransacted = "n";
//						// file path is the same for the record in fs and bc
//						// now verify if any of the other fields have changed
//
//
//						// verify uid change
//
//						if (thisfsfileprofile.getUid().equals(thisbcfileprofile.getUid())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify user name change
//						if (thisfsfileprofile.getUsername().equals(thisbcfileprofile.getUsername())) {
//
//						} else {
//							// mark object
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify group id change
//						if (thisfsfileprofile.getGid().equals(thisbcfileprofile.getGid())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//
//						// verify group name change
//						if (thisfsfileprofile.getGroupname().equals(thisbcfileprofile.getGroupname())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify unixuserperm change
//						if (thisfsfileprofile.getUnixuserperm().equals(thisbcfileprofile.getUnixuserperm())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify unixgroupperm change
//						if (thisfsfileprofile.getUnixgroupperm().equals(thisbcfileprofile.getUnixgroupperm())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify unixotherperm change
//						if (thisfsfileprofile.getUnixotherperm().equals(thisbcfileprofile.getUnixotherperm())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify ctime change
//						if (thisfsfileprofile.getCtime().equals(thisbcfileprofile.getCtime())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify atime change
//						if (thisfsfileprofile.getAtime().equals(thisbcfileprofile.getAtime())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify mtime change
//						if (thisfsfileprofile.getMtime().equals(thisbcfileprofile.getMtime())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify btime change
//						if (thisfsfileprofile.getBtime().equals(thisbcfileprofile.getBtime())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//						// verify size change
//						if (thisfsfileprofile.getSize().equals(thisbcfileprofile.getSize())) {
//
//						} else {
//							// mark object for transaction
//							fileprofileinfstobetransacted = "y";
//						}
//
//
//						// at this point the pathname is the same but we may have found changes
//						// if we found changes in any of the other fields then we transact
//						if (fileprofileinfstobetransacted.equals("y")) {
//							this.transactSingleFileProfile(thisfsfileprofile);
//						}
//
//						// at this point we do not want to search any further in the blockchain, we already found the latest state with this name
//						// therefore we will break out of this loop so that we can move to the next PII in the data on the file system
//						break;
//					}
//				}
//
//				// if we have not found the entry in the block chain then we need to transact a new object here
//				if (fileprofilefoundinbc.equals("n")) {
//					this.transactSingleFileProfile(thisfsfileprofile);
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
//		return this.getAllFileProfiles();
//	}
//
//
//
//	/*
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
//	*/
//
//
//
//
//	public PII transactSingleFileProfile(FileProfile fileprofile) {
//
//		FlowHandle flowhandle = null;
//	    CordaClient cordaclient  = new CordaClient();
//		CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//	    flowhandle = cordarpcops.startFlowDynamic(FileProfileDataFlow.class, fileprofile.getFilename() ,fileprofile.getNashost(), fileprofile.getFilepath(), fileprofile.getSymlink(), fileprofile.getUid(), fileprofile.getUsername(), fileprofile.getGid(), fileprofile.getGroupname(), fileprofile.getUnixuserperm(), fileprofile.getUnixgroupperm(), fileprofile.getUnixotherperm(), fileprofile.getWinuserperms(), fileprofile.getWingroupperms(), fileprofile.getWinotherperms(), fileprofile.getCtime(), fileprofile.getAtime(), fileprofile.getMtime(), fileprofile.getBtime(), fileprofile.getSize(), fileprofile.getInode(), fileprofile.getDevname(), fileprofile.getDevtype(), fileprofile.getDevmajor(), fileprofile.getDevminor(), fileprofile.getFstype(), fileprofile.getPiientities());
//	    return null;
//	}
//
//
//	public ArrayList<FileProfile> readFileProfileFromFS() throws JsonParseException, JsonMappingException, IOException{
//		ArrayList<FileProfile> fileProfiles = new ArrayList<FileProfile>();
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
//	    	FileProfile fileProfileData = objectMapper.readValue(new File(sampleDataPath+"/"+pathname), FileProfile.class);
//	        fileProfiles.add(fileProfileData);
//	    }
//		return  fileProfiles;
//	}
//
//	public FileProfile getFileProfileByFilePath(String filePath) throws NoSuchFieldException {
//
//		CordaClient cordaclient = new CordaClient();
//
//		FileProfile fileProfile = new FileProfile();
//
//		CordaRPCOps proxy = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//
//		QueryCriteria generalCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.ALL);
//
//		FieldInfo attributeFilePath = QueryCriteriaUtils.getField("filePath", FileProfileDataSchemaV1.PersistentFileProfileData.class);
//
//		CriteriaExpression accountIdCriteria = Builder.equal(attributeFilePath,filePath);
//		QueryCriteria customCriteria = new QueryCriteria.VaultCustomQueryCriteria(accountIdCriteria);
//		QueryCriteria criteria = generalCriteria.and(customCriteria);
//
//		DataFeed<Page<FileProfileDataState>, Vault.Update<FileProfileDataState>> dataFeed = proxy.vaultTrackByCriteria(FileProfileDataState.class, criteria);
//
//		Vault.Page<FileProfileDataState> snapshot = dataFeed.getSnapshot();
//
//		if(snapshot.getStates().size()>0) {
//
//			FileProfileDataState fs = snapshot.getStates().get(snapshot.getStates().size() - 1).getState().getData();
//
//			fileProfile.setDevtype(fs.getDevType());
//			fileProfile.setAtime(fs.getATime());
//			fileProfile.setBtime(fs.getBTime());
//			fileProfile.setCtime(fs.getCTime());
//			fileProfile.setDevmajor(fs.getDevMajor());
//			fileProfile.setDevminor(fs.getDevMinor());
//			fileProfile.setFstype(fs.getFsType());
//			fileProfile.setGid(fs.getGid());
//			fileProfile.setGroupname(fs.getGroupName());
//			fileProfile.setUnixgroupperm(fs.getUnixGroupPerm());
//			fileProfile.setUnixotherperm(fs.getUnixOtherPerm());
//			fileProfile.setSize(fs.getSize());
//			fileProfile.setWingroupperms(fs.getWinGroupPerms());
//			fileProfile.setWinotherperms(fs.getWinOtherPerms());
//			fileProfile.setWinuserperms(fs.getWinUserPerms());
//			fileProfile.setInode(fs.getINode());
//			fileProfile.setPiientities(fs.getPiiEntities());
//			fileProfile.setUsername(fs.getUserName());
//			fileProfile.setSymlink(fs.getSymlink());
//			fileProfile.setNashost(fs.getNasHost());
//			fileProfile.setMtime(fs.getMTime());
//			fileProfile.setFilename(fs.getFileName());
//			fileProfile.setFilepath(fs.getFilePath());
//			fileProfile.setHostName(fs.getHost1().getName().toString());
//			fileProfile.setUid(fs.getUid());
//			fileProfile.setDevname(fs.getDevName());
//		}
//		return fileProfile;
//	}
//
//	public List<FileProfile> getAllFileProfileByFilePath(String filePath) throws NoSuchFieldException {
//
//		CordaClient cordaclient = new CordaClient();
//
//		List<FileProfile> fileProfileAL = new ArrayList<>();
//
//		CordaRPCOps proxy = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//
//		QueryCriteria generalCriteria = new QueryCriteria.VaultQueryCriteria(Vault.StateStatus.ALL);
//
//		FieldInfo attributeFilePath = QueryCriteriaUtils.getField("filePath", FileProfileDataSchemaV1.PersistentFileProfileData.class);
//
//		CriteriaExpression accountIdCriteria = Builder.equal(attributeFilePath,filePath);
//		QueryCriteria customCriteria = new QueryCriteria.VaultCustomQueryCriteria(accountIdCriteria);
//		QueryCriteria criteria = generalCriteria.and(customCriteria);
//
//		DataFeed<Page<FileProfileDataState>, Vault.Update<FileProfileDataState>> dataFeed = proxy.vaultTrackByCriteria(FileProfileDataState.class, criteria);
//
//		Vault.Page<FileProfileDataState> dataStates = dataFeed.getSnapshot();
//
//			for(int i=0; i<dataStates.getStates().size(); i++) {
//
//				FileProfile fileProfile = new FileProfile();
//
//				FileProfileDataState fs = dataStates.getStates().get(i).getState().getData();
//
//				fileProfile.setDevtype(fs.getDevType());
//				fileProfile.setAtime(fs.getATime());
//				fileProfile.setBtime(fs.getBTime());
//				fileProfile.setCtime(fs.getCTime());
//				fileProfile.setDevmajor(fs.getDevMajor());
//				fileProfile.setDevminor(fs.getDevMinor());
//				fileProfile.setFstype(fs.getFsType());
//				fileProfile.setGid(fs.getGid());
//				fileProfile.setGroupname(fs.getGroupName());
//				fileProfile.setUnixgroupperm(fs.getUnixGroupPerm());
//				fileProfile.setUnixotherperm(fs.getUnixOtherPerm());
//				fileProfile.setSize(fs.getSize());
//				fileProfile.setWingroupperms(fs.getWinGroupPerms());
//				fileProfile.setWinotherperms(fs.getWinOtherPerms());
//				fileProfile.setWinuserperms(fs.getWinUserPerms());
//				fileProfile.setInode(fs.getINode());
//				fileProfile.setPiientities(fs.getPiiEntities());
//				fileProfile.setUsername(fs.getUserName());
//				fileProfile.setSymlink(fs.getSymlink());
//				fileProfile.setNashost(fs.getNasHost());
//				fileProfile.setMtime(fs.getMTime());
//				fileProfile.setFilename(fs.getFileName());
//				fileProfile.setFilepath(fs.getFilePath());
//				fileProfile.setHostName(fs.getHost1().getName().toString());
//				fileProfile.setUid(fs.getUid());
//				fileProfile.setDevname(fs.getDevName());
//				fileProfile.setUnixuserperm(fs.getUnixUserPerm());
//				fileProfileAL.add(fileProfile);
//			}
//		return fileProfileAL;
//	}
//
//	public List<FileProfile> createAuditTrailFileProfile(String filePath) throws NoSuchFieldException {
//		List<FileProfile> fileProfiles = getAllFileProfileByFilePath(filePath);
//
//		List<FileProfile> outFileProfiles = new ArrayList<FileProfile>();
//		FileProfile prevFileProfile = new FileProfile();
//		Iterator<FileProfile> iterator = fileProfiles.iterator();
//
//		while (iterator.hasNext()) {
//
//			FileProfile currentFileProfile = iterator.next();
//
//			if (prevFileProfile.getFilepath() == null) {
//
//				prevFileProfile = currentFileProfile;
//				currentFileProfile.setAuditDescr("The record for client "+ currentFileProfile.getFilepath() + " has been newly discovered and locked in the blockchain");
//				outFileProfiles.add(currentFileProfile);
//			} else {
//
//				// compare filepath change
//				if (! prevFileProfile.getFilepath().equals(currentFileProfile.getFilepath())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a filepath change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getFilepath()+ "</b> to <b> " + currentFileProfile.getFilepath()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//				// compare atime change
//				if (! prevFileProfile.getAtime().equals(currentFileProfile.getAtime())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had an atime change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getAtime()+ "</b> to <b> " + currentFileProfile.getAtime()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare btime change
//				if (! prevFileProfile.getBtime().equals(currentFileProfile.getBtime())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a btime change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getBtime()+ "</b> to <b> " + currentFileProfile.getBtime()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare ctime change
//				if (! prevFileProfile.getCtime().equals(currentFileProfile.getCtime())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a ctime change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getCtime()+ "</b> to <b> " + currentFileProfile.getCtime()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//				// compare devmajor change
//				if (! prevFileProfile.getDevmajor().equals(currentFileProfile.getDevmajor())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a dev major change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getDevmajor()+ "</b> to <b> " + currentFileProfile.getDevmajor()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare devminor change
//				if (! prevFileProfile.getDevminor().equals(currentFileProfile.getDevminor())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a dev minor change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getDevminor()+ "</b> to <b> " + currentFileProfile.getDevminor()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare devname change
//				if (! prevFileProfile.getDevname().equals(currentFileProfile.getDevname())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a dev name change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getDevname()+ "</b> to <b> " + currentFileProfile.getDevname() + "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare devtype change
//				if (! prevFileProfile.getDevtype().equals(currentFileProfile.getDevtype())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a dev type change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getDevtype()+ "</b> to <b> " + currentFileProfile.getDevtype() + "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare filename change
//				if (! prevFileProfile.getFilename().equals(currentFileProfile.getFilename())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a file name change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getFilename()+ "</b> to <b> " + currentFileProfile.getFilename()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare fstype change
//				if (! prevFileProfile.getFstype().equals(currentFileProfile.getFstype())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a fs type change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getFstype()+ "</b> to <b> " + currentFileProfile.getFstype()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare gid change
//				if (! prevFileProfile.getGid().equals(currentFileProfile.getGid())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a gid change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getGid()+ "</b> to <b> " + currentFileProfile.getGid()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare groupname change
//				if (! prevFileProfile.getGroupname().equals(currentFileProfile.getGroupname())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a group name change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getGroupname()+ "</b> to <b> " + currentFileProfile.getGroupname()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare hostname change
//				if (! prevFileProfile.getHostName().equals(currentFileProfile.getHostName())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a host name change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getHostName()+ "</b> to <b> " + currentFileProfile.getHostName()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare inode change
//				if (! prevFileProfile.getInode().equals(currentFileProfile.getInode())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had an inode change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getInode() + "</b> to <b> " + currentFileProfile.getInode()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare mtime change
//				if (! prevFileProfile.getMtime().equals(currentFileProfile.getMtime())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a mtime change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getMtime() + "</b> to <b> " + currentFileProfile.getMtime() + "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare uid change
//				if (! prevFileProfile.getUid().equals(currentFileProfile.getUid())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had an uid change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getUid()+ "</b> to <b> " + currentFileProfile.getUid() + "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare nas host change
//				if (! prevFileProfile.getNashost().equals(currentFileProfile.getNashost())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a nas host change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getNashost()+ "</b> to <b> " + currentFileProfile.getNashost()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare PII Entities change
//				if (! prevFileProfile.getPiientities().equals(currentFileProfile.getPiientities())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a pii entities change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getPiientities()+ "</b> to <b> " + currentFileProfile.getPiientities()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare winuserperms change
//				if (! prevFileProfile.getWinuserperms().equals(currentFileProfile.getWinuserperms())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a win user perms change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getWinuserperms()+ "</b> to <b> " + currentFileProfile.getWinuserperms()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare wingroupperms change
//				if (! prevFileProfile.getWingroupperms().equals(currentFileProfile.getWingroupperms())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a win group perms change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getWingroupperms() + "</b> to <b> " + currentFileProfile.getWingroupperms()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare winotherperms change
//				if (! prevFileProfile.getWinotherperms().equals(currentFileProfile.getWinotherperms())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a win other perms change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getWinotherperms()+ "</b> to <b> " + currentFileProfile.getWinotherperms() + "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare unixuserperm change
//				if (! prevFileProfile.getUnixuserperm().equals(currentFileProfile.getUnixuserperm())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had an unix user perms change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getUnixuserperm() + "</b> to <b> " + currentFileProfile.getUnixuserperm()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare unixgroupperm change
//				if (! prevFileProfile.getUnixgroupperm().equals(currentFileProfile.getUnixgroupperm())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had an unix group perms change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getUnixgroupperm()+ "</b> to <b> " + currentFileProfile.getUnixgroupperm()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare unixotherperm change
//				if (! prevFileProfile.getUnixotherperm().equals(currentFileProfile.getUnixotherperm())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had an unix other perms change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getUnixotherperm()+ "</b> to <b> " + currentFileProfile.getUnixotherperm()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare username change
//				if (! prevFileProfile.getUsername().equals(currentFileProfile.getUsername())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had an username change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getUsername()+ "</b> to <b> " + currentFileProfile.getUsername()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare symlink change
//				if (! prevFileProfile.getSymlink().equals(currentFileProfile.getSymlink())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a symlink change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getSymlink()+ "</b> to <b> " + currentFileProfile.getSymlink()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//
//				// compare size change
//				if (! prevFileProfile.getSize().equals(currentFileProfile.getSize())){
//					currentFileProfile.setAuditDescr("Client "+ currentFileProfile.getFilepath() + " had a size change. "+ currentFileProfile.getFilepath() + " moved from: <b>"+ prevFileProfile.getSize()+ "</b> to <b> " + currentFileProfile.getSize()+ "</b>." );
//					outFileProfiles.add(currentFileProfile);
//				}
//			}
//			prevFileProfile = currentFileProfile;
//		}
//		Collections.reverse(outFileProfiles);
//		return outFileProfiles;
//	}
//}
//
//
//
//
//
//
