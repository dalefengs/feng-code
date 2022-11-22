package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import cn.lzscxb.domain.model.LoginUser;
import cn.lzscxb.domain.model.ProblemCreateBody;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemMapper;
import cn.lzscxb.domain.entity.FengProblem;
import cn.lzscxb.business.service.IFengProblemService;

/**
 * 题目管理Service业务层处理
 * 
 * @author Likfees
 * @date 2022-11-21
 */
@Service
public class FengProblemServiceImpl implements IFengProblemService 
{
    @Autowired
    private FengProblemMapper fengProblemMapper;

    /**
     * 查询题目管理
     * 
     * @param id 题目管理主键
     * @return 题目管理
     */
    @Override
    public FengProblem selectFengProblemById(Long id)
    {
        return fengProblemMapper.selectFengProblemById(id);
    }

    /**
     * 查询题目管理列表
     * 
     * @param fengProblem 题目管理
     * @return 题目管理
     */
    @Override
    public List<FengProblem> selectFengProblemList(FengProblem fengProblem)
    {
        return fengProblemMapper.selectFengProblemList(fengProblem);
    }

    /**
     * 新增题目管理
     * 
     * @param problemCreateBody 题目管理
     * @return 结果
     */
    @Override
    public int insertFengProblem(ProblemCreateBody problemCreateBody)
    {
        // 获取用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 将 ProblemCreateBody 转换为 FengProblem 对象
        FengProblem fengProblem = this.toFengProblem(problemCreateBody);
        fengProblem.setUserId(loginUser.getUserId());
        fengProblem.setCreateTime(DateUtils.getNowDate());

        return fengProblemMapper.insertFengProblem(fengProblem);
    }


    /**
     * 修改题目管理
     * 
     * @param problemCreateBody 题目管理
     * @return 结果
     */
    @Override
    public int updateFengProblem(ProblemCreateBody problemCreateBody)
    {
        FengProblem fengProblem = this.toFengProblem(problemCreateBody);
        fengProblem.setUpdateTime(DateUtils.getNowDate());
        return fengProblemMapper.updateFengProblem(fengProblem);
    }

    public FengProblem toFengProblem(ProblemCreateBody p){
        FengProblem f = new FengProblem();
        f.setId(p.getId());
        f.setUserId(p.getUserId());
        f.setTitle(p.getTitle());
        f.setDescription(p.getDescription());
        f.setHint(p.getHint());
        f.setCategoryId(p.getCategoryId());
        f.setTagId(p.getTagId());
        f.setLevel(p.getLevel());
        f.setSort(p.getSort());
        f.setIsAuto(p.getIsAuto());
        f.setLanguage(JSON.toJSONString(p.getLanguage()));
        f.setMethodNames(JSON.toJSONString(p.getMethodNames()));
        f.setParamTypes(JSON.toJSONString(p.getParamTypes()));
        f.setCodeTemplates(JSON.toJSONString(p.getCodeTemplates()));
        f.setTestCase(JSON.toJSONString(p.getTestCase()));
        return f;
    }

    /**
     * 批量删除题目管理
     * 
     * @param ids 需要删除的题目管理主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemByIds(Long[] ids)
    {
        return fengProblemMapper.deleteFengProblemByIds(ids);
    }

    /**
     * 删除题目管理信息
     * 
     * @param id 题目管理主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemById(Long id)
    {
        return fengProblemMapper.deleteFengProblemById(id);
    }
}
