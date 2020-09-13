package cn.sunline.compare.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * playback_result
 * @author 
 */
@Data
public class PlaybackResult implements Serializable {
    /**
     * 主键
     */
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
     * 成功 1 成功 2 失败
     */
    private Short success;

    /**
     * 新系统返回 json
     */
    private String newJson;

    /**
     * 旧系统返回 json
     */
    private String oldJson;

    private static final long serialVersionUID = 1L;

}