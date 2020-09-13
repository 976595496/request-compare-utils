package cn.sunline.compare.dto.playback;

import lombok.Data;

/**
 * <h3>compare-util</h3>
 *
 * <p>交易回放请求信息</p>
 *
 * @Author zcz
 * @Date 2020-07-06 10:25
 */
@Data
public class RequestDTO {

    /**
     * 流水号
     */
    private String flowNum;

    /**
     * 来源 新系统/旧系统
     */
    private String source;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求报文
     */
    private String requestMsg;

    /**
     * 响应报文
     */
    private String responseMsg;

}
