package cn.jeesoft.widget.pickerview;

/**
 * Created by harshvardhan on 27/11/15.
 */
public class GaradiItem {
    private String value = "";
    private String id = "";
    private String levelID = "";

    public GaradiItem(String value, String id) {
        this.value = value;
        this.id = id;
    }

    public GaradiItem(String value, String id, String levelID) {
        this.value = value;
        this.id = id;
        this.levelID = levelID;
    }

    public GaradiItem() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }
}
