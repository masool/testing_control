This is Project demonstrates persisting Fileprofile data sets so called Remediation sets into OFFchain ledger(postgresql) & ONChain ledger(Corda Blockchain).

Prerequisites:
1)gradlew
2)IntelliJ or Eclipse
3)Git4
4)Java 8 or above / Kotlin

Steps to Run:

First Method

1) Clone this project from BCControl Irfan.masool feature branch.
2) Corda Project has to be run in local machine/UAT server
   If Corda running in UAT Server then just place UAT Server's IP addess in application properties in BCControl project, else run Corda project by get clone from Hanuman's feature bbranch and replace UAT Server's IP address with localhost as corda running in local machine.
3) Run BCCOntrol Spring boot application
4) Login into postgresql Database to monitor whether group of fileprofile data records has been stored.

Now we ready to go!!

Below are the details of postman collection: 

1)Below API is to get corda network all peers info as how many peers defined in connected corda network

URL: http://localhost:8082/peers
Method: GET
Content-Type: application/json

2) Below API is to get peer info that which node connected to present spring boot server.

URL: http://localhost:8082/me
Method: GET
Content-Type: application/json

3) Below API is to persist set of fileprofile data into OFFchainDB(postgres) & ONChain Ldger(Corda) and it returns persisted set of fileprofile data into OFFChain and Onchain as response in SON format.

URL: http://localhost:8082/InsertRemediationSet_Offchain_ONchain
Method; POST
input Payload as RequestBody : 
{
	"REM_ID":"10002",
	"REM_TYPE":"Action",
	"ANALYSIS_ID":"a00gk9a"
}
Content-Type: application/json

4) Below API is to retrieve all fileprofiles data with given Remediation Id from offchainDB and returns the same as response in JSON Format.

URL: http://localhost:8082/getAllFileProfileByRem_id
Method: POST
Input Payload as RequestBody :
{
	"REM_ID":"10002"
}
Content-Type: application/json

5)Below API is to retrieve all fileprofiles data with given Remediation Id from offchainDB and returns the same as response in JSON Format.

URL: http://localhost:8082/sendAllFileProfileByRem_id
Method: POST
Input Payload as RequestBody :
{
	"REM_ID":"10002"
}
Content-Type: application/json

6)Below API is to retrieve all fileprofiles data with given Remediation Id from offchainDB and returns the same as response in JSON Format.

URL: http://localhost:8082/CalculateHashes
Method: POST
Input Payload as RequestBody :
{
	"REM_ID":"10002",
	"REM_TYPE":"Action",
	"ANALYSIS_ID":"a00gk9a"
}
Content-Type: application/json

second Method:

Just export postman collection file(controlX-cordaAPI.postman_collection.json) which can be find in BCCOntrol project structure and start enjoy running this project.

Happy Learning !!!:)





















