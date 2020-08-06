package bccontrol;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class InsertDB {

    Connection connection = null;

    public String createAuditTrailRecord(List<FileProfile> rem_set, String rem_id) throws AuditTrailException {
        System.out.println("Create Record function ");
        String message = "success";
        try {
            OffchainDB db = new OffchainDB();
            GetDB getDB = new GetDB();

            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestampObject = new java.sql.Timestamp(calendar.getTime().getTime());

            ArrayList remIdArray = getDB.fetchRemId(rem_id);
            if (remIdArray.size() == 0) {
                String fileProfileRemQuery = "INSERT INTO public.remid(rem_id, status, timestamp)" +
                        " VALUES ('" + rem_id + "','0','" + timestampObject + "');";
                System.out.println("query fileProfileRemQuery 1" + fileProfileRemQuery);
                db.queryDB(connection, fileProfileRemQuery);
            }

            for (FileProfile node : rem_set) {
                HashCode hash = new HashCode();
                node.setHash(hash.getHashOfFileProfileData(node));
                node.setRemId(rem_id);
                node.setId(UUID.randomUUID());

                List<FileProfile> offchainSet = getDB.fetchFilePath(node.getFilepath());

                if (offchainSet.size() == 0) {
                    String fileProfileQuery = "INSERT INTO public.fileprofile(id, filename, nashost, filepath, symlink, uid, username, gid, groupname, unixuserperm, unixgroupperm, unixotherperm, winuserperms, wingroupperms, winotherperms, ctime, atime, mtime, btime, size, inode, devname, devtype, devmajor, devminor, fstype, piientities, hostname, auditdescr, status, hash, timestamp)" +
                            "VALUES ('" + node.getId() + "','" + node.getFilename() + "','" + node.getNashost() + "','" + node.getFilepath() + "','" + node.getSymlink() + "','" + node.getUid() + "','" + node.getUsername() + "','" + node.getGid() + "','" + node.getGroupname() + "','" + node.getUnixuserperm() + "','" + node.getUnixgroupperm() + "','" + node.getUnixotherperm() + "','" + node.getWinuserperms() + "','" + node.getWingroupperms() + "','" + node.getWinotherperms() + "','" + node.getCtime() + "','" + node.getAtime() + "','" + node.getMtime() + "','" + node.getBtime() + "','" + node.getSize() + "','" + node.getInode() + "','" + node.getDevname() + "','" + node.getDevtype() + "','" + node.getDevmajor() + "','" + node.getDevminor() + "','" + node.getFstype() + "','" + node.getPiientities() + "','" + node.getHostName() + "','" + node.getAuditDescr() + "','0','" + node.getHash() + "','" + timestampObject + "');";
                    System.out.println("query fileProfileQuery " + fileProfileQuery);
                    db.queryDB(connection, fileProfileQuery);

                    String fileProfileHashQuery = "INSERT INTO public.fileprofilehashlog(uuid, hashvalue, timestamp)" +
                            "VALUES ('" + node.getId() + "','" + node.getHash() + "','" + timestampObject + "');";
                    System.out.println("query fileProfileHashQuery '" + fileProfileHashQuery);
                    db.queryDB(connection, fileProfileHashQuery);

                    String fileProfileRelationQuery = "INSERT INTO public.fileprofile_remid(fileprofile_id, rem_id)" +
                            "VALUES ('" + node.getId() + "','" + rem_id + "');";
                    System.out.println("query fileProfileRelationQuery '" + fileProfileRelationQuery);
                    db.queryDB(connection, fileProfileRelationQuery);
                }
            }
        } catch (Exception e) {
            message = e.getMessage();
            System.out.println("Insert DB  Catch : Message " + e.getMessage());
            throw new AuditTrailException(rem_id, "Error", e.getMessage());
        }
        return message;
    }

    public String createEncryptionRecord(List<FileProfile> enc_set, String rem_id) throws AuditTrailException {
        System.out.println("Create Record function ");
        String message = "success";
        try {
            OffchainDB db = new OffchainDB();
            GetDB getDB = new GetDB();

            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestampObject = new java.sql.Timestamp(calendar.getTime().getTime());

            ArrayList remIdArray = getDB.fetchRemId(rem_id);
            if (remIdArray.size() == 0) {
                String fileProfileRemQuery = "INSERT INTO public.remid(rem_id, status, timestamp)" +
                        " VALUES ('" + rem_id + "','0','" + timestampObject + "');";
                System.out.println("query fileProfileRemQuery 1" + fileProfileRemQuery);
                db.queryDB(connection, fileProfileRemQuery);
            }
            for (FileProfile node : enc_set) {
                List<FileProfile> offchainSet = getDB.fetchFilePath(node.getFilepath());

                if (offchainSet.size() == 0) {
                    String ENCInsertRemQuery = "INSERT INTO public.file(filepath, filename, encyptiontimestamp, decyptiontimestamp, status)" +
                            "VALUES ('" + node.getFilepath() + "','" + node.getFilename() + "',null,null,'N');";
                    System.out.println("query ENCInsertRemQuery " + ENCInsertRemQuery);
                    db.queryDB(connection, ENCInsertRemQuery);

                    String ENCRealtionQuery = "INSERT INTO public.file_remid(file_id, rem_id)" +
                            "VALUES ('" + node.getFilepath() + "','" + rem_id + "');";
                    System.out.println("query ENCRealtionQuery " + ENCRealtionQuery);
                    db.queryDB(connection, ENCRealtionQuery);
                }
            }
        } catch (Exception e) {
            message = e.getMessage();
            System.out.println("Insert DB  Catch : Message " + e.getMessage());
            throw new AuditTrailException(rem_id, "Error", e.getMessage());
        }
        return message;
    }
}


