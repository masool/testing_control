package bccontrol;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.corda.core.messaging.CordaRPCOps;
import org.springframework.web.client.RestTemplate;

import javax.json.JsonObject;


/***********************************************************************
 *
 * This version is experimental, lots of hard coded stuff and quick
 * hacks as a trade-off for early POC delivery.
 *
 * It will get better in time, if you find something broken - fix it !
 *
 ***********************************************************************/



@RestController
@RequestMapping("/api/1.0")
public class RestControl {

	private final AtomicLong counter = new AtomicLong();

	// retrieve values from application.properties file

	//	@Value("${sampleData.path}")
//	private String sampleDataPath;
	@Value("${sampleFileData.path}")
	private String sampleFileDataPath;
	@Value("${corda.user}")
	private String cordauser;
	@Value("${corda.password}")
	private String cordapassword;
	@Value("${corda.node0}")
	private String cordanode0;
	@Value("${corda.devnode}")
	private String cordadevnode;
	@Value("${azure.kv.agentUrl}")
	private String agentUrl;
	@Value("${azure.kv.secretAPI}")
	private String secretAPI;

	@Value("${encrypt.agent.encryptAPI}")
	private String encryptAPI;


	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	CompareRemSets compRemSets;


	// Endpoints that just return strings, testing only

//	@RequestMapping("/properties")
//	public String getproperty(@RequestParam Map<String,String> requestParams) throws Exception{
//		return cordanode0;
//	}
//
//	@RequestMapping("/cordanodetime")
//	public String cordatime1(@RequestParam Map<String,String> requestParams) throws Exception{
//	    String cordatime;
//	    CordaClient cordaclient  = new CordaClient();
//
//	    CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//	    cordatime = cordarpcops.currentNodeTime().toString();
//	    return cordatime;
//	}
//
//	@RequestMapping("/cordanodeinfo")
//	public String cordanodeinfo(@RequestParam Map<String,String> requestParams) throws Exception{
//	    String nodeinfo;
//	    CordaClient cordaclient  = new CordaClient();
//		CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//	    nodeinfo = cordarpcops.nodeInfo().toString();
//	    return nodeinfo;
//	}
//
//	@RequestMapping("/cordanodediags")
//	public String cordanodeanalysis(@RequestParam Map<String,String> requestParams) throws Exception{
//	    String nodediags;
//	    CordaClient cordaclient  = new CordaClient();
//		CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//	    nodediags = cordarpcops.nodeDiagnosticInfo().toString();
//	    return nodediags;
//	}
//
//	@RequestMapping("/startflow")
//	public String startflow(@RequestParam Map<String,String> requestParams) throws Exception{
//		String name= requestParams.get("name");
//		String address = requestParams.get("address");
//		String email = requestParams.get("email");
//		String mobileno = requestParams.get("mobile");
//		String gender = requestParams.get("gender");
//		String dob = requestParams.get("dob");
//		String profession = requestParams.get("profession");
//		FlowHandle flowhandle = null;
//	    CordaClient cordaclient  = new CordaClient();
//		CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//	    flowhandle = cordarpcops.startFlowDynamic(DataFlow.class, name ,address, email, mobileno, gender, dob, profession);
//	    return flowhandle.toString();
//	}
//
//	@RequestMapping("/getsinglestate")
//	public String getState(@RequestParam Map<String,String> requestParams) throws Exception{
//		int statenum = Integer.parseInt(requestParams.get("statenumber"));
//		Page<DataState> datastate = null;
//	    CordaClient cordaclient  = new CordaClient();
//		CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//	    datastate = cordarpcops.vaultQuery(DataState.class);
//	    return datastate.getStates().get(statenum).getState().toString();
//
//	}
//
//	@GetMapping("/getallstates")
//	public String getAllStates(@RequestParam Map<String,String> requestParams) throws Exception{
//		Page<DataState> datastate = null;
//	    CordaClient cordaclient  = new CordaClient();
//		CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
//	    datastate = cordarpcops.vaultQuery(DataState.class);
//	    return datastate.getStates().toString() + "----------<br></br>" + datastate.getStatesMetadata();
//	}
//
//
//
//	// Endpoints that return JSON via PII object or List<PII> parsed through Spring Boot's built-in Jackson library
//
//	@RequestMapping("/getallpiifrombc")
//	public ArrayList<PII> getAllPIIData(@RequestParam Map<String,String> requestParams) throws Exception{
//		ArrayList<PII> piis = new ArrayList<PII>();
//		PIIOps piiops = new PIIOps(cordauser, cordapassword, cordanode0, sampleDataPath);
//		piis = piiops.getAllPII();
//		return piis;
//	}
//
//	@RequestMapping("/getpiibyname")
//	public ArrayList<PII> getPIIByName(@RequestParam Map<String,String> requestParams) throws Exception{
//		String name = (requestParams.get("name"));
//		ArrayList<PII> piis = new ArrayList<PII>();
//		PIIOps piiops = new PIIOps(cordauser, cordapassword, cordanode0, sampleDataPath);
//		piis = piiops.getPIIByName(name);
//		return piis;
//	}
//
//	@RequestMapping("/scanandtransact")
//	public ArrayList<PII> scanAndTransact(@RequestParam Map<String,String> requestParams) throws Exception{
//		PIIOps piiops = new PIIOps(cordauser, cordapassword, cordanode0, sampleDataPath);
//		ArrayList<PII> piis = piiops.scanAndTransact();
//		return piis;
//	}
//
//	@RequestMapping("/scanandtransactfileprofiles")
//	public ArrayList<FileProfile> scanAndTransactFileProfiles(@RequestParam Map<String,String> requestParams) throws Exception{
//		FileProfileOps fops = new FileProfileOps(cordauser, cordapassword, cordanode0, sampleFileDataPath);
//		ArrayList<FileProfile> piis = fops.scanAndTransactFileProfiles();
//		return piis;
//	}
//
//	@RequestMapping("/createaudittrail")
//	public ArrayList<PII> createAuditTrail(@RequestParam Map<String,String> requestParams) throws Exception{
//		String name = (requestParams.get("name"));
//		PIIOps piiops = new PIIOps(cordauser, cordapassword, cordanode0, sampleDataPath);
//		ArrayList<PII> piis = piiops.createAuditTrail(name);
//		return piis;
//	}
//
//	@RequestMapping("/readpii")
//	public ArrayList<PII> getPIIData(@RequestParam Map<String,String> requestParams) throws Exception{
//		ArrayList<PII> piis = new ArrayList<PII>();
//		String[] pathnames;
//
//	    File f = new File(sampleDataPath);
//	    pathnames = f.list();
//	    for (String pathname : pathnames) {
//	        ObjectMapper objectMapper = new ObjectMapper();
//	    	PII piidata = objectMapper.readValue(new File(sampleDataPath+"/"+pathname), PII.class);
//	        piis.add(piidata);
//	    }
//		return  piis;
//	}
//
//	@RequestMapping("/readfileprofile")
//	public ArrayList<FileProfile> getFileProfileData(@RequestParam Map<String,String> requestParams) throws Exception{
//		FileProfileOps fops = new FileProfileOps(cordauser, cordapassword, cordanode0, sampleFileDataPath);
//		ArrayList<FileProfile> fprofs = fops.readFileProfileFromFS();
//		return fprofs;
//	}
//
//	@RequestMapping("/getallfileprofilesfrombc")
//	public ArrayList<FileProfile> getAllFileProfileData(@RequestParam Map<String,String> requestParams) throws Exception{
//		ArrayList<FileProfile> fileprofiles = new ArrayList<FileProfile>();
//		FileProfileOps fops = new FileProfileOps(cordauser, cordapassword, cordanode0, sampleFileDataPath);
//		fileprofiles = fops.getAllFileProfiles();
//		return fileprofiles;
//	}
//
//	@RequestMapping("/getFileProfileDataByFilePath")
//	public FileProfile getFileProfileDataByFilePath(@RequestParam Map<String,String> requestParams) throws NoSuchFieldException, JsonProcessingException {
//
//		FileProfileOps fops = new FileProfileOps(cordauser, cordapassword, cordanode0, sampleFileDataPath);
//
//		return fops.getFileProfileByFilePath(requestParams.get("filePath"));
//	}
//
//	@RequestMapping("/createAuditTrailFileProfile")
//	public List<FileProfile> createAuditTrailFileProfile(@RequestParam Map<String,String> requestParams) throws Exception{
//
//		FileProfileOps fops = new FileProfileOps(cordauser, cordapassword, cordanode0, sampleFileDataPath);
//
//		List<FileProfile> fileProfiles = fops.createAuditTrailFileProfile(requestParams.get("filePath"));
//
//		return fileProfiles;
//	}
//
//	@PostMapping("/createSecret")
//	public String createSecretOnAZ(@RequestBody Map<String,String> requestParams) throws Exception {
//		RestTemplate restTemplate = new RestTemplate();
//		return restTemplate.postForObject(agentUrl+secretAPI, requestParams, String.class);
//	}

//	//added by gauri
//	@PostMapping("/auditTrailFunction")
//	@CrossOrigin(origins = "*")
//	public String  auditTrailFunction(@RequestBody Map<String,String> requestParams) throws Exception{
//
//		System.out.println("Remidiation ID"+requestParams.get("REM_ID"));
//		System.out.println("Remidiation TYPE"+requestParams.get("REM_TYPE"));
//		System.out.println("Analysis ID"+requestParams.get("ANALYSIS_ID"));
//		AuditTrail auditTrail = new AuditTrail();
//		auditTrail.getToken();
//
//		return "Remediation ID got Successfully";
//	}

	// ---------------------------------------  CORDA NETWORK END POINTS STARTS HERE -----------------------------------------
	// Find peer node connected to this server
	@GetMapping(value = "/me",produces = MediaType.APPLICATION_JSON_VALUE)
	public String cordanodeinformation(@RequestParam Map<String,String> requestParams) throws Exception{
		CordaClient cordaclient  = new CordaClient();
		CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
		String mynode = cordarpcops.nodeInfo().getLegalIdentities().toString();
		return mynode;
	}
	// Get network info of corda connected to this server
	@GetMapping(value = "/peers", produces = MediaType.APPLICATION_JSON_VALUE)
	private String peers() {
		CordaClient cordaclient  = new CordaClient();
		CordaRPCOps cordarpcops = cordaclient.getCordaRPCOps(cordanode0, cordauser, cordapassword);
		String result = cordarpcops.networkMapSnapshot().stream()
				.map(it -> it.getLegalIdentities().toString())
				.collect(Collectors.toList()).toString();
		return result;
	}

	//added by gauri
//	@PostMapping("/audittrail/startaudit")
//	@CrossOrigin(origins = "*")
//	public JSONObject auditTrailFunction(@RequestBody Map<String,String> requestParams) throws Exception{
//
//		List<FileProfile> offchainList = new ArrayList<>();
//		System.out.println("Remedidation ID"+requestParams.get("REM_ID"));
//		System.out.println("Remedidation TYPE"+requestParams.get("REM_TYPE"));
//		System.out.println("Analysis ID"+requestParams.get("ANALYSIS_ID"));
//
//		String remStatus = "ACTIVE";
//		String respMessage = "Audit Trail Enabled Successfully";
//		AuditTrail auditTrail = new AuditTrail();
//
//		GetDB getDB = new GetDB();
//		InsertDB insertDB = new InsertDB();
//		DeleteDB deleteDB = new DeleteDB();
//		UpdateDB updateDB = new UpdateDB();
//
//		auditTrail.getToken(requestParams);
//
//		String remId = requestParams.get("REM_ID");
//
//		List<FileProfile> ixFileProfileData = auditTrail.getFileProfileData(requestParams);
//
//		List<FileProfile> offchainSet = getDB.fetchFileProfileRecord(remId);
//
//		System.out.println("Records no of database records "+offchainSet.size());
//
//		if(offchainSet.size() == 0){
//			System.out.println("No DataFound with RemId as "+remId);
//
//			//Insert records into off chain db
//			String message = insertDB.createAuditTrailRecord(ixFileProfileData,remId);
//
//			//Insert records into blockchain db
//			OffchainToOnchain calculatehash = new OffchainToOnchain(cordauser, cordapassword,cordanode0);
//			List<FileProfile> comResp = calculatehash.InsertRecordintobc(ixFileProfileData,remId);
//			if(!message.equals("success")){
//				remStatus = "Failed";
//				respMessage = message;
//				throw new AuditTrailException(remId,remStatus,respMessage);
//			}
//		}
//		else {
//			ComparisonResults comResp = compRemSets.compareRemediationSet(ixFileProfileData, offchainSet);
//
//			String msg = "";
//			System.out.println("Message "+msg);
//			msg = insertDB.createAuditTrailRecord(comResp.getAdditionSet(),remId);
//			if(!msg.equals("success")){
//				throw new AuditTrailException(remId,"Error",msg);
//			}
//
////			//insert modified records into blockchain
////			OffchainToOnchain calculatehash = new OffchainToOnchain(cordauser, cordapassword,cordanode0);
////			List<FileProfile> UpdcomResp = calculatehash.DeleteRecordOffchainOnchain(ixFileProfileData,remId);
//
//			msg = deleteDB.removeAuditTrailRecord(comResp.getDeletionSet(),remId);
//			if(!msg.equals("success")){
//				throw new AuditTrailException(remId,"Error",msg);
//			}
//
//			System.out.println(" ");
//			System.out.println("Datadynamics: Please wait Corda flow yet to start");
//			System.out.println(" ");
//			
//			//check fileprofile set have modified attributes?  && insert modified records into Blockchain
//			for (int i = 0; i < ixFileProfileData.size(); i++) {
//	            HashCode hash = new HashCode();
//	            String hashValue = hash.getHashOfFileProfileData(ixFileProfileData.get(i));
//	            ixFileProfileData.get(i).setHash(hashValue);
//	            ixFileProfileData.get(i).setRemId(remId);             
//	           	 if(ixFileProfileData.get(i).getHash().equalsIgnoreCase(offchainSet.get(i).getHash())) {
////	           		 ixFileProfileData.get(i).setHash("null");
//	           		 System.out.println("two hashes are same, not to trigger's corda flow");
//
//	           	 }
//	           	 else {
////	          		OffchainToOnchain calculatehash = new OffchainToOnchain(cordauser, cordapassword,cordanode0);
////	          		List<FileProfile> UpdcomResp = calculatehash.updateRecordOffchainOnchain(ixFileProfileData,remId);
//	           		 
//	           		 System.out.println("trigger corda flow");
//	           		 }
//	           	 }	
//
//			//Insert modified records into offchain
//			msg = updateDB.modifyAuditTrailRecord(comResp.getUpdateSet(),remId);
//			if(!msg.equals("success")){
//				throw new AuditTrailException(remId,"Error",msg);
//			}
//
//		}
//		JSONObject resData = new JSONObject();
//		resData.put("remId",remId);
//		resData.put("remStatus",remStatus);
//		resData.put("message",respMessage);
//		return resData;
//	}


//	//retrieve all fileprofiles with given remid
//	@RequestMapping(value = "/getAllFileProfileByRem_id", method = RequestMethod.POST, consumes = "application/json",produces = "application/json")
//	public List<FileProfile> getAllFileProfileData(@RequestBody Map<String,String> requestParams) throws Exception {
//		GetDB getDB = new GetDB();
//		String rem_id = requestParams.get("REM_ID");
//		List<FileProfile> offchainSet = getDB.fetchFileProfileRecord(rem_id);
//		return offchainSet;
//
//	}

	// To send all fileprofile data with given remid to IX
	@RequestMapping(value = "/sendAllFileProfileByRem_id", method = RequestMethod.POST, consumes = "application/json",produces = "application/json")
	public String sendAllFileProfileDataToIX(@RequestBody Map<String,String> requestParams) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type","application/json");
		HashMap<String,String> json = new HashMap<String,String>();
		String rem_id = requestParams.get("REM_ID");
		json.put("REM_ID", rem_id);
		HttpEntity<HashMap<String, String>> requestEntity = new HttpEntity<HashMap<String,String >>(json, headers);

		ResponseEntity<String> result = restTemplate.exchange("http://localhost:8082/getAllFileProfileByRem_id", HttpMethod.POST, requestEntity, String.class);
		return result.getBody();

	}

	// Find Audit record and store into off chain ledger and on chain ledger
	@RequestMapping(value = "/getallfileprofilesfrombc", method = RequestMethod.GET, consumes = "application/json",produces = "application/json")
	public List<FileProfile> getAllFileProfilesOnchain() throws Exception{
		ArrayList<FileProfile> fileprofiles = new ArrayList<FileProfile>();
		OffchainToOnchain fops = new OffchainToOnchain(cordauser, cordapassword, cordanode0);
//		String rem_Id = requestParams.get("REM_ID");
		fileprofiles = fops.getAllFileProfiles();
		return fileprofiles;
	}


	@PostMapping("/startEncryption")
	public JSONObject startEncryption(@RequestBody Map<String,String> requestParams) throws Exception{

		List<FileProfile> offchainList = new ArrayList<>();
		System.out.println("Remedidation ID"+requestParams.get("REM_ID"));
		System.out.println("Remedidation TYPE"+requestParams.get("REM_TYPE"));
		System.out.println("Analysis ID"+requestParams.get("ANALYSIS_ID"));

		AuditTrail auditTrail = new AuditTrail();

		RestTemplate restTemplate = new RestTemplate();

		//auditTrail.getToken(requestParams);

		String remId = requestParams.get("REM_ID");

		GetDB getDB = new GetDB();
		InsertDB insertDB = new InsertDB();

		UpdateDB updateDB = new UpdateDB();

		List<FileProfile> ixFileProfileData = auditTrail.getFileProfileData(requestParams);
		List<RespModel> resp = new ArrayList<>();


		List<FileProfile> offchainSet = getDB.fetchEncryptionRecord(remId);

		System.out.println("Records no of database records "+offchainSet.size());
		LinkedHashMap<String,String> inputDataForEncAgent = new LinkedHashMap<>();
		for(int i=0;i<ixFileProfileData.size();i++)
		{
			inputDataForEncAgent.put( ixFileProfileData.get(i).getFilename(), ixFileProfileData.get(i).getFilepath());
		}
		if(offchainSet.size() == 0){
			System.out.println("No DataFound with RemId as "+remId);

			//Insert records into off chain db
			String message = insertDB.createEncryptionRecord(ixFileProfileData,remId);
			JSONObject respFromEncAgent = restTemplate.postForObject(agentUrl+encryptAPI, inputDataForEncAgent, JSONObject.class);

			List<RespModel> enResp = objectMapper.convertValue(
					respFromEncAgent.get("Resp"),
					new TypeReference<List<RespModel>>(){}
			);
			System.out.println("enResp: "+enResp);

			for(int i=0;i<enResp.size();i++){
				String status = enResp.get(i).getStatus();
				if(status.equalsIgnoreCase("success")) {
					String msg = updateDB.modifyEncryptionStatus(enResp.get(i).getFilePath().replace(".asc",""));
				}
			}
		}
		else {
			ComparisonResults comResp = compRemSets.compareRemediationSetForEnc(ixFileProfileData, offchainSet);

			String msg = "";
			System.out.println("Message "+msg);
			msg = insertDB.createEncryptionRecord(comResp.getAdditionSet(),remId);
			JSONObject respFromEncAgent = restTemplate.postForObject(agentUrl+encryptAPI, inputDataForEncAgent, JSONObject.class);

			List<RespModel> enResp = objectMapper.convertValue(
					respFromEncAgent.get("Resp"),
					new TypeReference<List<RespModel>>(){}
			);
			System.out.println("enResp: "+enResp);
			System.out.println("enResp Size: "+enResp.size());
			for(int i=0;i<enResp.size();i++){
				String status = enResp.get(i).getStatus();
				if(status.equalsIgnoreCase("success")) {
					String message = updateDB.modifyEncryptionStatus(enResp.get(i).getFilePath().replace(".asc",""));
				}
			}
			if(!msg.equals("success")){
				throw new AuditTrailException(remId,"Error",msg);
			}

//			//Insert modified records into offchain
//			msg = updateDB.modifyEncryptionRecord(comResp.getUpdateSet(),remId);
//			if(!msg.equals("success")){
//				throw new AuditTrailException(remId,"Error",msg);
//			}

		}

		JSONObject resData = new JSONObject();
		resData.put("remId",remId);
		return resData;
	}
	
	//Irfan's stuff
	// Retrieve file name, path, id and status of fileprofile data
		@RequestMapping(value = "/getfilenamepathstatusId", method = RequestMethod.GET, consumes = "application/json",produces = "application/json")
		public List<Map<String, String>> FilepathnameStatus(@RequestBody Map<String,String> requestParams) throws Exception{
			GetDB getDB = new GetDB();
			String rem_id = requestParams.get("REM_ID");
			List<FileProfile> offchainSet = getDB.fetchFileProfileRecord(rem_id);
			
			List<Map<String,String>> arrmap = new ArrayList<Map<String,String>>();
			for(int i = 0; i<offchainSet.size(); i++) {
			Map<String, String> map = new HashMap<String,String>();
			map.put("filename", offchainSet.get(i).getFilename());
			map.put("filepath", offchainSet.get(i).getFilepath());
			map.put("status", offchainSet.get(i).getStatus());
			map.put("id",  "\""+offchainSet.get(i).getId()+"\"");
			arrmap.add(map);
			}
			return arrmap;
			}
		
		//retrieve filename,path and encryption status with given remID from Offchain
		@RequestMapping(value = "/encryptionstatus_Name_Path", method = RequestMethod.GET, consumes = "application/json",produces = "application/json")
		public List<Map<String, String>> Encrypton_Path_Status(@RequestBody Map<String,String> requestParams) throws Exception{
			GetDB getDB = new GetDB();
			String rem_id = requestParams.get("REM_ID");
			List<FileProfile> offchainSet = getDB.fetchEncryptionRecord(rem_id);
			
			List<Map<String,String>> arrmap = new ArrayList<Map<String,String>>();
			for(int i = 0; i<offchainSet.size(); i++) {
			Map<String, String> map = new HashMap<String,String>();
			map.put("filename", offchainSet.get(i).getFilename());
			map.put("filepath", offchainSet.get(i).getFilepath());
			map.put("status", offchainSet.get(i).getStatus());
			arrmap.add(map);
			}
			return arrmap;
			}	
		//retrieve filename,path and encryption status with given remID from Offchain
		@RequestMapping(value = "/Encrypton_Status", method = RequestMethod.GET, consumes = "application/json",produces = "application/json")
		public List<Map<String, String>> Encrypton_Status(@RequestBody Map<String,String> requestParams) throws Exception{
			GetDB getDB = new GetDB();
			String rem_id = requestParams.get("REM_ID");
			List<FileProfile> offchainSet = getDB.fetchEncryptionRecord(rem_id);
			
			List<Map<String,String>> arrmap = new ArrayList<Map<String,String>>();
			for(int i = 0; i<offchainSet.size(); i++) {
			Map<String, String> map = new HashMap<String,String>();
			map.put("status", offchainSet.get(i).getStatus());
			arrmap.add(map);
			}
			return arrmap;
			}
		
		/*************************************************Single files retrieval starts here ********************************************************/
//		//retrieve only encryption status with given remID from Offchain
//		@RequestMapping(value = "/encryptionstatus_Single", method = RequestMethod.GET, consumes = "application/json",produces = "application/json")
//		public Map<String, String> EncryptonStatus_Single(@RequestBody Map<String,String> requestParams) throws Exception{
//			GetDB getDB = new GetDB();
//			String filepath = requestParams.get("filepath");
//			FileProfile offchainSet = getDB.encryption_status(filepath);
//			
//			Map<String, String> map = new HashMap<String,String>();
//			map.put("status", offchainSet.getStatus());
//			map.put("groupname", offchainSet.getGroupname());
//			return map;
//			}
		
		// API to insert new record into off chain and on chain		
		@RequestMapping(value = "/audittrail/startaudit", method = RequestMethod.POST, consumes = "application/json",produces = "application/json")
		public JSONObject AuditTrailFileProfileRecord(@RequestBody Map<String,String> requestParams) throws Exception{

			AuditTrail auditTrail = new AuditTrail();

			GetDB getDB = new GetDB();
			InsertDB insertDB = new InsertDB();
			UpdateDB updateDB = new UpdateDB();
			
			String remStatus = "ACTIVE";
			String respMessage = "Audit Trail Enabled Successfully";
			
//			auditTrail.getToken(requestParams);

			String remId = requestParams.get("REM_ID");

			List<FileProfile> ixFileProfileData = auditTrail.getFileProfileData(requestParams);

			List<FileProfile> offchainSet = getDB.fetchFileProfileRecord(remId);

			System.out.println("Records no of database records "+offchainSet.size());

			if(offchainSet.size() == 0){
				System.out.println("No DataFound with RemId as "+remId);

				//Insert records into off chain db
				String message = insertDB.createAuditTrailRecord(ixFileProfileData,remId);

//				Insert records into blockchain db
				OffchainToOnchain calculatehash = new OffchainToOnchain(cordauser, cordapassword,cordanode0);
				List<FileProfile> comResp = calculatehash.InsertRecordintobc(ixFileProfileData,remId);
				if(!message.equals("success")){
					remStatus = "Failed";
					respMessage = message;
					throw new AuditTrailException(remId,remStatus,respMessage);
				}
			}
			else {
				ComparisonResults comResp = compRemSets.compareRemediationSet(ixFileProfileData, offchainSet);
				
//				String msg = insertDB.createAuditTrailRecord(comResp.getAdditionSet(),remId);
				
				//check fileprofile set have modified attributes?  && insert modified records into Blockchain
				for (int i = 0; i < ixFileProfileData.size(); i++) {
		            HashCode hash = new HashCode();
		            String hashValue = hash.getHashOfFileProfileData(ixFileProfileData.get(i));
		            ixFileProfileData.get(i).setHash(hashValue);
		            ixFileProfileData.get(i).setRemId(remId);             
		           	 if(ixFileProfileData.get(i).getHash().equalsIgnoreCase(offchainSet.get(i).getHash())) {
//		           		 ixFileProfileData.get(i).setHash("null");
		           		 System.out.println("two hashes are same, not to trigger corda flow");

		           	 }
		           	 else {
		          		OffchainToOnchain calculatehash = new OffchainToOnchain(cordauser, cordapassword,cordanode0);
		          		List<FileProfile> UpdcomResp = calculatehash.updateRecordOffchainOnchain(ixFileProfileData,remId);
		           		System.out.println("trigger corda flow");
		           		 }
		           	 }	

				//Insert modified records into offchain
				String Updmsg = updateDB.modifyAuditTrailRecord(comResp.getUpdateSet(),remId);
				System.out.println("update offchain db done..!");
			}
			JSONObject resData = new JSONObject();
			resData.put("remId",remId);
			resData.put("remStatus",remStatus);
			resData.put("message",respMessage);
			return resData;
		}
		
		// API to delete fileprofile from off chain and on chain		
		@RequestMapping(value = "/Delete_fileProfile", method = RequestMethod.POST, consumes = "application/json",produces = "application/json")
		public String DeleteFileProfile(@RequestBody Map<String,String> requestParams) throws Exception{

			AuditTrail auditTrail = new AuditTrail();

			GetDB getDB = new GetDB();
			DeleteDB deleteDB = new DeleteDB();

			auditTrail.getToken(requestParams);

			String remId = requestParams.get("REM_ID");

			List<FileProfile> ixFileProfileData = auditTrail.getFileProfileData(requestParams);

			List<FileProfile> offchainSet = getDB.fetchFileProfileRecord(remId);

			System.out.println("Records no of database records "+offchainSet.size());

				System.out.println("No DataFound with RemId as "+remId);

				// Delete Records from off chain db
				ComparisonResults comResp = compRemSets.compareRemediationSet(ixFileProfileData, offchainSet);
				String Delmessage = deleteDB.removeAuditTrailRecord(comResp.getDeletionSet(),remId);

				return remId+" has been deleted from Offchain";
			}
		
		// Retrieve file name, path, id and status of fileprofile data
		@RequestMapping(value = "/findAuditTrail", method = RequestMethod.GET, consumes = "application/json",produces = "application/json")
		public List<FileProfile> FindAuditTrail(@RequestBody Map<String,String> requestParams) throws Exception{
			GetDB getDB = new GetDB();
			String rem_id = requestParams.get("REM_ID");
			List<FileProfile> offchainSet = getDB.fetchFileProfileRecord(rem_id);
			return offchainSet;
			}
	

}




