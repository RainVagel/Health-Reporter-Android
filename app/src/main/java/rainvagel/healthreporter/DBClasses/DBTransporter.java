package rainvagel.healthreporter.DBClasses;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rainvagel on 24.11.16.
 */

public class DBTransporter {
    private ArrayList<String> groupID;
    private ArrayList<String> clientID;
    private Map<String, String> idToName;
    private Map<String, String> nameToId;
    private Map<String, String> idToId;
    private ArrayList<String> names;

    public DBTransporter(ArrayList<String> groupID, ArrayList<String> clientID,
                         Map<String, String> idToName, Map<String, String> nameToId,
                         Map<String, String> idToId, ArrayList<String> names) {
        this.idToId = idToId;
        this.clientID = clientID;
        this.groupID = groupID;
        this.idToName = idToName;
        this.nameToId = nameToId;
        this.names = names;
    }

    public ArrayList<String> getClientID() {
        return clientID;
    }

    public void setClientID(ArrayList<String> clientID) {
        this.clientID = clientID;
    }

    public Map<String, String> getIdToId() {
        return idToId;
    }

    public void setIdToId(Map<String, String> idToId) {
        this.idToId = idToId;
    }

    public ArrayList<String> getGroupID() {
        return groupID;
    }

    public void setGroupID(ArrayList<String> groupID) {
        this.groupID = groupID;
    }

    public Map<String, String> getIdToName() {
        return idToName;
    }

    public void setIdToName(Map<String, String> idToName) {
        this.idToName = idToName;
    }

    public Map<String, String> getNameToId() {
        return nameToId;
    }

    public void setNameToId(Map<String, String> nameToId) {
        this.nameToId = nameToId;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
}
