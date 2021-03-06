package cn.gou23.shop.dao;

import cn.gou23.shop.entity.SourceOwnerEntity;
import cn.gou23.shop.entity.SourceOwnerEntityCondition;
import cn.gou23.shop.model.SourceOwnerModel;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SourceOwnerEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int countByExample(SourceOwnerEntityCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int deleteByExample(SourceOwnerEntityCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int insert(SourceOwnerEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int insertSelective(SourceOwnerEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    List<SourceOwnerModel> selectByExampleWithBLOBsWithRowbounds(SourceOwnerEntityCondition example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    List<SourceOwnerModel> selectByExampleWithBLOBs(SourceOwnerEntityCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    List<SourceOwnerModel> selectByExampleWithRowbounds(SourceOwnerEntityCondition example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    List<SourceOwnerModel> selectByExample(SourceOwnerEntityCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    SourceOwnerModel selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SourceOwnerEntity record, @Param("example") SourceOwnerEntityCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") SourceOwnerEntity record, @Param("example") SourceOwnerEntityCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SourceOwnerEntity record, @Param("example") SourceOwnerEntityCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SourceOwnerEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(SourceOwnerEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table source_owner
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SourceOwnerEntity record);
}