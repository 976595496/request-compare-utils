package cn.sunline.compare.utils.jsonequals;

import java.util.List;

/**
 * <h3>compare-util</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-07-04 12:56
 */
public class JsonCompareResult {
    private final boolean isEqual;
    private final List<String> successMessages;
    private final List<String> inequalityMessages;
    private final List<InequalityDTO> inequalitys;

    private JsonCompareResult(boolean isEqual, List<String> successMessages, List<String> inequalityMessages, List<InequalityDTO> inequalitys) {
        this.isEqual = isEqual;
        this.successMessages = successMessages;
        this.inequalityMessages = inequalityMessages;
        this.inequalitys = inequalitys;
    }

    public static JsonCompareResult of(boolean isEqual, List<String> successMessages, List<String> inequalityMessages, List<InequalityDTO> inequalitys) {
        return new JsonCompareResult(isEqual, successMessages, inequalityMessages, inequalitys);
    }

    public List<String> getSuccessMessages() {
        return this.successMessages;
    }

    public List<String> getInequalityMessages() {
        return this.inequalityMessages;
    }

    public boolean isEqual() {
        return this.isEqual;
    }

    public int getSuccessCount() {
        return this.successMessages.size();
    }

    public int getInequalityCount() {
        return this.inequalityMessages.size();
    }

    public int getTotalMessageCount() {
        return this.successMessages.size() + this.inequalityMessages.size();
    }

    public List<InequalityDTO> getInequalitys() {
        return inequalitys;
    }
}
