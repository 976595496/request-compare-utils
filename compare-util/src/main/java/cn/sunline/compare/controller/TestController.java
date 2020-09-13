package cn.sunline.compare.controller;

import cn.sunline.compare.contant.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.Map;

/**
 * <h3>compare-util</h3>
 *
 * <p>测试接口</p>
 *
 * @Author zcz
 * @Date 2020-07-09 13:20
 */
@RestController
public class TestController {

    @PostMapping("/test")
    public R test(@RequestBody Map<String, Object> vo){
        return R.ok().data(vo);
    }

}
