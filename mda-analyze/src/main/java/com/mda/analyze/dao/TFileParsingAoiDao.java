package com.mda.analyze.dao;

import com.mda.analyze.model.TFileParsingAoi;
import com.mda.analyze.model.TFileParsingAoiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFileParsingAoiDao {
    long countByExample(TFileParsingAoiExample example);

    int deleteByExample(TFileParsingAoiExample example);

    int deleteByPrimaryKey(String serialNo);

    int insert(TFileParsingAoi record);

    int insertSelective(TFileParsingAoi record);

    List<TFileParsingAoi> selectByExample(TFileParsingAoiExample example);

    TFileParsingAoi selectByPrimaryKey(String serialNo);

    int updateByExampleSelective(@Param("record") TFileParsingAoi record, @Param("example") TFileParsingAoiExample example);

    int updateByExample(@Param("record") TFileParsingAoi record, @Param("example") TFileParsingAoiExample example);

    int updateByPrimaryKeySelective(TFileParsingAoi record);

    int updateByPrimaryKey(TFileParsingAoi record);
}