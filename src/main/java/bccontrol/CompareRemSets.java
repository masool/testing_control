package bccontrol;

import org.springframework.stereotype.Component;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CompareRemSets {

    private static String status = "NF";

    public ComparisonResults compareRemediationSet(List<FileProfile> ixList,List<FileProfile> offchainList) throws NoSuchAlgorithmException {


        ComparisonResults compRes = new ComparisonResults();

        LinkedHashMap<UUID, LinkedHashMap<String,String>> updateSet = new LinkedHashMap<>();

        List<FileProfile> offChainData = new ArrayList<>();

        List<FileProfile> deletionSet = new ArrayList<>();

        List<FileProfile> additionSet = new ArrayList<>();

        HashCode hashCode = new HashCode();

        int ixRemLen = ixList.size();
        int offChainLen = offchainList.size();

        System.out.println("No of IX Records : "+ixRemLen);
        System.out.println("No of offchain records : "+offChainLen);

        if(ixRemLen>offChainLen || offChainLen>ixRemLen || ixRemLen==offChainLen) {

            if (ixRemLen == 0) {
                System.out.println("No Records Found from IX");
                deletionSet.addAll(offchainList);
            } else if (offChainLen == 0) {
                System.out.println("No Records Found from Offchain Database");
                additionSet.addAll(ixList);
            } else {
                System.out.println("both sources have some records");
                for (int i = 0; i < ixRemLen; ) {
                    for (int j = 0; j < offChainLen && i < ixRemLen; ) {
                        System.out.println("comparison done b/w " + ixList.get(i).getFilepath() + " and " + offchainList.get(j).getFilepath());
                        if (ixList.get(i).getFilepath().equals(offchainList.get(j).getFilepath())) {

                            System.out.println("File path is same  " + ixList.get(i).getFilepath());

                            String  ixHash = hashCode.getHashOfFileProfileData(ixList.get(i));
                            String offChainHash  = hashCode.getHashOfFileProfileData(offchainList.get(j));

                            System.out.println("IX Hash --> "+ixHash);
                            System.out.println("offChain Hash --> "+offChainHash);

                            if(ixHash.equals(offChainHash)){
                                System.out.println("No Change found in attributes");
                            }
                            else{
                                FileProfile ixFileProfile = ixList.get(i);
                                FileProfile offChainFileProfile = offchainList.get(j);

                                LinkedHashMap<String,String> updateHM = new LinkedHashMap<>();

                                if (!Objects.equals(ixFileProfile.getAtime(),offChainFileProfile.getAtime())){
                                    updateHM.put("aTime",ixFileProfile.getAtime());
                                }
                                // compare btime change
                                if (!Objects.equals(ixFileProfile.getBtime(),offChainFileProfile.getBtime())){
                                    updateHM.put("bTime",ixFileProfile.getBtime());
                                }

                                // compare ctime change
                                if (! Objects.equals(ixFileProfile.getCtime(),offChainFileProfile.getCtime())){
                                    updateHM.put("cTime",ixFileProfile.getCtime());
                                }
                                // compare devmajor change
                                if (! Objects.equals(ixFileProfile.getDevmajor(),offChainFileProfile.getDevmajor())){
                                    updateHM.put("devMajor",ixFileProfile.getDevmajor());
                                }

                                // compare devminor change
                                if (! Objects.equals(ixFileProfile.getDevminor(),offChainFileProfile.getDevminor())){
                                    updateHM.put("devMinor",ixFileProfile.getDevminor());
                                }

                                // compare devname change
                                if (! Objects.equals(ixFileProfile.getDevname(),offChainFileProfile.getDevname())){
                                    updateHM.put("devName",ixFileProfile.getDevname());
                                }

                                // compare devtype change
                                if (! Objects.equals(ixFileProfile.getDevtype(),offChainFileProfile.getDevtype())){
                                    updateHM.put("devType",ixFileProfile.getDevtype());
                                }

                                // compare filename change
                                if (! Objects.equals(ixFileProfile.getFilename(),offChainFileProfile.getFilename())){
                                    updateHM.put("fileName",ixFileProfile.getFilename());
                                }
                                // compare fstype change
                                if (! Objects.equals(ixFileProfile.getFstype(),offChainFileProfile.getFstype())){
                                    updateHM.put("fsType",ixFileProfile.getFstype());
                                }

                                // compare gid change
                                if (! Objects.equals(ixFileProfile.getGid(),offChainFileProfile.getGid())){
                                    updateHM.put("gid",ixFileProfile.getGid());
                                }
                                // compare groupname change
                                if (! Objects.equals(ixFileProfile.getGroupname(),offChainFileProfile.getGroupname())){
                                    updateHM.put("groupName",ixFileProfile.getGroupname());
                                }

                                // compare hostname change
                                if (! Objects.equals(ixFileProfile.getHostName(),offChainFileProfile.getHostName())){
                                    updateHM.put("hostName",ixFileProfile.getHostName());
                                }

                                // compare inode change
                                if (! Objects.equals(ixFileProfile.getInode(),offChainFileProfile.getInode())){
                                    updateHM.put("iNode",ixFileProfile.getInode());
                                }

                                // compare mtime change
                                if (! Objects.equals(ixFileProfile.getMtime(),offChainFileProfile.getMtime())){
                                    updateHM.put("mTime",ixFileProfile.getMtime());
                                }

                                // compare uid change
                                if (! Objects.equals(ixFileProfile.getUid(),offChainFileProfile.getUid())){
                                    updateHM.put("uid",ixFileProfile.getUid());
                                }

                                // compare nas host change
                                if (! Objects.equals(ixFileProfile.getNashost(),offChainFileProfile.getNashost())){
                                    updateHM.put("nasHost",ixFileProfile.getNashost());
                                }

                                // compare PII Entities change
                                if (! Objects.equals(ixFileProfile.getPiientities(),offChainFileProfile.getPiientities())){
                                    updateHM.put("piiEntities",ixFileProfile.getPiientities());
                                }

                                // compare winuserperms change
                                if (! Objects.equals(ixFileProfile.getWinuserperms(),offChainFileProfile.getWinuserperms())){
                                    updateHM.put("winUserPerm",ixFileProfile.getWinuserperms());
                                }

                                // compare wingroupperms change
                                if (! Objects.equals(ixFileProfile.getWingroupperms(),offChainFileProfile.getWingroupperms())){
                                    updateHM.put("winGroupPerm",ixFileProfile.getWingroupperms());
                                }

                                // compare winotherperms change
                                if (! Objects.equals(ixFileProfile.getWinotherperms(),offChainFileProfile.getWinotherperms())){
                                    updateHM.put("winOtherPerm",ixFileProfile.getWinotherperms());
                                }

                                // compare unixuserperm change
                                if (! Objects.equals(ixFileProfile.getUnixuserperm(),offChainFileProfile.getUnixuserperm())){
                                    updateHM.put("unixUserPerm",ixFileProfile.getUnixuserperm());
                                }

                                // compare unixgroupperm change
                                if (! Objects.equals(ixFileProfile.getUnixgroupperm(),offChainFileProfile.getUnixgroupperm())){
                                    updateHM.put("unixGroupPerm",ixFileProfile.getUnixgroupperm());
                                }

                                // compare unixotherperm change
                                if (! Objects.equals(ixFileProfile.getUnixotherperm(),offChainFileProfile.getUnixotherperm())){
                                    updateHM.put("unixOtherPerm",ixFileProfile.getUnixotherperm());
                                }

                                // compare username change
                                if (! Objects.equals(ixFileProfile.getUsername(),offChainFileProfile.getUsername())){
                                    updateHM.put("userName",ixFileProfile.getUsername());
                                }

                                // compare symlink change
                                if (! Objects.equals(ixFileProfile.getSymlink(),offChainFileProfile.getSymlink())){
                                    updateHM.put("symlink",ixFileProfile.getSymlink());
                                }

                                // compare size change
                                if (! Objects.equals(ixFileProfile.getSize(),offChainFileProfile.getSize())){
                                    updateHM.put("size",ixFileProfile.getSize());
                                }
                                updateHM.put("hash",ixHash);
                                updateSet.put(offchainList.get(j).getId(),updateHM);
                            }
                            offchainList.get(j).setFlag("update");
                            i++;
                            status = "F";
                        } else {
                            if (offchainList.get(j).getFlag() == null || (!offchainList.get(j).getFlag().equals("update"))) {
                                offchainList.get(j).setFlag("Removal");
                            }
                        }
                        if (j == (offChainLen - 1) && status.equals("NF")) {
                            additionSet.add(ixList.get(i));
                            System.out.println("new records discovered with i value " + i + " and j " + j + " " + ixList.get(i).getFilepath());
                            i++;
                        }
                        j++;
                        if(i == ixRemLen && j<offChainLen){
                            System.out.println("some records left in off chain");
                            while(j<offChainLen){
                                if(offchainList.get(j).getFlag() == null || (!offchainList.get(j).getFlag().equals("update") && !offchainList.get(j).getFlag().equals("Removal"))) {
                                    System.out.println("Deletion "+offchainList.get(j).getFilepath());
                                    deletionSet.add(offchainList.get(j));
                                }
                                j++;
                            }
                        }
                    }
                    status = "NF";

                }
                deletionSet.addAll(offchainList.stream().filter(fp -> "Removal".equals(fp.getFlag())).collect(Collectors.toList()));
            }
        }

        compRes.setAdditionSet(additionSet);
        compRes.setDeletionSet(deletionSet);
        compRes.setUpdateSet(updateSet);
        System.out.println("Addition set");
        for(int row=0;row<compRes.getAdditionSet().size();row++){
            System.out.println("Record No "+row+" "+" file path  "+compRes.getAdditionSet().get(row).getFilepath());
        }

        System.out.println("Deletion set");
        for(int row=0;row<compRes.getDeletionSet().size();row++){
            System.out.println("Record No "+row+" "+" file path  "+compRes.getDeletionSet().get(row).getFilepath());
        }
        System.out.println("Update set");
        compRes.getUpdateSet().forEach((key, value) -> {
            System.out.println(key + " => " + value);
            System.out.println("value keyset : "+value.keySet());
        });

        return compRes;
    }



    public ComparisonResults compareRemediationSetForEnc(List<FileProfile> ixList,List<FileProfile> offchainList) throws NoSuchAlgorithmException {


        ComparisonResults compRes = new ComparisonResults();

        LinkedHashMap<UUID, LinkedHashMap<String,String>> updateSet = new LinkedHashMap<>();

        List<FileProfile> offChainData = new ArrayList<>();

        List<FileProfile> deletionSet = new ArrayList<>();

        List<FileProfile> additionSet = new ArrayList<>();

        HashCode hashCode = new HashCode();

        int ixRemLen = ixList.size();
        int offChainLen = offchainList.size();

        System.out.println("No of IX Records : "+ixRemLen);
        System.out.println("No of offchain records : "+offChainLen);

        if(ixRemLen>offChainLen || offChainLen>ixRemLen || ixRemLen==offChainLen) {

            if (ixRemLen == 0) {
                System.out.println("No Records Found from IX");
                deletionSet.addAll(offchainList);
            } else if (offChainLen == 0) {
                System.out.println("No Records Found from Offchain Database");
                additionSet.addAll(ixList);
            } else {
                System.out.println("both sources have some records");
                for (int i = 0; i < ixRemLen; ) {
                    for (int j = 0; j < offChainLen && i < ixRemLen; ) {
                        System.out.println("comparison done b/w " + ixList.get(i).getFilepath() + " and " + offchainList.get(j).getFilepath());
                        if (ixList.get(i).getFilepath().equals(offchainList.get(j).getFilepath())) {

                            System.out.println("File path is same  " + ixList.get(i).getFilepath());

                            String  ixHash = hashCode.getHashOfFileProfileData(ixList.get(i));
                            String offChainHash  = hashCode.getHashOfFileProfileData(offchainList.get(j));

                            System.out.println("IX Hash --> "+ixHash);
                            System.out.println("offChain Hash --> "+offChainHash);

                            if(ixHash.equals(offChainHash)){
                                System.out.println("No Change found in attributes");
                            }
                            else{
                                FileProfile ixFileProfile = ixList.get(i);
                                FileProfile offChainFileProfile = offchainList.get(j);

                                LinkedHashMap<String,String> updateHM = new LinkedHashMap<>();

                                updateHM.put("hash",ixHash);
                                updateSet.put(offchainList.get(j).getId(),updateHM);
                            }
                            offchainList.get(j).setFlag("update");
                            i++;
                            status = "F";
                        } else {
                            if (offchainList.get(j).getFlag() == null || (!offchainList.get(j).getFlag().equals("update"))) {
                                offchainList.get(j).setFlag("Removal");
                            }
                        }
                        if (j == (offChainLen - 1) && status.equals("NF")) {
                            additionSet.add(ixList.get(i));
                            System.out.println("new records discovered with i value " + i + " and j " + j + " " + ixList.get(i).getFilepath());
                            i++;
                        }
                        j++;
                        if(i == ixRemLen && j<offChainLen){
                            System.out.println("some records left in off chain");
                            while(j<offChainLen){
                                if(offchainList.get(j).getFlag() == null || (!offchainList.get(j).getFlag().equals("update") && !offchainList.get(j).getFlag().equals("Removal"))) {
                                    System.out.println("Deletion "+offchainList.get(j).getFilepath());
                                    deletionSet.add(offchainList.get(j));
                                }
                                j++;
                            }
                        }
                    }
                    status = "NF";

                }
                deletionSet.addAll(offchainList.stream().filter(fp -> "Removal".equals(fp.getFlag())).collect(Collectors.toList()));
            }
        }

        compRes.setAdditionSet(additionSet);
        compRes.setDeletionSet(deletionSet);
        compRes.setUpdateSet(updateSet);
        System.out.println("Addition set");
        for(int row=0;row<compRes.getAdditionSet().size();row++){
            System.out.println("Record No "+row+" "+" file path  "+compRes.getAdditionSet().get(row).getFilepath());
        }

        System.out.println("Deletion set");
        for(int row=0;row<compRes.getDeletionSet().size();row++){
            System.out.println("Record No "+row+" "+" file path  "+compRes.getDeletionSet().get(row).getFilepath());
        }
        System.out.println("Update set");
        compRes.getUpdateSet().forEach((key, value) -> {
            System.out.println(key + " => " + value);
            System.out.println("value keyset : "+value.keySet());
        });

        return compRes;
    }

}
