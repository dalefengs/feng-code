package cn.lzscxb.business.service;

import java.util.List;
import cn.lzscxb.domain.FengCollege;

/**
 * 学院管理Service接口
 * 
 * @author Likfees
 * @date 2022-10-14
 */
public interface IFengCollegeService 
{
    /**
     * 查询学院管理
     * 
     * @param id 学院管理主键
     * @return 学院管理
     */
    public FengCollege selectFengCollegeById(Long id);

    /**
     * 查询学院管理列表
     * 
     * @param fengCollege 学院管理
     * @return 学院管理集合
     */
    public List<FengCollege> selectFengCollegeList(FengCollege fengCollege);

    /**
     * 获取所有学院信息列表
     * @return
     */
    public List<FengCollege> selectFengCollegeListAll();

    /**
     * 新增学院管理
     * 
     * @param fengCollege 学院管理
     * @return 结果
     */
    public int insertFengCollege(FengCollege fengCollege);

    /**
     * 修改学院管理
     * 
     * @param fengCollege 学院管理
     * @return 结果
     */
    public int updateFengCollege(FengCollege fengCollege);

    /**
     * 批量删除学院管理
     * 
     * @param ids 需要删除的学院管理主键集合
     * @return 结果
     */
    public int deleteFengCollegeByIds(Long[] ids);

    /**
     * 删除学院管理信息
     * 
     * @param id 学院管理主键
     * @return 结果
     */
    public int deleteFengCollegeById(Long id);
}
