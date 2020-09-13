package cn.sunline.compare.utils.jsonequals;

import me.doubledutch.lazyjson.LazyArray;
import me.doubledutch.lazyjson.LazyElement;
import me.doubledutch.lazyjson.LazyObject;
import me.doubledutch.lazyjson.LazyType;

import java.util.Map;
import java.util.Set;

/**
 * <h3>compare-util</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-07-04 12:57
 */
public class JsonRoot {
    private final LazyElement root;
    private final LazyType rootType;

    private JsonRoot(LazyElement root) {
        this.root = root;
        this.rootType = root.getType();
    }

    private JsonRoot(String raw) {
        if (raw.startsWith("{")) {
            this.rootType = LazyType.OBJECT;
            this.root = new LazyObject(raw);
        } else if (raw.startsWith("[")) {
            this.rootType = LazyType.ARRAY;
            this.root = new LazyArray(raw);
        } else {
            this.rootType = LazyType.NULL;
            this.root = null;
        }

    }

    public static JsonRoot from(LazyElement root) {
        return new JsonRoot(root);
    }

    public static JsonRoot from(String raw) {
        return new JsonRoot(raw);
    }

    public JsonCompareResult compareNodeTo(JsonRoot other, Set<String> ignoreFields, Map<String, String> pruneFields) {
        if (other != null) {
            if (this.isRootObject() && other.isRootObject()) {
                return JsonEquals.ofType(LazyType.OBJECT).withSource(this.getRoot()).withComparate(other.getRoot()).withIgnoreFields(ignoreFields).withPruneFields(pruneFields).compareNode();
            }

            if (this.isRootArray() && other.isRootArray()) {
                return JsonEquals.ofType(LazyType.ARRAY).withSource(this.getRoot()).withComparate(other.getRoot()).withIgnoreFields(ignoreFields).withPruneFields(pruneFields).compareNode();
            }
        }

        return null;
    }

    public JsonCompareResult compareNodeTo(JsonRoot other) {
        return this.compareNodeTo(other, (Set)null, (Map)null);
    }

    public JsonCompareResult compareNodeToWithIgnore(JsonRoot other, Set<String> ignoreFields) {
        return this.compareNodeTo(other, ignoreFields, (Map)null);
    }

    public JsonCompareResult compareNodeToWithPrune(JsonRoot other, Map<String, String> pruneFields) {
        return this.compareNodeTo(other, (Set)null, pruneFields);
    }



    public JsonCompareResult compareKeyTo(JsonRoot other, Set<String> ignoreFields, Map<String, String> pruneFields) {
        if (other != null) {
            if (this.isRootObject() && other.isRootObject()) {
                return JsonEquals.ofType(LazyType.OBJECT).withSource(this.getRoot()).withComparate(other.getRoot()).withIgnoreFields(ignoreFields).withPruneFields(pruneFields).compareKey();
            }

            if (this.isRootArray() && other.isRootArray()) {
                return JsonEquals.ofType(LazyType.ARRAY).withSource(this.getRoot()).withComparate(other.getRoot()).withIgnoreFields(ignoreFields).withPruneFields(pruneFields).compareKey();
            }
        }

        return null;
    }

    public JsonCompareResult compareKeyTo(JsonRoot other) {
        return this.compareKeyTo(other, (Set)null, (Map)null);
    }

    public JsonCompareResult compareKeyToWithIgnore(JsonRoot other, Set<String> ignoreFields) {
        return this.compareKeyTo(other, ignoreFields, (Map)null);
    }

    public JsonCompareResult compareKeyToWithPrune(JsonRoot other, Map<String, String> pruneFields) {
        return this.compareKeyTo(other, (Set)null, pruneFields);
    }


    public LazyElement getRoot() {
        return this.root;
    }

    public boolean isRootObject() {
        return this.rootType == LazyType.OBJECT;
    }

    public boolean isRootArray() {
        return this.rootType == LazyType.ARRAY;
    }

    public String toString() {
        return this.root.toString();
    }
}
