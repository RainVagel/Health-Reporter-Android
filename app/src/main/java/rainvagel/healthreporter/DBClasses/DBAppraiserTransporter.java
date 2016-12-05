package rainvagel.healthreporter.DBClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rainvagel on 03.12.16.
 */

public class DBAppraiserTransporter {
    ArrayList<String> appraiserID;
    Map<String, String> appraiserIdToName;
    Map<String, String> appraiserIdToUpdated;
    Map<String, String> appraiserIdToUploaded;

    public DBAppraiserTransporter(ArrayList<String> appraiserID, Map<String, String> appraiserIdToName,
                                  Map<String, String> appraiserIdToUpdated,
                                  Map<String, String> appraiserIdToUploaded) {
        this.appraiserID = appraiserID;
        this.appraiserIdToName = appraiserIdToName;
        this.appraiserIdToUpdated = appraiserIdToUpdated;
        this.appraiserIdToUploaded = appraiserIdToUploaded;
    }

    public ArrayList<String> getAppraiserID() {
        return appraiserID;
    }

    public void setAppraiserID(ArrayList<String> appraiserID) {
        this.appraiserID = appraiserID;
    }

    public Map<String, String> getAppraiserIdToName() {
        return appraiserIdToName;
    }

    public void setAppraiserIdToName(Map<String, String> appraiserIdToName) {
        this.appraiserIdToName = appraiserIdToName;
    }

    public Map<String, String> getAppraiserIdToUpdated() {
        return appraiserIdToUpdated;
    }

    public void setAppraiserIdToUpdated(Map<String, String> appraiserIdToUpdated) {
        this.appraiserIdToUpdated = appraiserIdToUpdated;
    }

    public Map<String, String> getAppraiserIdToUploaded() {
        return appraiserIdToUploaded;
    }

    public void setAppraiserIdToUploaded(Map<String, String> appraiserIdToUploaded) {
        this.appraiserIdToUploaded = appraiserIdToUploaded;
    }
}
