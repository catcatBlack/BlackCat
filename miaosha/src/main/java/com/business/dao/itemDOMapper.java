package com.business.dao;

import com.business.dataObject.itemDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface itemDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Sat Aug 21 16:37:52 GMT+08:00 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Sat Aug 21 16:37:52 GMT+08:00 2021
     */
    int insert(itemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Sat Aug 21 16:37:52 GMT+08:00 2021
     */
    int insertSelective(itemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Sat Aug 21 16:37:52 GMT+08:00 2021
     */
    itemDO selectByPrimaryKey(Integer id);
    List<itemDO> listItem();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Sat Aug 21 16:37:52 GMT+08:00 2021
     */
    int updateByPrimaryKeySelective(itemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Sat Aug 21 16:37:52 GMT+08:00 2021
     */
    int updateByPrimaryKey(itemDO record);

    int increaseSales(@Param("id") Integer id, @Param("amount") Integer amount);

}