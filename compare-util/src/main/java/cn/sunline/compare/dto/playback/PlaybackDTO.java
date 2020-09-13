package cn.sunline.compare.dto.playback;

import lombok.Data;

import java.util.List;

/**
 * <h3>compare-util</h3>
 *
 * <p>请求回放返回对象</p>
 *
 * @Author zcz
 * @Date 2020-07-06 10:21
 */

@Data
public class PlaybackDTO {

    /**
     * 处理交易数
     */
    private Integer businessCount;

    /**
     * 成功数
     */
    private Integer successCount;

    /**
     * 失败数
     */
    private Integer failureCount;

    /**
     * 请求信息
     */
    private List<RequestDTO> requestDtos;
}
