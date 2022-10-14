package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.business.domain.FengCollege;

/**
 * 学院管理Mapper接口
 * 
 * @author Likfees
 * @date 2022-10-14
 */
public interface FengCollegeMapper 
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
     * 删除学院管理
     * 
     * @param id 学院管理主键
     * @return 结果
     */
    public int deleteFengCollegeById(Long id);

    /**
     * 批量删除学院管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengCollegeByIds(Long[] ids);
}
