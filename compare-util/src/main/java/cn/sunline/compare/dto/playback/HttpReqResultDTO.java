package cn.sunline.compare.dto.playback;

import lombok.Data;

/**
 * <h3>compare-util</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-07-22 11:32
 */
@Data
public class HttpReqResultDTO {
    //返回 json
    private String json;
    //是否请求成功
    private Short successFlg;
}
