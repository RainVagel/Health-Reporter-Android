package rainvagel.healthreporter.DBClasses;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by rainvagel on 27.11.16.
 */

public class DBPresetsTransporter {
    private ArrayList<String> presetID;
    private Map<String, String> presetIdToName;
    private Map<String, String> presetIdToUpdated;
    private Map<String, String> presetIdToUploaded;

    public DBPresetsTransporter(ArrayList<String> presetID, Map<String, String> presetIdToName,
                                Map<String, String> presetIdToUpdated, Map<String, String> presetIdToUploaded) {
        this.presetID = presetID;
        this.presetIdToName = presetIdToName;
        this.presetIdToUpdated = presetIdToUpdated;
        this.presetIdToUploaded = presetIdToUploaded;
    }

    public ArrayList<String> getPresetID() {
        return presetID;
    }

    public void setPresetID(ArrayList<String> presetID) {
        this.presetID = presetID;
    }

    public Map<String, String> getPresetIdToName() {
        return presetIdToName;
    }

    public void setPresetIdToName(Map<String, String> presetIdToName) {
        this.presetIdToName = presetIdToName;
    }

    public Map<String, String> getPresetIdToUpdated() {
        return presetIdToUpdated;
    }

    public void setPresetIdToUpdated(Map<String, String> presetIdToUpdated) {
        this.presetIdToUpdated = presetIdToUpdated;
    }

    public Map<String, String> getPresetIdToUploaded() {
        return presetIdToUploaded;
    }

    public void setPresetIdToUploaded(Map<String, String> presetIdToUploaded) {
        this.presetIdToUploaded = presetIdToUploaded;
    }
}
