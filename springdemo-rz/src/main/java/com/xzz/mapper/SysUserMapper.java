package com.xzz.mapper;

import com.xzz.entity.SysUser;
import com.xzz.entity.SysUserExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int countByExample(SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int deleteByExample(SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int insert(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int insertSelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    List<SysUser> selectByExample(SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    SysUser selectByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int updateByPrimaryKey(SysUser record);

    List<SysUser> findLockAccount();

    int unLockAccount(SysUser sysUser);

    List<Map<String,Object>>  findPieData();

    List<Map<String,Object>> findBarData();


}