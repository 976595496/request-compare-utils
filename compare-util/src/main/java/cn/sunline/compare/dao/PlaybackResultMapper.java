package cn.sunline.compare.dao;

import cn.sunline.compare.entity.PlaybackResult;
import cn.sunline.compare.entity.UnifyRequestAnalysis;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaybackResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlaybackResult record);

    int insertSelective(PlaybackResult record);

    int insertBatch(@Param("playbackResults") List<PlaybackResult> playbackResults);

    PlaybackResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlaybackResult record);

    int updateByPrimaryKeyWithBLOBs(PlaybackResult record);

    int updateByPrimaryKey(PlaybackResult record);

    int truncate();

    List<PlaybackResult> selectAfterId(Integer id);
}