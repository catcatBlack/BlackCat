package com.business.dao;

import com.business.dataObject.promoDO;

public interface promoDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Wed Aug 25 18:28:04 GMT+08:00 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Wed Aug 25 18:28:04 GMT+08:00 2021
     */
    int insert(promoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Wed Aug 25 18:28:04 GMT+08:00 2021
     */
    int insertSelective(promoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Wed Aug 25 18:28:04 GMT+08:00 2021
     */
    promoDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Wed Aug 25 18:28:04 GMT+08:00 2021
     */
    int updateByPrimaryKeySelective(promoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo
     *
     * @mbg.generated Wed Aug 25 18:28:04 GMT+08:00 2021
     */
    int updateByPrimaryKey(promoDO record);
}