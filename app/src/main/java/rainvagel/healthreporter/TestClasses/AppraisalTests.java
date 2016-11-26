package rainvagel.healthreporter.TestClasses;

/**
 * Created by Karl on 10/9/2016.
 */

public class AppraisalTests {
    String id;
    String testid;
    String score;
    String note;
    String trial1;
    String trial2;
    String trial3;
    String updated;
    String uploaded;

    public AppraisalTests(String id, String testid, String score, String note, String trial1, String trial2, String trial3, String updated, String uploaded) {

        this.id = id;
        this.testid = testid;
        this.score = score;
        this.note = note;
        this.trial1 = trial1;
        this.trial2 = trial2;
        this.trial3 = trial3;
        this.updated = updated;
        this.uploaded = uploaded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTrial1() {
        return trial1;
    }

    public void setTrial1(String trial1) {
        this.trial1 = trial1;
    }

    public String getTrial2() {
        return trial2;
    }

    public void setTrial2(String trial2) {
        this.trial2 = trial2;
    }

    public String getTrial3() {
        return trial3;
    }

    public void setTrial3(String trial3) {
        this.trial3 = trial3;
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
}
