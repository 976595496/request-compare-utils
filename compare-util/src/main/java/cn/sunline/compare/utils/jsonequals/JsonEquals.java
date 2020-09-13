package cn.sunline.compare.utils.jsonequals;

import lombok.extern.slf4j.Slf4j;
import me.doubledutch.lazyjson.LazyArray;
import me.doubledutch.lazyjson.LazyElement;
import me.doubledutch.lazyjson.LazyObject;
import me.doubledutch.lazyjson.LazyType;
import cn.sunline.compare.utils.jsonequals.JsonChildren.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * <h3>compare-util</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-07-04 12:57
 */
@Slf4j
public class JsonEquals {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonEquals.class);
    private static boolean debugMode = false;
    private final LazyType rootType;
    private LazyElement source = null;
    private LazyElement comparate = null;
    private Map<String, String> pruneFields;
    private Set<String> ignoreFields;
    private List<String> successMessages;
    private List<String> inequalityMessages;
    private List<InequalityDTO> inequalitys;

    private JsonEquals(LazyType rootType) {
        this.rootType = rootType;
        this.successMessages = new ArrayList();
        this.inequalityMessages = new ArrayList();
        this.inequalitys = new ArrayList<>();
    }

    public static JsonEquals ofType(LazyType rootType) {
        return new JsonEquals(rootType);
    }

    public static JsonEquals between(LazyObject source, LazyObject comparate) {
        return (new JsonEquals(LazyType.OBJECT)).withSource(source).withComparate(comparate);
    }

    public static JsonEquals between(LazyArray source, LazyArray comparate) {
        return (new JsonEquals(LazyType.ARRAY)).withSource(source).withComparate(comparate);
    }

    public JsonEquals withSource(LazyElement source) {
        this.source = source;
        return this;
    }

    public JsonEquals withComparate(LazyElement comparate) {
        this.comparate = comparate;
        return this;
    }

    public JsonEquals withIgnoreFields(Set<String> ignoreFields) {
        this.ignoreFields = ignoreFields;
        return this;
    }

    public JsonEquals withPruneFields(Map<String, String> pruneFields) {
        this.pruneFields = pruneFields;
        return this;
    }

    public JsonCompareResult compareNode() {
        if (this.rootType == LazyType.OBJECT) {
            this.compareNode((LazyObject)this.source, (LazyObject)this.comparate);
        } else {
            this.compareNode((LazyArray)this.source, (LazyArray)this.comparate);
        }

        return JsonCompareResult.of(this.inequalityMessages.isEmpty(), this.successMessages, this.inequalityMessages, this.inequalitys);
    }

    public JsonCompareResult compareKey() {
        if (this.rootType == LazyType.OBJECT) {
            this.compareKey((LazyObject)this.source, (LazyObject)this.comparate);
        } else {
            this.compareKey((LazyArray)this.source, (LazyArray)this.comparate);
        }

        return JsonCompareResult.of(this.inequalityMessages.isEmpty(), this.successMessages, this.inequalityMessages, this.inequalitys);
    }

    public void compareNode(LazyObject a, LazyObject b) {
        this.compareNode(a, b, "$");
    }

    public void compareNode(LazyArray a, LazyArray b) {
        this.compareNode(a, b, "$");
    }


    public void compareKey(LazyObject a, LazyObject b) {
        this.compareKey(a, b, "$");
    }

    public void compareKey(LazyArray a, LazyArray b) {
        this.compareKey(a, b, "$");
    }

    public void compareNode(LazyObject a, LazyObject b, String currentPath) {
        if (!this.pathIsIgnoreField(currentPath)) {
            Set<String> fieldsA = a.keySet();
            Set<String> fieldsB = b.keySet();
            if (fieldsA.equals(fieldsB)) {
                Iterator var6 = fieldsA.iterator();

                while(true) {
                    while(var6.hasNext()) {
                        String fieldName = (String)var6.next();
                        if (childIsObject(a, fieldName) && childIsObject(b, fieldName)) {
                            this.compareNode(a.getJSONObject(fieldName), b.getJSONObject(fieldName), getChildPath(currentPath, fieldName));
                        } else if (childIsArray(a, fieldName) && childIsArray(b, fieldName)) {
                            this.compareNode(a.getJSONArray(fieldName), b.getJSONArray(fieldName), getChildPath(currentPath, fieldName));
                        } else {
                            this.compareValues(a, b, fieldName, getChildPath(currentPath, fieldName));
                        }
                    }

                    return;
                }
            } else {
                log.info("6");
                this.inequalityMessages.add("JSON objects do not have the same child key names! " + fieldsA + " vs. " + fieldsB);
                InequalityDTO dto = new InequalityDTO(currentPath, fieldsA.toString(), fieldsB.toString(), "key diff");
                this.inequalitys.add(dto);
            }
        }
    }

    public void compareKey(LazyObject a, LazyObject b, String currentPath) {
        if (!this.pathIsIgnoreField(currentPath)) {
            Set<String> fieldsA = a.keySet();
            Set<String> fieldsB = b.keySet();
            if (fieldsA.equals(fieldsB)) {
                Iterator var6 = fieldsA.iterator();

                while(true) {
                    while(var6.hasNext()) {
                        String fieldName = (String)var6.next();
                        if (childIsObject(a, fieldName) && childIsObject(b, fieldName)) {
                            this.compareKey(a.getJSONObject(fieldName), b.getJSONObject(fieldName), getChildPath(currentPath, fieldName));
                        } else if (childIsArray(a, fieldName) && childIsArray(b, fieldName)) {
                            this.compareKey(a.getJSONArray(fieldName), b.getJSONArray(fieldName), getChildPath(currentPath, fieldName));
                        }else {
                            this.compareValueTypes(a, b, fieldName, getChildPath(currentPath, fieldName));
                        }
                    }

                    return;
                }
            } else {
                log.info("5");
                this.inequalityMessages.add("JSON objects do not have the same child key names! " + fieldsA + " vs. " + fieldsB);

                InequalityDTO dto = new InequalityDTO(currentPath, fieldsA.toString(), fieldsB.toString(), "child key diff");
                this.inequalitys.add(dto);
            }
        }
    }

    public void compareNode(LazyArray a, LazyArray b, String currentPath) {
        if (!this.pathIsIgnoreField(currentPath)) {
            JsonChildren childrenA = this.getChildList(a);
            JsonChildren childrenB = this.getChildList(b);
            if (this.pruneFields != null && !this.pruneFields.isEmpty()) {
                this.prune(childrenA, currentPath, "source");
                this.prune(childrenB, currentPath, "comparate");
            }

            if (!childrenA.isEmpty() && !childrenB.isEmpty()) {
                if (childrenA.objectCount() == childrenB.objectCount() && childrenA.arrayCount() == childrenB.arrayCount() && childrenA.valueCount() == childrenB.valueCount()) {
                    for(int i = 0; i < childrenA.size(); ++i) {
                        if (childrenA.getType(i) == Type.OBJECT && childrenB.getType(i) == Type.OBJECT) {
                            this.compareNode(childrenA.getObj(i), childrenB.getObj(i), currentPath + "[" + i + "]");
                        } else if (childrenA.getType(i) == Type.ARRAY && childrenB.getType(i) == Type.ARRAY) {
                            this.compareNode(childrenA.getArr(i), childrenB.getArr(i), currentPath + "[" + i + "]");
                        } else if (childrenA.getType(i) == Type.VALUE && childrenB.getType(i) == Type.VALUE) {
                            if (debugMode) {
                                LOGGER.debug("Checking array value: {}", currentPath + "[" + i + "]");
                            }

                            if (!childrenA.get(i).equals(childrenB.get(i))) {
                                log.info("4");
                                this.inequalityMessages.add(currentPath + " JSON array value expected to be " + childrenA.get(i) + " but got " + childrenB.get(i));
                                InequalityDTO dto = new InequalityDTO(currentPath, childrenA.get(i).toString(), childrenB.get(i).toString(), "type");
                                this.inequalitys.add(dto);

                            } else {
                                this.successMessages.add(currentPath + "[" + i + "]" + "==" + childrenA.get(i));
                            }
                        } else {
                            log.info("3");
                            this.inequalityMessages.add(currentPath + "[" + i + "]" + " types were not the same! Expected " + childrenA.getType(i) + " but got " + childrenB.getType(i));
                            InequalityDTO dto = new InequalityDTO(currentPath, childrenA.toString(), childrenB.toString(), "type");
                            this.inequalitys.add(dto);
                        }
                    }
                } else {
                    log.info("2");
                    this.inequalityMessages.add(currentPath + " JSON array not equal in length! " + childrenA.size() + " vs " + childrenB.size());
                    InequalityDTO dto = new InequalityDTO(currentPath, childrenA.toString(), childrenB.toString(), "size diff");
                    this.inequalitys.add(dto);
                }
            }

        }
    }
    public void compareKey(LazyArray a, LazyArray b, String currentPath) {
        if (!this.pathIsIgnoreField(currentPath)) {
            JsonChildren childrenA = this.getChildList(a);
            JsonChildren childrenB = this.getChildList(b);
            if (this.pruneFields != null && !this.pruneFields.isEmpty()) {
                this.prune(childrenA, currentPath, "source");
                this.prune(childrenB, currentPath, "comparate");
            }

            if (!childrenA.isEmpty() && !childrenB.isEmpty()) {
                if (childrenA.objectCount() == childrenB.objectCount() && childrenA.arrayCount() == childrenB.arrayCount() && childrenA.valueCount() == childrenB.valueCount()) {
                    for(int i = 0; i < childrenA.size(); ++i) {
                        if (childrenA.getType(i) == Type.OBJECT && childrenB.getType(i) == Type.OBJECT) {
                            this.compareKey(childrenA.getObj(i), childrenB.getObj(i), currentPath + "[" + i + "]");
                        } else if (childrenA.getType(i) == Type.ARRAY && childrenB.getType(i) == Type.ARRAY) {
                            this.compareKey(childrenA.getArr(i), childrenB.getArr(i), currentPath + "[" + i + "]");
                        } else if (!(childrenA.getType(i) == Type.VALUE && childrenB.getType(i) == Type.VALUE)) {
                            this.inequalityMessages.add(currentPath + "[" + i + "]" + " types were not the same! Expected " + childrenA.getType(i) + " but got " + childrenB.getType(i));
                            InequalityDTO dto = new InequalityDTO(currentPath, childrenA.toString(), childrenB.toString(), "type");
                            this.inequalitys.add(dto);
                        }
                    }
                }
            }

        }
    }

    public void compareValues(LazyObject a, LazyObject b, String fieldName, String currentPath) {
        if (!this.pathIsIgnoreField(currentPath)) {
            if (debugMode) {
                LOGGER.debug("Checking leaf object: {}", currentPath);
            }

            if (a.getType(fieldName) == LazyType.STRING && b.getType(fieldName) == LazyType.STRING) {
                if (a.getString(fieldName).equals(b.getString(fieldName))) {
                    this.successMessages.add(currentPath + "==" + a.getString(fieldName));
                } else {
                    this.logInequality(a.getString(fieldName), b.getString(fieldName), currentPath);
                }
            } else if (a.getType(fieldName) == LazyType.INTEGER && b.getType(fieldName) == LazyType.INTEGER) {
                if (a.getInt(fieldName) == b.getInt(fieldName)) {
                    this.successMessages.add(currentPath + "==" + a.getString(fieldName));
                } else {
                    this.logInequality(a.getString(fieldName), b.getString(fieldName), currentPath);
                }
            } else if (a.getType(fieldName) == LazyType.BOOLEAN && b.getType(fieldName) == LazyType.BOOLEAN) {
                if (a.getBoolean(fieldName) == b.getBoolean(fieldName)) {
                    this.successMessages.add(currentPath + "==" + a.getString(fieldName));
                } else {
                    this.logInequality(a.getString(fieldName), b.getString(fieldName), currentPath);
                }
            } else if (a.getType(fieldName) == LazyType.FLOAT && b.getType(fieldName) == LazyType.FLOAT) {
                if (a.getString(fieldName).equals(b.getString(fieldName))) {
                    this.successMessages.add(currentPath + "==" + a.getString(fieldName));
                } else {
                    this.logInequality(a.getString(fieldName), b.getString(fieldName), currentPath);
                }
            } else if (a.getType(fieldName) == LazyType.NULL && b.getType(fieldName) == LazyType.NULL) {
                this.successMessages.add(currentPath + "==" + a.getString(fieldName));
            } else {
                this.inequalityMessages.add(currentPath + " were not of the same type! Expected type " + a.getType(fieldName) + " but got type " + b.getType(fieldName));
                log.info("1");
                InequalityDTO dto = new InequalityDTO(getChildPath(currentPath, fieldName), a.getType(fieldName).toString(), b.getType(fieldName).toString(), "type");
//                InequalityDTO dto = new InequalityDTO(currentPath, a.getString(fieldName).toString(), b.getString(fieldName), "type");
                this.inequalitys.add(dto);
            }

        }
    }
    public void compareValueTypes(LazyObject a, LazyObject b, String fieldName, String currentPath) {
        if (!this.pathIsIgnoreField(currentPath)) {
            if (debugMode) {
                LOGGER.debug("Checking leaf object: {}", currentPath);
            }

            if (a.getType(fieldName) != b.getType(fieldName)) {
                this.inequalityMessages.add(currentPath + " were not of the same type! Expected type " + a.getType(fieldName) + " but got type " + b.getType(fieldName));
                log.info("类型不同差异:"+ a.getType(fieldName).toString()+b.getType(fieldName).toString());
                InequalityDTO dto = new InequalityDTO(getChildPath(currentPath, fieldName), a.getType(fieldName).toString(), b.getType(fieldName).toString(), "type");
                this.inequalitys.add(dto);
            }

        }
    }

    private JsonChildren getChildList(LazyArray parent) {
        JsonChildren jsonChildren = JsonChildren.create();

        for(int i = 0; i < parent.length(); ++i) {
            switch(parent.getType(i)) {
                case OBJECT:
                    jsonChildren.addChildObject(parent.getJSONObject(i));
                    break;
                case ARRAY:
                    jsonChildren.addChildArray(parent.getJSONArray(i));
                    break;
                case STRING:
                    jsonChildren.addChildValue(parent.getString(i));
                    break;
                case INTEGER:
                    jsonChildren.addChildValue(parent.getInt(i));
                    break;
                case BOOLEAN:
                    jsonChildren.addChildValue(parent.getBoolean(i));
                    break;
                case FLOAT:
                    jsonChildren.addChildValue(parent.getDouble(i));
                    break;
                default:
                    jsonChildren.addChildValue((Object)null);
            }
        }

        return jsonChildren;
    }

    private void prune(JsonChildren children, String currentPath, String identifier) {
        List<Object> childNodes = children.getChildren();
        List<Type> childTypes = children.getChildrenTypes();
        Iterator<Object> childIterator = childNodes.iterator();
        Iterator<Type> childTypesIterator = childTypes.iterator();

        for(int i = 0; childIterator.hasNext() && childTypesIterator.hasNext(); ++i) {
            Object child = childIterator.next();
            Type childType = (Type)childTypesIterator.next();
            if (childType == Type.OBJECT && this.pathIsPruneField(currentPath + "[" + i + "]", (LazyObject)child)) {
                if (debugMode) {
                    LOGGER.debug("Pruning {} {}{}{}{}", new Object[]{identifier, currentPath, "[", i, "]"});
                }

                childIterator.remove();
                childTypesIterator.remove();
                children.decrementObjCount();
            }
        }

    }

    private boolean pathIsPruneField(String currentPath, LazyObject node) {
        Iterator var3 = this.pruneFields.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, String> pruneEntry = (Map.Entry)var3.next();
            String key = (String)pruneEntry.getKey();
            String value = (String)pruneEntry.getValue();
            String[] fields = key.split(":");
            if (fields.length == 0 || fields.length > 2) {
                return false;
            }

            if (pathEquals(currentPath, fields[0])) {
                LazyObject currentNode = node;
                String[] childNodes = fields[1].split("\\.");

                for(int i = 0; i < childNodes.length - 1; ++i) {
                    if (currentNode == null || currentNode.getType() != LazyType.OBJECT) {
                        return false;
                    }

                    currentNode = currentNode.getJSONObject(childNodes[i]);
                }

                if (currentNode != null) {
                    return this.getValueAsString(currentNode, childNodes[childNodes.length - 1]).equals(value);
                }
            }
        }

        return false;
    }

    private String getValueAsString(LazyObject jsonNode, String fieldName) {
        switch(jsonNode.getType(fieldName)) {
            case STRING:
                return jsonNode.getString(fieldName);
            case INTEGER:
                return Integer.toString(jsonNode.getInt(fieldName));
            case BOOLEAN:
                return Boolean.toString(jsonNode.getBoolean(fieldName));
            case FLOAT:
                return Double.toString(jsonNode.getDouble(fieldName));
            default:
                return "null";
        }
    }

    private String getValueAsString(LazyArray jsonNode, int i) {
        switch(jsonNode.getType(i)) {
            case STRING:
                return jsonNode.getString(i);
            case INTEGER:
                return Integer.toString(jsonNode.getInt(i));
            case BOOLEAN:
                return Boolean.toString(jsonNode.getBoolean(i));
            case FLOAT:
                return Double.toString(jsonNode.getDouble(i));
            default:
                return "null";
        }
    }

    private boolean pathIsIgnoreField(String currentPath) {
        if (this.ignoreFields != null && !this.ignoreFields.isEmpty()) {
            Iterator var2 = this.ignoreFields.iterator();

            while(var2.hasNext()) {
                String ignoreField = (String)var2.next();
                if (pathEquals(currentPath, ignoreField)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean pathEquals(String path, String patternedPath) {
        String[] pathArr = path.split("\\.");
        String[] ignorePathArr = patternedPath.split("\\.");
        if (pathArr.length != ignorePathArr.length) {
            return false;
        } else {
            for(int i = 0; i < pathArr.length; ++i) {
                String nodePath = pathArr[i];
                String ignoreNodeArr = ignorePathArr[i];
                boolean nodeIsArray = nodePath.endsWith("]");
                boolean ignoreNodeIsArray = ignoreNodeArr.endsWith("]");
                if (nodeIsArray && ignoreNodeIsArray) {
                    String pathIndex = pathArr[i].substring(pathArr[i].lastIndexOf("[") + 1, pathArr[i].length() - 1);
                    String ignoreIndex = ignorePathArr[i].substring(ignorePathArr[i].lastIndexOf("[") + 1, ignorePathArr[i].length() - 1);
                    if (!pathIndex.equals(ignoreIndex) && !ignoreIndex.equals("*")) {
                        return false;
                    }

                    nodePath = nodePath.substring(0, nodePath.length() - pathIndex.length() - 2);
                    ignoreNodeArr = ignoreNodeArr.substring(0, ignoreNodeArr.length() - ignoreIndex.length() - 2);
                }

                if (!nodePath.equals(ignoreNodeArr)) {
                    return false;
                }
            }

            return true;
        }
    }

    private void logInequality(String valueA, String valueB, String currentPath) {
        log.info("7");
        this.inequalityMessages.add(currentPath + " values were not the same! Expected " + valueA + " but got " + valueB);
        InequalityDTO dto = new InequalityDTO(currentPath, valueA, valueB, "value");
        this.inequalitys.add(dto);
    }

    private static String getChildPath(String currentPath, String childName) {
        return currentPath + "." + childName;
    }

    private static boolean childIsObject(LazyObject parent, String fieldName) {
        return parent.getType(fieldName) == LazyType.OBJECT;
    }

    private static boolean childIsObject(LazyArray parent, int index) {
        return parent.getType(index) == LazyType.OBJECT;
    }

    private static boolean childIsArray(LazyObject parent, String fieldName) {
        return parent.getType(fieldName) == LazyType.ARRAY;
    }

    private static boolean childIsArray(LazyArray parent, int index) {
        return parent.getType(index) == LazyType.ARRAY;
    }

    public static void setDebugMode(boolean debugMode) {
        JsonEquals.debugMode = debugMode;
    }
}
