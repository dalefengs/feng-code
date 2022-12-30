package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.domain.entity.FengGive;

/**
 * 点赞Mapper接口
 * 
 * @author Likfees
 * @date 2022-12-30
 */
public interface FengGiveMapper 
{
    /**
     * 查询点赞
     * 
     * @param id 点赞主键
     * @return 点赞
     */
    public FengGive selectFengGiveById(Long id);

    /**
     * 查询点赞列表
     * 
     * @param fengGive 点赞
     * @return 点赞集合
     */
    public List<FengGive> selectFengGiveList(FengGive fengGive);

    /**
     * 新增点赞
     * 
     * @param fengGive 点赞
     * @return 结果
     */
    public int insertFengGive(FengGive fengGive);

    /**
     * 修改点赞
     * 
     * @param fengGive 点赞
     * @return 结果
     */
    public int updateFengGive(FengGive fengGive);

    /**
     * 删除点赞
     * 
     * @param id 点赞主键
     * @return 结果
     */
    public int deleteFengGiveById(Long id);

    /**
     * 批量删除点赞
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengGiveByIds(Long[] ids);

    FengGive selectFengGive(FengGive fengGive);
}
