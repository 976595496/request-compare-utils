package cn.sunline.compare.utils.playback.adapter;

import cn.sunline.compare.entity.UnifyRequestAnalysis;
import cn.sunline.compare.utils.playback.PlaybackAdapter;
import cn.sunline.compare.utils.playback.strategy.HttpJsonPlaybackStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <h3>compare-util</h3>
 *
 * <p>httpjson 请求适配器</p>
 *
 * @Author zcz
 * @Date 2020-07-07 15:09
 */
@Component
public class HttpJsonPlaybackAdapter implements PlaybackAdapter<UnifyRequestAnalysis> {

    @Autowired
    private HttpJsonPlaybackStrategy httpJsonPlaybackStrategy;

    @Override
    public UnifyRequestAnalysis requestAdapt(UnifyRequestAnalysis obj) {
        return obj;
    }

    @Override
    public String responseAdapt(Object obj) {
        return (String) obj;
    }

    @Override
    public String play(UnifyRequestAnalysis obj, String url) {
        String result = httpJsonPlaybackStrategy.play(obj, url);
        return responseAdapt(result);
    }

    public HttpJsonPlaybackAdapter(HttpJsonPlaybackStrategy httpJsonPlaybackStrategy) {
        this.httpJsonPlaybackStrategy = httpJsonPlaybackStrategy;
    }
}
