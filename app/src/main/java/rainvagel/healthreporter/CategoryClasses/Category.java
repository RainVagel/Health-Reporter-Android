package rainvagel.healthreporter.CategoryClasses;
/**
 * Created by Karl on 25.09.2016.
 */
public class Category {
    String id;
    String parentId;
    String name;
    String position;
    String updated;
    String uploaded;

    public Category(String id, String parentId, String name, String position, String updated, String uploaded) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.position = position;
        this.updated = updated;
        this.uploaded = uploaded;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}