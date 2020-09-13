package cn.sunline.compare.dao;

import cn.sunline.compare.entity.PositionFlag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionFlagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PositionFlag record);

    int insertSelective(PositionFlag record);

    int updateByPrimaryKeySelective(PositionFlag record);

    int updateByPrimaryKey(PositionFlag record);

    int updatePositionByFlag(@Param("position") int position, @Param("flag") String flag);

    PositionFlag selectByPrimaryKey(Integer id);

    PositionFlag selectByFlag(@Param("flag") String flag);
}