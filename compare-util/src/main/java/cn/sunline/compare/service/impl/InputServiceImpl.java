package cn.sunline.compare.service.impl;

import cn.sunline.compare.contant.FileTypeEnum;
import cn.sunline.compare.contant.GlobalProperties;
import cn.sunline.compare.dao.UnifyRequestAnalysisMapper;
import cn.sunline.compare.dto.input.AnalysisResultDTO;
import cn.sunline.compare.entity.UnifyRequestAnalysis;
import cn.sunline.compare.service.InputService;
import cn.sunline.compare.utils.analysis.FileAnalysisStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h3>compare-util</h3>
 *
 * <p>录入文件 service 层</p>
 *
 * @Author zcz
 * @Date 2020-07-02 17:15
 */
@Service
public class InputServiceImpl implements InputService {
    private final String TEXT_DIR="txt";

    @Qualifier("textFileAnalysis")
    @Autowired
    private FileAnalysisStrategy fileAnalysis;


    @Autowired
    private UnifyRequestAnalysisMapper unifyRequestAnalysisMapper;


    /**
     * @author zcz
     * @description 录入文件服务, 调用解析工具解析文件
     * @date 2020-07-02
     *
     * @method input
     * @param file
     * @param fileTypeEnum
     * @return java.lang.Object
     */
    @Override
    public AnalysisResultDTO input(MultipartFile file, FileTypeEnum fileTypeEnum) {
        //解析文件
        AnalysisResultDTO result = analysis(file, fileTypeEnum);
        List<UnifyRequestAnalysis> requestAnalysisDTOs = result.getUnifyRequestAnalysisDTOS();
        if (result.getFailureDTOS().size()<=0) {
            //对解析结果对象操作
            int count = unifyRequestAnalysisMapper.insertBatch(requestAnalysisDTOs);
        }

        return result;
    }


    //解析文件
    private AnalysisResultDTO analysis(MultipartFile multipartFile, FileTypeEnum fileTypeEnum){

        try {
            //返回结果
            AnalysisResultDTO result = null;
            switch (fileTypeEnum){
                case FILE_TYPE_ENUM_TXT:
                    File file = new File(GlobalProperties.TEMP_DIR+File.separator+LocalDateTime.now().toString()+".txt");

                    if (!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();//创建父级文件路径
                        file.createNewFile();//创建文件
                    }
                    multipartFile.transferTo(file);

                    result = fileAnalysis.analysis(file);
                    file.delete();
                    break;
                case FILE_TYPE_ENUM_EXCEL:
                    System.out.println("excel");
                    break;
                case FILE_TYPE_ENUM_BAINARY:
                    System.out.println("bainary");
                    break;
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
            //IO 文件异常 返回 null
            return null;
        }
    }
}
