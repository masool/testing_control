package bccontrol;

import java.sql.Connection;
import java.util.*;

public class UpdateDB {
    Connection connection = null;

    public String modifyAuditTrailRecord(LinkedHashMap<UUID, LinkedHashMap<String,String>> updateSet, String remId) throws AuditTrailException {
            String message =  "success";
            try {
                Calendar calendar = Calendar.getInstance();
                java.sql.Timestamp timestampObject = new java.sql.Timestamp(calendar.getTime().getTime());

                for (Map.Entry<UUID, LinkedHashMap<String, String>> node : updateSet.entrySet()) {

                    LinkedHashMap<String, String> data = node.getValue();

                    Set<String> keys = data.keySet();
                    String[] keysArray = new String[keys.size()];
                    keys.toArray(keysArray);

                    OffchainDB db = new OffchainDB();
                    for (int i = 0; i < keysArray.length; i++) {
                        String fileProfileQuery = "UPDATE fileprofile SET " + keysArray[i].toLowerCase() + " = '" + data.get(keysArray[i]) + "' where id = '" + node.getKey() + "' and status = '0';";
                        System.out.println("Operation done successfully+" + fileProfileQuery);
                        db.queryDB(connection, fileProfileQuery);
                    }

                    String fileProfileHashQuery = "INSERT INTO public.fileprofilehashlog(uuid, hashvalue, timestamp)"+
                                                        "VALUES ('" + node.getKey() +"', '" + data.get("hash") + "','"+timestampObject+"');";
                    System.out.println("query fileProfileHashQuery '" + fileProfileHashQuery);
                    db.queryDB(connection, fileProfileHashQuery);

                }
            }
            catch(Exception e){
                message = e.getMessage();
                throw new AuditTrailException(remId,"Error",e.getMessage());
            }
        return  message;
    }

    public String modifyEncryptionRecord(LinkedHashMap<UUID, LinkedHashMap<String,String>> updateSet, String remId) throws AuditTrailException {
        String message =  "success";
        try {
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestampObject = new java.sql.Timestamp(calendar.getTime().getTime());

            for (Map.Entry<UUID, LinkedHashMap<String, String>> node : updateSet.entrySet()) {

                LinkedHashMap<String, String> data = node.getValue();

                Set<String> keys = data.keySet();
                String[] keysArray = new String[keys.size()];
                keys.toArray(keysArray);

                OffchainDB db = new OffchainDB();
                for (int i = 0; i < keysArray.length; i++) {

                    String fileUpdateQuery = "UPDATE file SET " + keysArray[i].toLowerCase() + " = '" + data.get(keysArray[i]) + "' where filepath = '" + node.getKey() + "' and status = 'N';";
                    System.out.println("Operation done successfully+" + fileUpdateQuery);
                    db.queryDB(connection, fileUpdateQuery);
                }
            }
        }
        catch(Exception e){
            message = e.getMessage();
            throw new AuditTrailException(remId,"Error",e.getMessage());
        }
        return  message;
    }

    public String modifyEncryptionStatus( String filePath) throws AuditTrailException {
        String message =  "success";
        try {
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestampObject = new java.sql.Timestamp(calendar.getTime().getTime());

                OffchainDB db = new OffchainDB();

                String fileENCQuery = "UPDATE file SET status = 'Y' , encyptiontimestamp='"+timestampObject+"' where filePath = '" + filePath + "' and status = 'N';";
                System.out.println("Operation done successfully+" + fileENCQuery);
                db.queryDB(connection, fileENCQuery);

        }
        catch(Exception e){
            message = e.getMessage();
            throw new AuditTrailException(filePath,"Error",e.getMessage());
        }
        return  message;
    }

}