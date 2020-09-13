package cn.sunline.compare.dao;

import cn.sunline.compare.entity.UnifyRequestAnalysis;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnifyRequestAnalysisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UnifyRequestAnalysis record);

    int insertSelective(UnifyRequestAnalysis record);

    int insertBatch(@Param("dtos") List<UnifyRequestAnalysis> dtos);

    int updateByPrimaryKeySelective(UnifyRequestAnalysis record);

    int updateByPrimaryKeyWithBLOBs(UnifyRequestAnalysis record);

    int updateByPrimaryKey(UnifyRequestAnalysis record);

    int truncate();

    UnifyRequestAnalysis selectByPrimaryKey(Integer id);

    List<UnifyRequestAnalysis> selectAfterId(@Param("id") Integer id);

}