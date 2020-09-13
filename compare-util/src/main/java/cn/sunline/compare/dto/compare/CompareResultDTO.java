package cn.sunline.compare.dto.compare;

import cn.sunline.compare.entity.CompareDiff;
import lombok.Data;

import java.util.List;

/**
 * <h3>compare-util</h3>
 *
 * <p>比较结果</p>
 *
 * @Author zcz
 * @Date 2020-07-09 18:41
 */
@Data
public class CompareResultDTO {
    private Integer diffCount = 0;
    private Integer errorCount = 0;
    private List<CompareDiff> compareDiffs;
}
