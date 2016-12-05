package rainvagel.healthreporter.DBClasses;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by rainvagel on 26.11.16.
 */

public class DBAppraisalTestsTransporter {
    private ArrayList<String> appraisalID;
    private ArrayList<String> testID;
    private Map<String, String> appraisalIdToTestId;
    private Map<String, String> appraisalIdToTestScores;
    private Map<String, String> appraisalIdToNote;
    private Map<String, String> appraisalIdToTrial1;
    private Map<String, String> appraisalIdToTrial2;
    private Map<String, String> appraisalIdToTrial3;
    private Map<String, String> appraisalIdToUpdated;
    private Map<String, String> appraisalIdToUploaded;

    public DBAppraisalTestsTransporter(ArrayList<String> appraisalID, ArrayList<String> testID,
                                       Map<String, String> appraisalIdToTestId, Map<String, String> appraisalIdToTestScores,
                                       Map<String, String> appraisalIdToNote, Map<String, String> appraisalIdToTrial1,
                                       Map<String, String> appraisalIdToTrial2, Map<String, String> appraisalIdToTrial3,
                                       Map<String, String> appraisalIdToUpdated, Map<String, String> appraisalIdToUploaded) {
        this.appraisalID = appraisalID;
        this.testID = testID;
        this.appraisalIdToTestId = appraisalIdToTestId;
        this.appraisalIdToTestScores = appraisalIdToTestScores;
        this.appraisalIdToNote = appraisalIdToNote;
        this.appraisalIdToTrial1 = appraisalIdToTrial1;
        this.appraisalIdToTrial2 = appraisalIdToTrial2;
        this.appraisalIdToTrial3 = appraisalIdToTrial3;
        this.appraisalIdToUpdated = appraisalIdToUpdated;
        this.appraisalIdToUploaded = appraisalIdToUploaded;
    }

    public ArrayList<String> getAppraisalID() {
        return appraisalID;
    }

    public void setAppraisalID(ArrayList<String> appraisalID) {
        this.appraisalID = appraisalID;
    }

    public ArrayList<String> getTestID() {
        return testID;
    }

    public void setTestID(ArrayList<String> testID) {
        this.testID = testID;
    }

    public Map<String, String> getAppraisalIdToTestId() {
        return appraisalIdToTestId;
    }

    public void setAppraisalIdToTestId(Map<String, String> appraisalIdToTestId) {
        this.appraisalIdToTestId = appraisalIdToTestId;
    }

    public Map<String, String> getAppraisalIdToTestScores() {
        return appraisalIdToTestScores;
    }

    public void setAppraisalIdToTestScores(Map<String, String> appraisalIdToTestScores) {
        this.appraisalIdToTestScores = appraisalIdToTestScores;
    }

    public Map<String, String> getAppraisalIdToNote() {
        return appraisalIdToNote;
    }

    public void setAppraisalIdToNote(Map<String, String> appraisalIdToNote) {
        this.appraisalIdToNote = appraisalIdToNote;
    }

    public Map<String, String> getAppraisalIdToTrial2() {
        return appraisalIdToTrial2;
    }

    public void setAppraisalIdToTrial2(Map<String, String> appraisalIdToTrial2) {
        this.appraisalIdToTrial2 = appraisalIdToTrial2;
    }

    public Map<String, String> getAppraisalIdToTrial1() {
        return appraisalIdToTrial1;
    }

    public void setAppraisalIdToTrial1(Map<String, String> appraisalIdToTrial1) {
        this.appraisalIdToTrial1 = appraisalIdToTrial1;
    }

    public Map<String, String> getAppraisalIdToTrial3() {
        return appraisalIdToTrial3;
    }

    public void setAppraisalIdToTrial3(Map<String, String> appraisalIdToTrial3) {
        this.appraisalIdToTrial3 = appraisalIdToTrial3;
    }

    public Map<String, String> getAppraisalIdToUpdated() {
        return appraisalIdToUpdated;
    }

    public void setAppraisalIdToUpdated(Map<String, String> appraisalIdToUpdated) {
        this.appraisalIdToUpdated = appraisalIdToUpdated;
    }

    public Map<String, String> getAppraisalIdToUploaded() {
        return appraisalIdToUploaded;
    }

    public void setAppraisalIdToUploaded(Map<String, String> appraisalIdToUploaded) {
        this.appraisalIdToUploaded = appraisalIdToUploaded;
    }
}
