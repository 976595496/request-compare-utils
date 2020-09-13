package cn.sunline.compare.contant;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>compare-util</h3>
 *
 * <p>文件类型枚举</p>
 *
 * @Author zcz
 * @Date 2020-07-02 17:24
 */
public enum FileTypeEnum {
    FILE_TYPE_ENUM_TXT("文本"),
    FILE_TYPE_ENUM_EXCEL("excel"),
    FILE_TYPE_ENUM_BAINARY("二进制");

    private String name;

    public String getName() {
        return name;
    }


    FileTypeEnum(String name) {
        this.name = name;
    }

    public static Map<String, String> enumMap(){
        Map<String, String> map = new HashMap<>();
        for (FileTypeEnum e : FileTypeEnum.values()) {
            map.put(e.toString(), e.getName());
        }
        return map;
    }


}
