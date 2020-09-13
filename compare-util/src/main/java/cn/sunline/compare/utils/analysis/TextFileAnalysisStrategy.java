package cn.sunline.compare.utils.analysis;

import cn.sunline.compare.config.UnifyException;
import cn.sunline.compare.contant.GlobalProperties;
import cn.sunline.compare.contant.RCode;
import cn.sunline.compare.dao.ApiSetMapper;
import cn.sunline.compare.dto.input.AnalysisFailureDTO;
import cn.sunline.compare.dto.input.AnalysisResultDTO;
import cn.sunline.compare.entity.UnifyRequestAnalysis;
import cn.sunline.compare.entity.ApiSet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h3>compare-util</h3>
 *
 * <p>文本文件解析</p>
 *
 * @Author zcz
 * @Date 2020-07-02 18:00
 */
@Component("textFileAnalysis")
public class TextFileAnalysisStrategy implements FileAnalysisStrategy {

    @Autowired
    private ApiSetMapper apiSetMapper;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @author zcz
     * @description 解析文本文件
     * @date 2020-07-02
     *
     * @method analysis
     * @param file 解析的文件
     * @return java.lang.Object
     */
    @Override
    public AnalysisResultDTO analysis(File file) {
        AnalysisResultDTO result = new AnalysisResultDTO();
        List<UnifyRequestAnalysis> dtos = new ArrayList<>();
        List<AnalysisFailureDTO> failureDTOS = new ArrayList<>();
        ApiSet apiSet = apiSetMapper.selectFirst();
        if (apiSet == null ) {
            file.deleteOnExit();
            throw new UnifyException("ApiHost 没有配置", RCode.ERROR_TXT_ANALYSIS);
        }

        int lineNum = 0;
        FileReader fileReader = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while((line=reader.readLine()) != null && !"".equals(line.trim())){
                String[] strs = line.split(GlobalProperties.SPLIT);
                UnifyRequestAnalysis dto = null;
                AnalysisFailureDTO failureDTO = null;
                try {
                    dto= assignment(strs, apiSet);
                    dtos.add(dto);
                }catch (UnifyException e){
                    failureDTO = new AnalysisFailureDTO();
                    failureDTO.setLineNum(lineNum);
                    failureDTO.setFlowInfo(line);
                    failureDTO.setCause(e.getMessage());
                    failureDTOS.add(failureDTO);
                }
                lineNum++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new UnifyException("文本不存在异常", RCode.ERROR_TXT_ANALYSIS);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnifyException("文本readLine()异常", RCode.ERROR_TXT_ANALYSIS);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new UnifyException("文本流close()异常", RCode.ERROR_TXT_ANALYSIS);
                }
            }
        }

        result.setTotal(lineNum);
        result.setSuccessCount(dtos.size());
        result.setFailureCount(failureDTOS.size());
        result.setFailureDTOS(failureDTOS);
        result.setUnifyRequestAnalysisDTOS(dtos);
        return result;
    }

    private UnifyRequestAnalysis assignment(String[] strs, ApiSet apiSet) {
        UnifyRequestAnalysis dto = new UnifyRequestAnalysis();
        //交易名
        dto.setTransName(strs[0]);
        //流水号
        dto.setFlowNum(strs[1]);
        //通信协议
        dto.setProtocol(strs[2]);
        //地址
        dto.setNewHost(apiSet.getNewHost());
        dto.setOldHost(apiSet.getOldHost());
        //端口号
        dto.setNewPort(apiSet.getNewPort());
        dto.setOldPort(apiSet.getOldPort());

        //路径
        dto.setPath(strs[3]);
        //请求头
        if (strs[4] != null && !"".equals(strs[4])) {
            try {
                objectMapper.readValue(strs[4], Map.class);
            } catch (JsonProcessingException e) {
                throw new UnifyException("请求头格式不正确", RCode.ERROR_TXT_ANALYSIS);
            }
        }
        dto.setHeaders(strs[4]);

        if (GlobalProperties.HTTP_METHODS.contains(strs[5].toUpperCase())) {
            dto.setMethod(strs[5].toUpperCase());
        }

        //请求体
        if (strs.length<=6 || strs[6] == null || "".equals(strs[6])) {
            dto.setBody("");
        }else {
            dto.setBody(strs[6]);
        }
        //请求体

        if (strs.length<=7 || strs[7] == null || "".equals(strs[7])) {
            dto.setResponse("");
        }else {
            dto.setResponse(strs[7]);
        }

        //校验
        if (dto.getTransName() == null || "".equals(dto.getTransName())){
            throw new UnifyException("交易名不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getFlowNum() == null || "".equals(dto.getFlowNum())){
            throw new UnifyException("流水号不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getProtocol() == null || "".equals(dto.getProtocol())){
            throw new UnifyException("通信协议不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getNewHost() == null || "".equals(dto.getNewHost())){
            throw new UnifyException("newHost不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getOldHost() == null || "".equals(dto.getOldHost())){
            throw new UnifyException("oldHost不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getNewPort() == null || "".equals(dto.getNewPort())){
            throw new UnifyException("new端口号不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getOldPort() == null || "".equals(dto.getOldPort())){
            throw new UnifyException("old端口号不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getPath() == null || "".equals(dto.getPath())){
            throw new UnifyException("路径不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getMethod() == null || "".equals(dto.getMethod())){
            throw new UnifyException("请求方法不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getHeaders() == null || "".equals(dto.getHeaders())){
            throw new UnifyException("请求头不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getBody()== null){
            throw new UnifyException("请求体不存在", RCode.ERROR_TXT_ANALYSIS);
        }
        if (dto.getResponse()== null){
            throw new UnifyException("响应体不存在", RCode.ERROR_TXT_ANALYSIS);
        }

        return dto;
    }
}
