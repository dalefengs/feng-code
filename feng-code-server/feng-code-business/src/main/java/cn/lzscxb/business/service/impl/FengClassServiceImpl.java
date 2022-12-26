package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.domain.entity.FengClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengClassMapper;
import cn.lzscxb.business.service.IFengClassService;

/**
 * 班级管理Service业务层处理
 * 
 * @author Likfees
 * @date 2022-10-14
 */
@Service
public class FengClassServiceImpl implements IFengClassService 
{
    @Autowired
    private FengClassMapper fengClassMapper;

    /**
     * 查询班级管理
     * 
     * @param id 班级管理主键
     * @return 班级管理
     */
    @Override
    public FengClass selectFengClassById(Long id)
    {
        return fengClassMapper.selectFengClassById(id);
    }

    /**
     * 通过学院id查询班级列表
     *
     * @param collegeId 学院主键
     * @return 班级管理
     */
    @Override
    public List<FengClass> selectFengClassListByCollegeId(Long collegeId) {
        return fengClassMapper.selectFengClassListByCollegeId(collegeId);
    }

    /**
     * 查询班级管理列表
     * 
     * @param fengClass 班级管理
     * @return 班级管理
     */
    @Override
    public List<FengClass> selectFengClassList(FengClass fengClass)
    {
        return fengClassMapper.selectFengClassList(fengClass);
    }


    @Override
    public List<FengClass> selectFengClassListByTaskId(Long taskId) {
        return fengClassMapper.selectClassListByTaskId(taskId);
    }

    /**
     * 新增班级管理
     * 
     * @param fengClass 班级管理
     * @return 结果
     */
    @Override
    public int insertFengClass(FengClass fengClass)
    {
        fengClass.setCreateTime(DateUtils.getNowDate());
        return fengClassMapper.insertFengClass(fengClass);
    }

    /**
     * 修改班级管理
     * 
     * @param fengClass 班级管理
     * @return 结果
     */
    @Override
    public int updateFengClass(FengClass fengClass)
    {
        fengClass.setUpdateTime(DateUtils.getNowDate());
        return fengClassMapper.updateFengClass(fengClass);
    }

    /**
     * 批量删除班级管理
     * 
     * @param ids 需要删除的班级管理主键
     * @return 结果
     */
    @Override
    public int deleteFengClassByIds(Long[] ids)
    {
        return fengClassMapper.deleteFengClassByIds(ids);
    }

    /**
     * 删除班级管理信息
     * 
     * @param id 班级管理主键
     * @return 结果
     */
    @Override
    public int deleteFengClassById(Long id)
    {
        return fengClassMapper.deleteFengClassById(id);
    }

}
