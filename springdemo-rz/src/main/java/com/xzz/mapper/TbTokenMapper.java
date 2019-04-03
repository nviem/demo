package com.xzz.mapper;

import com.xzz.entity.TbToken;
import com.xzz.entity.TbTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbTokenMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int countByExample(TbTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int deleteByExample(TbTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int insert(TbToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int insertSelective(TbToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    List<TbToken> selectByExample(TbTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    TbToken selectByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int updateByExampleSelective(@Param("record") TbToken record, @Param("example") TbTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int updateByExample(@Param("record") TbToken record, @Param("example") TbTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int updateByPrimaryKeySelective(TbToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_token
     *
     * @mbggenerated Mon Mar 25 21:06:34 CST 2019
     */
    int updateByPrimaryKey(TbToken record);
}