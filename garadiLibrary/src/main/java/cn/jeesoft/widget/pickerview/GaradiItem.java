package cn.jeesoft.widget.pickerview;

/**
 * Created by harshvardhan on 27/11/15.
 */
public class GaradiItem {
    private String value = "";
    private String id = "";

    public GaradiItem(String value, String id) {
        this.value = value;
        this.id = id;
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
}
