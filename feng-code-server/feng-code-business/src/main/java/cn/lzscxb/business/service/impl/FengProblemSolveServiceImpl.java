package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemSolveMapper;
import cn.lzscxb.domain.entity.FengProblemSolve;
import cn.lzscxb.business.service.IFengProblemSolveService;

/**
 * 题解列表Service业务层处理
 * 
 * @author Likfees
 * @date 2022-10-16
 */
@Service
public class FengProblemSolveServiceImpl implements IFengProblemSolveService 
{
    @Autowired
    private FengProblemSolveMapper fengProblemSolveMapper;

    /**
     * 查询题解列表
     * 
     * @param id 题解列表主键
     * @return 题解列表
     */
    @Override
    public FengProblemSolve selectFengProblemSolveById(Long id)
    {
        return fengProblemSolveMapper.selectFengProblemSolveById(id);
    }

    /**
     * 查询题解列表列表
     * 
     * @param fengProblemSolve 题解列表
     * @return 题解列表
     */
    @Override
    public List<FengProblemSolve> selectFengProblemSolveList(FengProblemSolve fengProblemSolve)
    {
        return fengProblemSolveMapper.selectFengProblemSolveList(fengProblemSolve);
    }

    /**
     * 新增题解列表
     * 
     * @param fengProblemSolve 题解列表
     * @return 结果
     */
    @Override
    public int insertFengProblemSolve(FengProblemSolve fengProblemSolve)
    {
        fengProblemSolve.setCreateTime(DateUtils.getNowDate());
        return fengProblemSolveMapper.insertFengProblemSolve(fengProblemSolve);
    }

    /**
     * 修改题解列表
     * 
     * @param fengProblemSolve 题解列表
     * @return 结果
     */
    @Override
    public int updateFengProblemSolve(FengProblemSolve fengProblemSolve)
    {
        fengProblemSolve.setUpdateTime(DateUtils.getNowDate());
        return fengProblemSolveMapper.updateFengProblemSolve(fengProblemSolve);
    }

    /**
     * 批量删除题解列表
     * 
     * @param ids 需要删除的题解列表主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemSolveByIds(Long[] ids)
    {
        return fengProblemSolveMapper.deleteFengProblemSolveByIds(ids);
    }

    /**
     * 删除题解列表信息
     * 
     * @param id 题解列表主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemSolveById(Long id)
    {
        return fengProblemSolveMapper.deleteFengProblemSolveById(id);
    }
}
