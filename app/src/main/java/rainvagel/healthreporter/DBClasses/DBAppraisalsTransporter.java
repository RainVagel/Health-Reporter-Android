package rainvagel.healthreporter.DBClasses;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by rainvagel on 26.11.16.
 */

public class DBAppraisalsTransporter {
    private ArrayList<String> appraisalID;
    private Map<String, String> appraisalIdToAppraiserId;
    private Map<String, String> appraisalIdToClientId;
    private Map<String, String> appraisalIdToAppraisalDate;
    private Map<String, String> appraisalIdToUpdated;
    private Map<String, String> appraisalIdToUploaded;

    public DBAppraisalsTransporter(ArrayList<String> appraisalID, Map<String,
            String> appraisalIdToAppraiserId, Map<String, String> appraisalIdToUpdated,
                                   Map<String, String> appraisalIdToClientId,
                                   Map<String, String> appraisalIdToAppraisalDate,
                                   Map<String, String> appraisalIdToUploaded) {
        this.appraisalID = appraisalID;
        this.appraisalIdToAppraiserId = appraisalIdToAppraiserId;
        this.appraisalIdToUpdated = appraisalIdToUpdated;
        this.appraisalIdToClientId = appraisalIdToClientId;
        this.appraisalIdToAppraisalDate = appraisalIdToAppraisalDate;
        this.appraisalIdToUploaded = appraisalIdToUploaded;
    }

    public ArrayList<String> getAppraisalID() {
        return appraisalID;
    }

    public void setAppraisalID(ArrayList<String> appraisalID) {
        this.appraisalID = appraisalID;
    }

    public Map<String, String> getAppraisalIdToAppraiserId() {
        return appraisalIdToAppraiserId;
    }

    public void setAppraisalIdToAppraiserId(Map<String, String> appraisalIdToAppraiserId) {
        this.appraisalIdToAppraiserId = appraisalIdToAppraiserId;
    }

    public Map<String, String> getAppraisalIdToUpdated() {
        return appraisalIdToUpdated;
    }

    public void setAppraisalIdToUpdated(Map<String, String> appraisalIdToUpdated) {
        this.appraisalIdToUpdated = appraisalIdToUpdated;
    }

    public Map<String, String> getAppraisalIdToClientId() {
        return appraisalIdToClientId;
    }

    public void setAppraisalIdToClientId(Map<String, String> appraisalIdToClientId) {
        this.appraisalIdToClientId = appraisalIdToClientId;
    }

    public Map<String, String> getAppraisalIdToAppraisalDate() {
        return appraisalIdToAppraisalDate;
    }

    public void setAppraisalIdToAppraisalDate(Map<String, String> appraisalIdToAppraisalDate) {
        this.appraisalIdToAppraisalDate = appraisalIdToAppraisalDate;
    }

    public Map<String, String> getAppraisalIdToUploaded() {
        return appraisalIdToUploaded;
    }

    public void setAppraisalIdToUploaded(Map<String, String> appraisalIdToUploaded) {
        this.appraisalIdToUploaded = appraisalIdToUploaded;
    }
}
