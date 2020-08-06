package bccontrol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetDB {

    Connection connection = null;

    public List<FileProfile> fetchFileProfileRecord(String rem_id) throws  SQLException, AuditTrailException {
        ResultSet rs = null;
        OffchainDB db = new OffchainDB();

        String fileProfileQuery = "SELECT * FROM fileprofile join fileprofile_remid on fileprofile.id = fileprofile_remid.fileprofile_id where rem_id = '"+rem_id+"' and status = '0' ; ";
        System.out.println("select query : "+fileProfileQuery);
        rs =  db.queryDB(connection, fileProfileQuery);

        List<FileProfile> resp = new ArrayList<>();
        while(rs.next()) {
            System.out.println("select query Result set: "+rs);

            FileProfile fp = new FileProfile();
            fp.setId(UUID.fromString(rs.getString("id")));
            fp.setFilename(rs.getString("filename"));
            fp.setNashost(rs.getString("nashost"));
            fp.setFilepath(rs.getString("filepath"));
            fp.setSymlink(rs.getString("symlink"));
            fp.setUid(rs.getString("uid"));
            fp.setUsername(rs.getString("username"));
            fp.setGid(rs.getString("gid"));
            fp.setGroupname(rs.getString("groupname"));
            fp.setUnixuserperm(rs.getString("unixuserperm"));
            fp.setUnixgroupperm(rs.getString("unixgroupperm"));
            fp.setUnixotherperm(rs.getString("unixotherperm"));
            fp.setWinuserperms(rs.getString("winuserperms"));
            fp.setWingroupperms(rs.getString("wingroupperms"));
            fp.setWinotherperms(rs.getString("winotherperms"));
            fp.setCtime(rs.getString("ctime"));
            fp.setAtime(rs.getString("atime"));
            fp.setMtime(rs.getString("mtime"));
            fp.setBtime(rs.getString("btime"));
            fp.setSize(rs.getString("size"));
            fp.setInode(rs.getString("inode"));
            fp.setDevname(rs.getString("devname"));
            fp.setDevtype(rs.getString("devtype"));
            fp.setDevmajor(rs.getString("devmajor"));
            fp.setDevminor(rs.getString("devminor"));
            fp.setFstype(rs.getString("fstype"));
            fp.setPiientities(rs.getString("piientities"));
            fp.setHostName(rs.getString("hostname"));
            fp.setAuditDescr(rs.getString("auditdescr"));
            fp.setStatus(rs.getString("status"));
            fp.setHash(rs.getString("hash"));
            resp.add(fp);
        }
        rs.close();
        return resp;
    }

    public ArrayList fetchRemId(String rem_id) throws  SQLException, AuditTrailException {
        ResultSet rs = null;
        OffchainDB db = new OffchainDB();

        String REMQuery = "SELECT rem_id FROM remid where rem_id = '"+rem_id+"' and status = '0' ";
        System.out.println("Query : "+REMQuery);
        rs =  db.queryDB(connection, REMQuery);

        ArrayList remIdArray = new ArrayList<>();
        while(rs.next()) {
            remIdArray.add(rs.getString("rem_id"));
        }
        System.out.println("remIdArray"+remIdArray);
        rs.close();
        return remIdArray;
    }

    public ArrayList fetchFilePath(String filepath) throws  SQLException, AuditTrailException {
        ResultSet rs = null;
        OffchainDB db = new OffchainDB();

        String fileProfileQuery = "SELECT filepath FROM fileprofile where  filepath = '"+filepath+"' and status = '0' ";
        rs =  db.queryDB(connection, fileProfileQuery);

        ArrayList filepathArray = new ArrayList<>();
        while(rs.next()) {
            filepathArray.add(rs.getString("filepath"));
        }
        System.out.println("filepathArray"+filepathArray);
        rs.close();
        return filepathArray;
    }

    public List<FileProfile> fetchEncryptionRecord(String rem_id) throws  SQLException, AuditTrailException {
        ResultSet rs = null;
        OffchainDB db = new OffchainDB();

        String fileQuery = "SELECT * FROM file join file_remid on file.filepath = file_remid.file_id where rem_id = '"+rem_id+"' and status = 'N' ; ";
        System.out.println("select query : "+fileQuery);
        rs =  db.queryDB(connection, fileQuery);

        List<FileProfile> resp = new ArrayList<>();
        while(rs.next()) {
            System.out.println("select query Result set: "+rs);

            FileProfile fp = new FileProfile();
            fp.setFilename(rs.getString("filename"));
            fp.setFilepath(rs.getString("filepath"));
            fp.setStatus(rs.getString("status"));
            resp.add(fp);
        }
        rs.close();
        return resp;
    }

}
