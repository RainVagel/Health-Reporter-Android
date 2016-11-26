package rainvagel.healthreporter.TestClasses;

/**
 * Created by Karl on 10/9/2016.
 */

public class Test {
    String id;
    String categoryid;
    String name;
    String description;
    String units;
    String decimals;
    String weight;
    String formulaF;
    String formulaM;
    String position;
    String updated;
    String uploaded;

    public Test(String id, String categoryid, String name, String description, String units,
                String decimals, String weight, String formulaF, String formulaM, String position,
                String updated, String uploaded) {
        this.id = id;
        this.categoryid = categoryid;
        this.name = name;
        this.description = description;
        this.units = units;
        this.decimals = decimals;
        this.weight = weight;
        this.formulaF = formulaF;
        this.formulaM = formulaM;
        this.position = position;
        this.updated = updated;
        this.uploaded = uploaded;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getDecimals() {
        return decimals;
    }

    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFormulaF() {
        return formulaF;
    }

    public void setFormulaF(String formulaF) {
        this.formulaF = formulaF;
    }

    public String getFormulaM() {
        return formulaM;
    }

    public void setFormulaM(String formulaM) {
        this.formulaM = formulaM;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }
}
