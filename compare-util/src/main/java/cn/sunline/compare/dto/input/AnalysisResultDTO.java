package cn.sunline.compare.dto.input;

import cn.sunline.compare.entity.UnifyRequestAnalysis;
import lombok.Data;

import java.util.List;

/**
 * <h3>compare-util</h3>
 *
 * <p>解析结果</p>
 *
 * @Author zcz
 * @Date 2020-07-08 18:38
 */
@Data
public class AnalysisResultDTO {
    /**
     * 解析总数
     */
    private Integer total;

    /**
     * 成功数
     */
    private Integer successCount;

    /**
     * 失败数
     */
    private Integer failureCount;

    /**
     * 失败内容
     */
    private List<AnalysisFailureDTO> failureDTOS;

    /**
     * 解析结果
     */
    private transient List<UnifyRequestAnalysis> unifyRequestAnalysisDTOS;
}
