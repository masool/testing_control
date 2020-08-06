package bccontrol;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class ComparisonResults {

    private List<FileProfile> additionSet;
    private List<FileProfile> deletionSet;
    private LinkedHashMap<UUID,LinkedHashMap<String,String>> updateSet;

    public List<FileProfile> getAdditionSet() {
        return additionSet;
    }

    public void setAdditionSet(List<FileProfile> additionSet) {
        this.additionSet = additionSet;
    }

    public List<FileProfile> getDeletionSet() {
        return deletionSet;
    }

    public void setDeletionSet(List<FileProfile> deletionSet) {
        this.deletionSet = deletionSet;
    }

    public LinkedHashMap<UUID, LinkedHashMap<String, String>> getUpdateSet() {
        return updateSet;
    }

    public void setUpdateSet(LinkedHashMap<UUID, LinkedHashMap<String, String>> updateSet) {
        this.updateSet = updateSet;
    }

    @Override
    public String toString() {
        return "ComparisonResults{" +
                "additionSet=" + additionSet +
                ", deletionSet=" + deletionSet +
                ", updateSet=" + updateSet +
                '}';
    }
}
