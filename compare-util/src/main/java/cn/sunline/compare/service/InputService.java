package cn.sunline.compare.service;

import cn.sunline.compare.contant.FileTypeEnum;
import cn.sunline.compare.dto.input.AnalysisResultDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <h3>compare-util</h3>
 *
 * <p>录入 service 层</p>
 *
 * @Author zcz
 * @Date 2020-07-02 17:15
 */
public interface InputService {

    /**
     * @author zcz
     * @description 录入文件服务
     * @date 2020-07-02
     *
     * @method input
     * @param file
     * @param fileTypeEnum
     * @return java.lang.Object
     */
    AnalysisResultDTO input(MultipartFile file, FileTypeEnum fileTypeEnum);
}
