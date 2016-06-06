package com.ascbank.client;

import com.ascbank.model.SystemInfo;
import com.ascbank.model.SystemInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    int countByExample(SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    int deleteByExample(SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    int insert(SystemInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    int insertSelective(SystemInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    List<SystemInfo> selectByExample(SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    SystemInfo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    int updateByExampleSelective(@Param("record") SystemInfo record, @Param("example") SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    int updateByExample(@Param("record") SystemInfo record, @Param("example") SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    int updateByPrimaryKeySelective(SystemInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated Mon Jun 06 16:44:46 CST 2016
     */
    int updateByPrimaryKey(SystemInfo record);
}