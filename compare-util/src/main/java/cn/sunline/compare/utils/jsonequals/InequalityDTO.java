package cn.sunline.compare.utils.jsonequals;

/**
 * <h3>compare-util</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-07-04 13:41
 */
public class InequalityDTO {
    private String key;

    private String json1;

    private String json2;

    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJson1() {
        return json1;
    }

    public void setJson1(String json1) {
        this.json1 = json1;
    }

    public String getJson2() {
        return json2;
    }

    public void setJson2(String json2) {
        this.json2 = json2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InequalityDTO(String key, String json1, String json2, String type) {
        this.key = key;
        this.json1 = json1;
        this.json2 = json2;
        this.type = type;
    }
}
