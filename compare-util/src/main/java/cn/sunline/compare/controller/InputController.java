package cn.sunline.compare.controller;

import cn.sunline.compare.contant.FileTypeEnum;
import cn.sunline.compare.contant.R;
import cn.sunline.compare.dto.input.AnalysisResultDTO;
import cn.sunline.compare.service.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

/**
 * <h3>compare-util</h3>
 *
 * <p>录入控制器</p>
 *
 * @Author zcz
 * @Date 2020-07-02 17:12
 */
@RestController
public class InputController {

    @Autowired
    private InputService inputService;


    /**
     * @author zcz
     * @description 准备数据 返回文件类型枚举的 map, 文件类型区分上传生成的文件的类型, 为后面文件解析提供策略支持
     * @date 2020-07-02
     *
     * @method getFileType
     * @param
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @GetMapping("/upload/file/types")
    public Map<String, String> getFileType(){

        return FileTypeEnum.enumMap();
    }


    @PostMapping("/upload/file")
    public R<AnalysisResultDTO> inputFile(@RequestParam("file") MultipartFile file,
                                          @RequestParam(value = "type", required = false) String type) {

        FileTypeEnum fileTypeEnum = null;
        if (type == null || "".equals(type)) {
            fileTypeEnum = FileTypeEnum.FILE_TYPE_ENUM_TXT;
        } else {
            fileTypeEnum = FileTypeEnum.valueOf(type);
        }
        System.out.println(fileTypeEnum);

        //录入文件
        AnalysisResultDTO result = inputService.input(file, fileTypeEnum);

        return R.ok().data(result);
    }
}
