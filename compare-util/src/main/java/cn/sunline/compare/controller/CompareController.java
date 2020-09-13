package cn.sunline.compare.controller;

import cn.sunline.compare.contant.R;
import cn.sunline.compare.dto.compare.CompareResultDTO;
import cn.sunline.compare.entity.CompareDiff;
import cn.sunline.compare.service.CompareService;
import cn.sunline.compare.utils.jsonequals.InequalityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <h3>compare-util</h3>
 *
 * <p>比对控制器</p>
 *
 * @Author zcz
 * @Date 2020-07-03 11:05
 */
@RestController
public class CompareController {

    @Autowired
    private CompareService compareService;

    /**
     * @author zcz
     * @description 比对结果
     * @date 2020-07-03
     *
     * @method compare
     * @param
     * @return java.lang.Object
     */
    @PostMapping("/compare")
    public R<CompareResultDTO> compare(){

        return R.ok().data(compareService.compare());
    }
}
