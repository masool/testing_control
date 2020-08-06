package bccontrol;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class HashCode {

    public String getHashOfFileProfileData(FileProfile fileProfile) throws NoSuchAlgorithmException {

        StringBuilder fileProfileSB = new StringBuilder();

        fileProfileSB.append(fileProfile.getFilepath());
        fileProfileSB.append(fileProfile.getAtime());
        fileProfileSB.append(fileProfile.getBtime());
        fileProfileSB.append(fileProfile.getCtime());
        fileProfileSB.append(fileProfile.getGid());
        fileProfileSB.append(fileProfile.getGroupname());
        fileProfileSB.append(fileProfile.getMtime());
        fileProfileSB.append(fileProfile.getSize());
        fileProfileSB.append(fileProfile.getUid());
        fileProfileSB.append(fileProfile.getUnixgroupperm());
        fileProfileSB.append(fileProfile.getUnixotherperm());
        fileProfileSB.append(fileProfile.getUsername());
        fileProfileSB.append(fileProfile.getDevmajor());
        fileProfileSB.append(fileProfile.getDevminor());
        fileProfileSB.append(fileProfile.getDevname());
        fileProfileSB.append(fileProfile.getDevtype());
        fileProfileSB.append(fileProfile.getFilename());
        fileProfileSB.append(fileProfile.getFstype());
        fileProfileSB.append(fileProfile.getInode());
        fileProfileSB.append(fileProfile.getHostName());
        fileProfileSB.append(fileProfile.getNashost());
        fileProfileSB.append(fileProfile.getSymlink());
        fileProfileSB.append(fileProfile.getUnixuserperm());
        fileProfileSB.append(fileProfile.getWinuserperms());
        fileProfileSB.append(fileProfile.getWingroupperms());
        fileProfileSB.append(fileProfile.getWinotherperms());
        fileProfileSB.append(fileProfile.getPiientities());

        System.out.println("String Builder : "+fileProfileSB);


        return toHexString(getSHA(fileProfileSB.toString()));
    }

    public byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public  String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

}
