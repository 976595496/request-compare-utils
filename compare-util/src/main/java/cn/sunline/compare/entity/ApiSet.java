package cn.sunline.compare.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * api_set
 * @author 
 */
@Data
public class ApiSet implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 新系统地址
     */
    private String newHost;

    /**
     * 新系统端口
     */
    private String newPort;

    /**
     * 旧系统地址
     */
    private String oldHost;

    /**
     * 旧系统端口
     */
    private String oldPort;

    /**
     * 不比对的交易名
     */
    private String noTransName;

    /**
     * 新系统公共参数
     */
    private String newPubParams;

    /**
     * 旧系统公共参数
     */
    private String oldPubParam;

    private static final long serialVersionUID = 1L;

}