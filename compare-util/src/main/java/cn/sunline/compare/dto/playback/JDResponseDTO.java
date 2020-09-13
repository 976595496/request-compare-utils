package cn.sunline.compare.dto.playback;

import lombok.Data;

import java.util.Map;

/**
 * <h3>compare-util</h3>
 *
 * <p>京东请求返回结果</p>
 *
 * @Author zcz
 * @Date 2020-07-06 10:45
 */
@Data
public class JDResponseDTO {

    /**
     * 公共返回参数
     */
    private String responseNo;
    private String responseTime;
    private String channelCode;
    private String version;
    private String code;
    private String msg;
    /**
     * 返回结果数据
     */
    private Map<String, Object> responseData;


}
