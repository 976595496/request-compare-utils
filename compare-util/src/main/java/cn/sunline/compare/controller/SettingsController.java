package cn.sunline.compare.controller;

import cn.sunline.compare.contant.R;
import cn.sunline.compare.service.SettingService;
import cn.sunline.compare.vo.ApiSettingsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <h3>compare-util</h3>
 *
 * <p>系统设置控制器</p>
 *
 * @Author zcz
 * @Date 2020-07-02 18:25
 */
@RestController
public class SettingsController {

    @Autowired
    private SettingService settingService;

    /**
     * @author zcz
     * @description 系统设置 设置新旧系统 API 和 公共参数
     * @date 2020-07-03
     *
     * @method apiSettings
     * @param vo
     * @return java.lang.String
     */
    @PostMapping("/settings")
    public R apiSettings(@RequestBody @Valid ApiSettingsVO vo){

        settingService.save(vo);

        return R.ok();
    }

    @GetMapping("/get/settings")
    public R<ApiSettingsVO> getApiSettings(){
        return R.ok().data(settingService.get());
    }


    /**
     * @author zcz
     * @description 清空数据
     * @date 2020-07-03
     *
     * @method apiSettings
     * @param type 清空数据类型  1 清空流水信息  2 清空回放信息
     * @return java.lang.String
     */
    @PostMapping("/settings/clear/{type}")
    public R apiSettings(@PathVariable Short type){
        settingService.clearTable(type);
        return R.ok();
    }
}
