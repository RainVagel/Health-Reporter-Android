package rainvagel.healthreporter.DBClasses;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by rainvagel on 26.11.16.
 */

public class DBTestsTransporter {
    private ArrayList<String> testID;
    private Map<String, String> testIdToCategoryId;
    private Map<String, String> testIdToName;
    private Map<String, String> testIdToDescription;
    private Map<String, String> testIdToUnits;
    private Map<String, String> testIdToDecimals;
    private Map<String, String> testIdToWeight;
    private Map<String, String> testIdToFormulaF;
    private Map<String, String> testIdToFormulaM;
    private Map<String, String> testIdToPosition;
    private Map<String, String> testIdToUpdated;
    private Map<String, String> testIdToUploaded;

    public DBTestsTransporter(ArrayList<String> testID, Map<String, String> testIdToCategoryId,
                              Map<String, String> testIdToName, Map<String, String> testIdToDescription,
                              Map<String, String> testIdToUnits, Map<String, String> testIdToDecimals,
                              Map<String, String> testIdToWeight, Map<String, String> testIdToFormulaF,
                              Map<String, String> testIdToFormulaM, Map<String, String> testIdToPosition,
                              Map<String, String> testIdToUpdated, Map<String, String> testIdToUploaded) {
        this.testID = testID;
        this.testIdToCategoryId = testIdToCategoryId;
        this.testIdToName = testIdToName;
        this.testIdToDescription = testIdToDescription;
        this.testIdToUnits = testIdToUnits;
        this.testIdToDecimals = testIdToDecimals;
        this.testIdToWeight = testIdToWeight;
        this.testIdToFormulaF = testIdToFormulaF;
        this.testIdToFormulaM = testIdToFormulaM;
        this.testIdToPosition = testIdToPosition;
        this.testIdToUpdated = testIdToUpdated;
        this.testIdToUploaded = testIdToUploaded;
    }

    public ArrayList<String> getTestID() {
        return testID;
    }

    public void setTestID(ArrayList<String> testID) {
        this.testID = testID;
    }

    public Map<String, String> getTestIdToCategoryId() {
        return testIdToCategoryId;
    }

    public void setTestIdToCategoryId(Map<String, String> testIdToCategoryId) {
        this.testIdToCategoryId = testIdToCategoryId;
    }

    public Map<String, String> getTestIdToName() {
        return testIdToName;
    }

    public void setTestIdToName(Map<String, String> testIdToName) {
        this.testIdToName = testIdToName;
    }

    public Map<String, String> getTestIdToDescription() {
        return testIdToDescription;
    }

    public void setTestIdToDescription(Map<String, String> testIdToDescription) {
        this.testIdToDescription = testIdToDescription;
    }

    public Map<String, String> getTestIdToUnits() {
        return testIdToUnits;
    }

    public void setTestIdToUnits(Map<String, String> testIdToUnits) {
        this.testIdToUnits = testIdToUnits;
    }

    public Map<String, String> getTestIdToDecimals() {
        return testIdToDecimals;
    }

    public void setTestIdToDecimals(Map<String, String> testIdToDecimals) {
        this.testIdToDecimals = testIdToDecimals;
    }

    public Map<String, String> getTestIdToWeight() {
        return testIdToWeight;
    }

    public void setTestIdToWeight(Map<String, String> testIdToWeight) {
        this.testIdToWeight = testIdToWeight;
    }

    public Map<String, String> getTestIdToFormulaF() {
        return testIdToFormulaF;
    }

    public void setTestIdToFormulaF(Map<String, String> testIdToFormulaF) {
        this.testIdToFormulaF = testIdToFormulaF;
    }

    public Map<String, String> getTestIdToFormulaM() {
        return testIdToFormulaM;
    }

    public void setTestIdToFormulaM(Map<String, String> testIdToFormulaM) {
        this.testIdToFormulaM = testIdToFormulaM;
    }

    public Map<String, String> getTestIdToPosition() {
        return testIdToPosition;
    }

    public void setTestIdToPosition(Map<String, String> testIdToPosition) {
        this.testIdToPosition = testIdToPosition;
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
