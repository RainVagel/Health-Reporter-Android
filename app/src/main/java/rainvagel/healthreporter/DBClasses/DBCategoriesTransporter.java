package rainvagel.healthreporter.DBClasses;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by rainvagel on 26.11.16.
 */

public class DBCategoriesTransporter {
    private ArrayList<String> categoriesID;
    private Map<String, String> categoriesIdToParentId;
    private Map<String, String> categoriesIdToName;
    private Map<String, String> categoriesIdToPosition;
    private Map<String, String> categoriesIdToUpdated;
    private Map<String, String> categoriesIdToUploaded;

    public DBCategoriesTransporter(ArrayList<String> categoriesID, Map<String, String> categoriesIdToParentId,
                                   Map<String, String> categoriesIdToName, Map<String, String> categoriesIdToPosition,
                                   Map<String, String> categoriesIdToUpdated, Map<String, String> categoriesIdToUploaded) {
        this.categoriesID = categoriesID;
        this.categoriesIdToParentId = categoriesIdToParentId;
        this.categoriesIdToName = categoriesIdToName;
        this.categoriesIdToPosition = categoriesIdToPosition;
        this.categoriesIdToUpdated = categoriesIdToUpdated;
        this.categoriesIdToUploaded = categoriesIdToUploaded;
    }

    public ArrayList<String> getCategoriesID() {
        return categoriesID;
    }

    public void setCategoriesID(ArrayList<String> categoriesID) {
        this.categoriesID = categoriesID;
    }

    public Map<String, String> getCategoriesIdToParentId() {
        return categoriesIdToParentId;
    }

    public void setCategoriesIdToParentId(Map<String, String> categoriesIdToParentId) {
        this.categoriesIdToParentId = categoriesIdToParentId;
    }

    public Map<String, String> getCategoriesIdToName() {
        return categoriesIdToName;
    }

    public void setCategoriesIdToName(Map<String, String> categoriesIdToName) {
        this.categoriesIdToName = categoriesIdToName;
    }

    public Map<String, String> getCategoriesIdToPosition() {
        return categoriesIdToPosition;
    }

    public void setCategoriesIdToPosition(Map<String, String> categoriesIdToPosition) {
        this.categoriesIdToPosition = categoriesIdToPosition;
    }

    public Map<String, String> getCategoriesIdToUpdated() {
        return categoriesIdToUpdated;
    }

    public void setCategoriesIdToUpdated(Map<String, String> categoriesIdToUpdated) {
        this.categoriesIdToUpdated = categoriesIdToUpdated;
    }

    public Map<String, String> getCategoriesIdToUploaded() {
        return categoriesIdToUploaded;
    }

    public void setCategoriesIdToUploaded(Map<String, String> categoriesIdToUploaded) {
        this.categoriesIdToUploaded = categoriesIdToUploaded;
    }
}
