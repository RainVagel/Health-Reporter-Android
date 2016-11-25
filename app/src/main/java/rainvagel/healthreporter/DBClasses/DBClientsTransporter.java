package rainvagel.healthreporter.DBClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rainvagel on 24.11.16.
 */

public class DBClientsTransporter {
    ArrayList<String> clientID = new ArrayList<>();
    Map<String, String> clientIdToFirstName = new HashMap<>();
    Map<String, String> clientIdToLastName = new HashMap<>();
    Map<String, String> clientIdToEmail = new HashMap<>();
    Map<String, String> clientIdToGender = new HashMap<>();
    Map<String, String> clientIdToGroupId = new HashMap<>();
    Map<String, String> clientIdToBirthDate = new HashMap<>();
    Map<String, String> clientIdToUpdated = new HashMap<>();

    public DBClientsTransporter(ArrayList<String> clientID, Map<String, String> clientIdToFirstName,
                                Map<String, String> clientIdToLastName, Map<String, String> clientIdToEmail,
                                Map<String, String> clientIdToGender, Map<String, String> clientIdToGroupId,
                                Map<String, String> clientIdToBirthDate, Map<String, String> clientIdToUpdated) {
        this.clientID = clientID;
        this.clientIdToFirstName = clientIdToFirstName;
        this.clientIdToLastName = clientIdToLastName;
        this.clientIdToEmail = clientIdToEmail;
        this.clientIdToGender = clientIdToGender;
        this.clientIdToGroupId = clientIdToGroupId;
        this.clientIdToBirthDate = clientIdToBirthDate;
        this.clientIdToUpdated = clientIdToUpdated;
    }

    public ArrayList<String> getClientID() {
        return clientID;
    }

    public void setClientID(ArrayList<String> clientID) {
        this.clientID = clientID;
    }

    public Map<String, String> getClientIdToFirstName() {
        return clientIdToFirstName;
    }

    public void setClientIdToFirstName(Map<String, String> clientIdToFirstName) {
        this.clientIdToFirstName = clientIdToFirstName;
    }

    public Map<String, String> getClientIdToLastName() {
        return clientIdToLastName;
    }

    public void setClientIdToLastName(Map<String, String> clientIdToLastName) {
        this.clientIdToLastName = clientIdToLastName;
    }

    public Map<String, String> getClientIdToEmail() {
        return clientIdToEmail;
    }

    public void setClientIdToEmail(Map<String, String> clientIdToEmail) {
        this.clientIdToEmail = clientIdToEmail;
    }

    public Map<String, String> getClientIdToGender() {
        return clientIdToGender;
    }

    public void setClientIdToGender(Map<String, String> clientIdToGender) {
        this.clientIdToGender = clientIdToGender;
    }

    public Map<String, String> getClientIdToBirthDate() {
        return clientIdToBirthDate;
    }

    public void setClientIdToBirthDate(Map<String, String> clientIdToBirthDate) {
        this.clientIdToBirthDate = clientIdToBirthDate;
    }

    public Map<String, String> getClientIdToGroupId() {
        return clientIdToGroupId;
    }

    public void setClientIdToGroupId(Map<String, String> clientIdToGroupId) {
        this.clientIdToGroupId = clientIdToGroupId;
    }

    public Map<String, String> getClientIdToUpdated() {
        return clientIdToUpdated;
    }

    public void setClientIdToUpdated(Map<String, String> clientIdToUpdated) {
        this.clientIdToUpdated = clientIdToUpdated;
    }
}
