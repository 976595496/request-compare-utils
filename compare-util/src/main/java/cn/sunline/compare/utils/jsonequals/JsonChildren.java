package cn.sunline.compare.utils.jsonequals;

import me.doubledutch.lazyjson.LazyArray;
import me.doubledutch.lazyjson.LazyObject;

import java.util.LinkedList;
import java.util.List;

/**
 * <h3>compare-util</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-07-04 12:55
 */
public class JsonChildren {
    private final List<Object> children = new LinkedList();
    private final List<JsonChildren.Type> childrenTypes = new LinkedList();
    private int countObjects = 0;
    private int countArrays = 0;
    private int countValues = 0;

    private JsonChildren() {
    }

    public static JsonChildren create() {
        return new JsonChildren();
    }

    public boolean isEmpty() {
        return this.children.isEmpty();
    }

    public int size() {
        return this.children.size();
    }

    public int objectCount() {
        return this.countObjects;
    }

    public int arrayCount() {
        return this.countArrays;
    }

    public int valueCount() {
        return this.countValues;
    }

    public JsonChildren.Type getType(int index) {
        return (JsonChildren.Type)this.childrenTypes.get(index);
    }

    public Object get(int index) {
        return this.children.get(index);
    }

    public LazyArray getArr(int index) {
        return (LazyArray)this.children.get(index);
    }

    public LazyObject getObj(int index) {
        return (LazyObject)this.children.get(index);
    }

    public void addChildObject(LazyObject obj) {
        this.children.add(obj);
        this.childrenTypes.add(JsonChildren.Type.OBJECT);
        ++this.countObjects;
    }

    public void addChildArray(LazyArray arr) {
        this.children.add(arr);
        this.childrenTypes.add(JsonChildren.Type.ARRAY);
        ++this.countArrays;
    }

    public void addChildValue(Object obj) {
        this.children.add(obj);
        this.childrenTypes.add(JsonChildren.Type.VALUE);
        ++this.countValues;
    }

    public List<Object> getChildren() {
        return this.children;
    }

    public List<JsonChildren.Type> getChildrenTypes() {
        return this.childrenTypes;
    }

    public void decrementObjCount() {
        --this.countObjects;
    }

    public static enum Type {
        OBJECT,
        ARRAY,
        VALUE;

        private Type() {
        }
    }
}
