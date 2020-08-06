package bccontrol;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.*;

public class AuditTrail {

    static ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode getToken(Map<String, String> requestParams) {
        JsonNode resData = null;
        Connection connection =null;
        try {
            HashMap<String, String> request = new HashMap<String, String>();
            request.put("username", "pvscan");
            request.put("password", "User@123");
            resData = restUtil(request);
            System.out.println("resssdata"+resData);


        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
        return resData;
    }

    public ArrayList<FileProfile> getFileProfileData(Map<String, String> requestParams) {
        System.out.println("Remediation ID"+requestParams.get("REM_ID"));

        ArrayList<FileProfile> obj = new ArrayList<FileProfile>();

        FileProfile fileProfile2 = new FileProfile();
        fileProfile2.setFilepath("C:/Users/Administrator/Desktop/gb1.txt");
        fileProfile2.setAtime("atime11");
        fileProfile2.setBtime("btime12");
        fileProfile2.setCtime("ctime13");
        fileProfile2.setGid("gid");
        fileProfile2.setGroupname("groupname");
        fileProfile2.setMtime("mtime15");
        fileProfile2.setSize("size");
        fileProfile2.setUid("1");
        fileProfile2.setUnixgroupperm("r--");
        fileProfile2.setUnixotherperm("r+-");
        fileProfile2.setUsername("infintus");
        fileProfile2.setDevmajor("devmajor");
        fileProfile2.setDevminor("devminor");
        fileProfile2.setDevname("devname");
        fileProfile2.setDevtype("devtype");
        fileProfile2.setFilename("gb1.txt");
        fileProfile2.setFstype("fstype");
        fileProfile2.setInode("inode");
        fileProfile2.setHostName("hostname");
        fileProfile2.setNashost("nashost");
        fileProfile2.setSymlink("symlink");
        fileProfile2.setUnixuserperm("r--");
        fileProfile2.setWinuserperms("r++");
        fileProfile2.setWingroupperms("r++");
        fileProfile2.setWinotherperms("r--");
        fileProfile2.setPiientities("piientities");
        fileProfile2.settime("2020-07-27 23:30:26.632");
//        obj.add(fileProfile);
        obj.add(fileProfile2);
        return obj;
    }

    public ObjectNode restUtil(HashMap<String, String> request) {
        ObjectNode node = objectMapper.createObjectNode();
        try {
            // certification verification ignore step
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
            }
            };
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            String url = "https://ianalytics.infintus.com/masterFlow";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Operation-Type", "userAuthentication");
            HttpEntity<HashMap<String, String>> entity = new HttpEntity<HashMap<String, String>>(request, headers);
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            node.put("token",response.getHeaders().get("token").toString());
            node = (ObjectNode) objectMapper.readTree(response.getBody().toString());

            System.out.println("res33"+node);
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
        return node;
    }
}

