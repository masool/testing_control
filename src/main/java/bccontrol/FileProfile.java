package bccontrol;

import java.util.UUID;

public class FileProfile {

	private String filename;
	private String nashost;
	private String filepath;
	private String symlink;
	private String uid;
	private String username;
	private String gid;
	private String groupname;
	private String unixuserperm;
	private String unixgroupperm;
	private String unixotherperm;
	private String winuserperms;
	private String wingroupperms;
	private String winotherperms;
	private String ctime;
	private String atime;
	private String mtime;
	private String btime;
	private String size;
	private String inode;
	private String devname;
	private String devtype;
	private String devmajor;
	private String devminor;
	private String fstype;
	private String piientities;
	private String hostName;
	private String auditDescr;
	private String flag;
	private String remId;
	private UUID id;
	private String status;
	private String hash;
	private String identifier;
	private String time;
	
	//additional add for corda
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String gettime() {
		return time;
	}

	public void settime(String time) {
		this.time = time;
	}
	//

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getRemId() {
		return remId;
	}

	public void setRemId(String remId) {
		this.remId = remId;
	}

	public String getFlag() { return flag; }
	public void setFlag(String flag) { this.flag = flag; }
	public String getAuditDescr() { return auditDescr; }
	public void setAuditDescr(String auditDescr) { this.auditDescr = auditDescr; }
	public String getHostName() { return hostName; }
	public void setHostName(String hostName) { this.hostName = hostName; }
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getNashost() {
		return nashost;
	}
	public void setNashost(String nashost) {
		this.nashost = nashost;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getSymlink() {
		return symlink;
	}
	public void setSymlink(String symlink) {
		this.symlink = symlink;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getUnixuserperm() {
		return unixuserperm;
	}
	public void setUnixuserperm(String unixuserperm) {
		this.unixuserperm = unixuserperm;
	}
	public String getUnixgroupperm() {
		return unixgroupperm;
	}
	public void setUnixgroupperm(String unixgroupperm) {
		this.unixgroupperm = unixgroupperm;
	}
	public String getUnixotherperm() {
		return unixotherperm;
	}
	public void setUnixotherperm(String unixotherperm) {
		this.unixotherperm = unixotherperm;
	}
	public String getWinuserperms() {
		return winuserperms;
	}
	public void setWinuserperms(String winuserperms) {
		this.winuserperms = winuserperms;
	}
	public String getWingroupperms() {
		return wingroupperms;
	}
	public void setWingroupperms(String wingroupperms) {
		this.wingroupperms = wingroupperms;
	}
	public String getWinotherperms() {
		return winotherperms;
	}
	public void setWinotherperms(String winotherperms) {
		this.winotherperms = winotherperms;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getAtime() {
		return atime;
	}
	public void setAtime(String atime) {
		this.atime = atime;
	}
	public String getMtime() {
		return mtime;
	}
	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getInode() {
		return inode;
	}
	public void setInode(String inode) {
		this.inode = inode;
	}
	public String getDevname() {
		return devname;
	}
	public void setDevname(String devname) {
		this.devname = devname;
	}
	public String getDevtype() {
		return devtype;
	}
	public void setDevtype(String devtype) {
		this.devtype = devtype;
	}
	public String getDevmajor() {
		return devmajor;
	}
	public void setDevmajor(String devmajor) {
		this.devmajor = devmajor;
	}
	public String getDevminor() {
		return devminor;
	}
	public void setDevminor(String devminor) {
		this.devminor = devminor;
	}
	public String getFstype() {
		return fstype;
	}
	public void setFstype(String fstype) {
		this.fstype = fstype;
	}
	public String getPiientities() {
		return piientities;
	}
	public void setPiientities(String piientities) {
		this.piientities = piientities;
	}
}
