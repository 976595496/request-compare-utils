package cn.sunline.compare.controller;

import cn.sunline.compare.contant.R;
import cn.sunline.compare.dto.playback.PlaybackDTO;
import cn.sunline.compare.service.PlaybackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>compare-util</h3>
 *
 * <p>交易回放控制器</p>
 *
 * @Author zcz
 * @Date 2020-07-03 10:00
 */

@RestController
public class PlayBackController {

    @Autowired
    private PlaybackService playbackService;

    /**
     * @author zcz
     * @description 回放交易请求, 并存库
     * @date 2020-07-06
     *
     * @method playBack
     * @param
     * @return java.lang.Object
     */
    @PostMapping("/play/back")
    public R<PlaybackDTO> playBack(){

        PlaybackDTO playback = playbackService.playback();

        return R.ok().data(playback);
    }

}
