package cn.sunline.compare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <h3>compare-util</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-07-02 16:19
 */
@Controller
public class LoadController {

    @RequestMapping("/")
    public String index() {
        return "indexssss";
    }
}
