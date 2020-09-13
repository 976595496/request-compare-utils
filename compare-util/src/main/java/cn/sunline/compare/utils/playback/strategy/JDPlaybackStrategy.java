package cn.sunline.compare.utils.playback.strategy;

import cn.sunline.compare.utils.playback.PlaybackStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * <h3>compare-util</h3>
 *
 * <p>京东请求回放策略</p>
 *
 * @Author zcz
 * @Date 2020-07-06 10:49
 */
@Component
public class JDPlaybackStrategy implements PlaybackStrategy<Object> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public String play(Object obj, String url) {

        return null;
    }
}
