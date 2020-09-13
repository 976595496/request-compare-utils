package cn.sunline.compare.contant;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * <h3>compare-util</h3>
 *
 * <p>全局配置属性</p>
 *
 * @Author zcz
 * @Date 2020-07-08 12:35
 */
@ConfigurationProperties("global.properties")
@Component
@Data
public class GlobalProperties implements InitializingBean {

    public static final List<String> HTTP_METHODS =
            Arrays.asList("GET", "POST", "HEAD", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE");


    public static final String PLAYBACK_FLG = "playback_flg";
    public static final String COMPARE_FLG = "compare_flg";


    public static final String PLAYBACK_MODE_DOUBLE = "double";
    public static final String PLAYBACK_MODE_SINGLE = "single";

    public static final String COMPARE_CONTENT_KEY = "key";
    public static final String COMPARE_CONTENT_VALUE = "value";

    public static String SPLIT;
    public static String TEMP_DIR;

    public static String PLAYBACK_MODE;

    public static String COMPARE_CONTENT;

    /*文件解析分隔符*/
    private String split = "~";
    /*文件解析分隔符*/
    private String temp;
    /*回放模式 单系统 双系统, 默认双系统 */
    private String playbackMode = PLAYBACK_MODE_DOUBLE;
    /*比对内容 比对结构, 比对值, 默认比对值 */
    private String compareContent = COMPARE_CONTENT_VALUE;



    @Override
    public void afterPropertiesSet() throws Exception {
        SPLIT = split;
        TEMP_DIR = temp;
        PLAYBACK_MODE = playbackMode;
        COMPARE_CONTENT = compareContent;

    }
}
