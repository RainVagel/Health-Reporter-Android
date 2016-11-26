package rainvagel.healthreporter.DBClasses;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by rainvagel on 27.11.16.
 */

public class DBPresetTestsTransporter {
    private ArrayList<String> testID;
    private Map<String, Set<String>> testIdToPresetId;
    private Map<String, String> testIdToUpdated;
    private Map<String, String> testIdToUploaded;

    public DBPresetTestsTransporter(ArrayList<String> testID, Map<String, Set<String>> testIdToPresetId,
                                    Map<String, String> testIdToUpdated, Map<String, String> testIdToUploaded) {
        this.testID = testID;
        this.testIdToPresetId = testIdToPresetId;
        this.testIdToUpdated = testIdToUpdated;
        this.testIdToUploaded = testIdToUploaded;
    }

    public ArrayList<String> getTestID() {
        return testID;
    }

    public void setTestID(ArrayList<String> testID) {
        this.testID = testID;
    }

    public Map<String, Set<String>> getTestIdToPresetId() {
        return testIdToPresetId;
    }

    public void setTestIdToPresetId(Map<String, Set<String>> testIdToPresetId) {
        this.testIdToPresetId = testIdToPresetId;
    }

    public Map<String, String> getTestIdToUpdated() {
        return testIdToUpdated;
    }

    public void setTestIdToUpdated(Map<String, String> testIdToUpdated) {
        this.testIdToUpdated = testIdToUpdated;
    }

    public Map<String, String> getTestIdToUploaded() {
        return testIdToUploaded;
    }

    public void setTestIdToUploaded(Map<String, String> testIdToUploaded) {
        this.testIdToUploaded = testIdToUploaded;
    }
}
