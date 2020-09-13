package cn.sunline.compare.entity;

import lombok.Data;

/**
 * <h3>compare-util</h3>
 *
 * <p>统一请求解析数据对象: 无论原来请求的方式, 请求的格式是什么样的, 解析成统一的对象类型, 标记原本请求类型, 在具体的回放时再做请求适配 转换为原本的请求数据</p>
 *
 * @Author zcz
 * @Date 2020-07-07 09:45
 */
@Data
public class UnifyRequestAnalysis {
    private Integer id;

    /**
     * 交易名
     */
    private String transName;

    /**
     * 流水号
     */
    private String flowNum;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 地址
     */
    private String newHost;

    /**
     * 端口号
     */
    private String newPort;

    /**
     * 地址
     */
    private String oldHost;

    /**
     * 端口号
     */
    private String oldPort;

    /**
     * 路径
     */
    private String path;

    /**
     * 请求头
     */
    private String headers;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求体
     */
    private String body;

    /**
     * 响应体
     */
    private String response;


}
