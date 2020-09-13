package cn.sunline.compare.dao;

import cn.sunline.compare.entity.ApiSet;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApiSet record);

    int insertSelective(ApiSet record);

    ApiSet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApiSet record);

    int updateByPrimaryKeyWithBLOBs(ApiSet record);

    int updateByPrimaryKey(ApiSet record);

    ApiSet selectFirst();
}