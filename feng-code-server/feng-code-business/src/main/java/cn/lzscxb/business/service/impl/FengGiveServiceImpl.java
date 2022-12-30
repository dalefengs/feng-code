package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengGiveMapper;
import cn.lzscxb.domain.entity.FengGive;
import cn.lzscxb.business.service.IFengGiveService;

/**
 * 点赞Service业务层处理
 * 
 * @author Likfees
 * @date 2022-12-30
 */
@Service
public class FengGiveServiceImpl implements IFengGiveService 
{
    @Autowired
    private FengGiveMapper fengGiveMapper;

    /**
     * 查询点赞
     * 
     * @param id 点赞主键
     * @return 点赞
     */
    @Override
    public FengGive selectFengGiveById(Long id)
    {
        return fengGiveMapper.selectFengGiveById(id);
    }

    /**
     * 查询点赞列表
     * 
     * @param fengGive 点赞
     * @return 点赞
     */
    @Override
    public List<FengGive> selectFengGiveList(FengGive fengGive)
    {
        return fengGiveMapper.selectFengGiveList(fengGive);
    }

    /**
     * 新增点赞
     * 
     * @param fengGive 点赞
     * @return 结果
     */
    @Override
    public int insertFengGive(FengGive fengGive)
    {
        fengGive.setUserId(SecurityUtils.getUserId());
        FengGive give = fengGiveMapper.selectFengGive(fengGive);
        if (give != null) {
            throw new RuntimeException("请不要重复点赞哟！");
        }
        fengGive.setCreateTime(DateUtils.getNowDate());
        return fengGiveMapper.insertFengGive(fengGive);
    }

    /**
     * 修改点赞
     * 
     * @param fengGive 点赞
     * @return 结果
     */
    @Override
    public int updateFengGive(FengGive fengGive)
    {
        fengGive.setUpdateTime(DateUtils.getNowDate());
        return fengGiveMapper.updateFengGive(fengGive);
    }

    /**
     * 批量删除点赞
     * 
     * @param ids 需要删除的点赞主键
     * @return 结果
     */
    @Override
    public int deleteFengGiveByIds(Long[] ids)
    {
        return fengGiveMapper.deleteFengGiveByIds(ids);
    }

    /**
     * 删除点赞信息
     * 
     * @param id 点赞主键
     * @return 结果
     */
    @Override
    public int deleteFengGiveById(Long id)
    {
        return fengGiveMapper.deleteFengGiveById(id);
    }
}
