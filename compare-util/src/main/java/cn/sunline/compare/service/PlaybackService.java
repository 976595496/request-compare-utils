package cn.sunline.compare.service;

import cn.sunline.compare.dto.playback.PlaybackDTO;

/**
 * <h3>compare-util</h3>
 *
 * <p>回放服务接口层</p>
 *
 * @Author zcz
 * @Date 2020-07-03 10:14
 */
public interface PlaybackService {
    /**
     * @author zcz
     * @description 比对系统回放的的交易
     * @date 2020-07-03
     *
     * @method
     * @return
     */
    PlaybackDTO playback();

}
