package cn.sunline.compare.dao;

import cn.sunline.compare.entity.CompareDiff;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompareDiffMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompareDiff record);

    int insertBatch(@Param("diffs") List<CompareDiff> diffs);

    int insertSelective(CompareDiff record);

    CompareDiff selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompareDiff record);

    int updateByPrimaryKey(CompareDiff record);

    int truncate();
}