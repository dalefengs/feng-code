package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengCollegeMapper;
import cn.lzscxb.domain.FengCollege;
import cn.lzscxb.business.service.IFengCollegeService;

/**
 * 学院管理Service业务层处理
 * 
 * @author Likfees
 * @date 2022-10-14
 */
@Service
public class FengCollegeServiceImpl implements IFengCollegeService 
{
    @Autowired
    private FengCollegeMapper fengCollegeMapper;

    /**
     * 查询学院管理
     * 
     * @param id 学院管理主键
     * @return 学院管理
     */
    @Override
    public FengCollege selectFengCollegeById(Long id)
    {
        return fengCollegeMapper.selectFengCollegeById(id);
    }

    /**
     * 查询学院管理列表
     * 
     * @param fengCollege 学院管理
     * @return 学院管理
     */
    @Override
    public List<FengCollege> selectFengCollegeList(FengCollege fengCollege)
    {
        return fengCollegeMapper.selectFengCollegeList(fengCollege);
    }

    /**
     * 获取所有学院信息列表
     * @return 列表
     */
    public List<FengCollege> selectFengCollegeListAll() {
        return fengCollegeMapper.seletcFengCollegeAllList();
    }


    /**
     * 新增学院管理
     * 
     * @param fengCollege 学院管理
     * @return 结果
     */
    @Override
    public int insertFengCollege(FengCollege fengCollege)
    {
        fengCollege.setCreateTime(DateUtils.getNowDate());
        return fengCollegeMapper.insertFengCollege(fengCollege);
    }

    /**
     * 修改学院管理
     * 
     * @param fengCollege 学院管理
     * @return 结果
     */
    @Override
    public int updateFengCollege(FengCollege fengCollege)
    {
        fengCollege.setUpdateTime(DateUtils.getNowDate());
        return fengCollegeMapper.updateFengCollege(fengCollege);
    }

    /**
     * 批量删除学院管理
     * 
     * @param ids 需要删除的学院管理主键
     * @return 结果
     */
    @Override
    public int deleteFengCollegeByIds(Long[] ids)
    {
        return fengCollegeMapper.deleteFengCollegeByIds(ids);
    }

    /**
     * 删除学院管理信息
     * 
     * @param id 学院管理主键
     * @return 结果
     */
    @Override
    public int deleteFengCollegeById(Long id)
    {
        return fengCollegeMapper.deleteFengCollegeById(id);
    }
}
