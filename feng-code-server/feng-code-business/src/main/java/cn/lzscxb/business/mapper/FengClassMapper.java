package cn.lzscxb.business.mapper;

import cn.lzscxb.domain.entity.FengClass;

import java.util.List;

/**
 * 班级管理Mapper接口
 * 
 * @author Likfees
 * @date 2022-10-14
 */
public interface FengClassMapper 
{
    /**
     * 查询班级管理
     * 
     * @param id 班级管理主键
     * @return 班级管理
     */
    public FengClass selectFengClassById(Long id);

    /**
     * 通过学院id查询班级列表
     *
     * @param collegeId 学院主键
     * @return 班级管理
     */
    public List<FengClass> selectFengClassListByCollegeId(Long collegeId);

    /**
     * 查询班级管理列表
     * 
     * @param fengClass 班级管理
     * @return 班级管理集合
     */
    public List<FengClass> selectFengClassList(FengClass fengClass);

    /**
     * 新增班级管理
     * 
     * @param fengClass 班级管理
     * @return 结果
     */
    public int insertFengClass(FengClass fengClass);

    /**
     * 修改班级管理
     * 
     * @param fengClass 班级管理
     * @return 结果
     */
    public int updateFengClass(FengClass fengClass);

    /**
     * 删除班级管理
     * 
     * @param id 班级管理主键
     * @return 结果
     */
    public int deleteFengClassById(Long id);

    /**
     * 批量删除班级管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengClassByIds(Long[] ids);
}
