package cn.sunline.compare.utils.analysis;

import cn.sunline.compare.dto.input.AnalysisResultDTO;

import java.io.File;

/**
 * <h3>compare-util</h3>
 *
 * <p>文件解析接口</p>
 *
 * @Author zcz
 * @Date 2020-07-02 17:58
 */
public interface FileAnalysisStrategy {


    /**
     * @author zcz
     * @description 解析文件
     * @date 2020-07-02
     *
     * @method analysis
     * @param file 解析的文件
     * @return java.lang.Object
     */
    AnalysisResultDTO analysis(File file);


}
