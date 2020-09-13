package cn.sunline.compare.service;

import cn.sunline.compare.dto.compare.CompareResultDTO;
import cn.sunline.compare.entity.CompareDiff;
import cn.sunline.compare.utils.jsonequals.InequalityDTO;

import java.util.List;
import java.util.Map;

/**
 * <h3>compare-util</h3>
 *
 * <p>比对服务层接口</p>
 *
 * @Author zcz
 * @Date 2020-07-03 11:11
 */
public interface CompareService {

    CompareResultDTO compare();
}
