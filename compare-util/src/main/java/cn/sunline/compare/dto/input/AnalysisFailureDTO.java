package cn.sunline.compare.dto.input;

import lombok.Data;

/**
 * <h3>compare-util</h3>
 *
 * <p>解析失败对象</p>
 *
 * @Author zcz
 * @Date 2020-07-08 18:26
 */
@Data
public class AnalysisFailureDTO {
    /**
     * 失败行号
     */
    private Integer lineNum;

    /**
     * 流水内容
     */
    private String flowInfo;

    /**
     * 失败原因
     */
    private String cause;
}
