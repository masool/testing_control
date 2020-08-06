package bccontrol;

import java.sql.Connection;
import java.util.List;

public class DeleteDB {
    Connection connection = null;
    public String removeAuditTrailRecord(List<FileProfile> rem_set, String remId) throws AuditTrailException {
        String message = "success";
        try {
            for (FileProfile node : rem_set) {
                OffchainDB db = new OffchainDB();

                String fileProfileQuery = "UPDATE fileprofile set status = '1' , filepath = '' where id = '" + node.getId() + "' ";
                db.queryDB(connection, fileProfileQuery);
                System.out.println("Operation done successfully with delete query " + fileProfileQuery);

                String fileProfileRemQuery = "UPDATE remid set status = '1'  where rem_id = '"+remId+"' ";
                db.queryDB(connection, fileProfileRemQuery);
                System.out.println("Operation done successfully with delete query fileProfileRemQuery "+fileProfileRemQuery);
            }
        }
        catch(Exception e){
            message = e.getMessage();
            throw new AuditTrailException(remId,"Error",e.getMessage());
        }
        return message;
    }
}
