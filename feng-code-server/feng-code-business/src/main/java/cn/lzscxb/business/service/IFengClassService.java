package cn.lzscxb.business.service;

import java.util.List;
import cn.lzscxb.business.domain.FengClass;

/**
 * 班级管理Service接口
 * 
 * @author Likfees
 * @date 2022-10-14
 */
public interface IFengClassService 
{
    /**
     * 查询班级管理
     * 
     * @param id 班级管理主键
     * @return 班级管理
     */
    public FengClass selectFengClassById(Long id);

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
     * 批量删除班级管理
     * 
     * @param ids 需要删除的班级管理主键集合
     * @return 结果
     */
    public int deleteFengClassByIds(Long[] ids);

    /**
     * 删除班级管理信息
     * 
     * @param id 班级管理主键
     * @return 结果
     */
    public int deleteFengClassById(Long id);
}
