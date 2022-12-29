package cn.lzscxb.business.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import cn.lzscxb.business.mapper.FengProblemMapper;
import cn.lzscxb.business.mapper.FengProblemQueueMapper;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import cn.lzscxb.domain.entity.FengProblem;
import cn.lzscxb.domain.entity.FengProblemQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemEverydayMapper;
import cn.lzscxb.domain.entity.FengProblemEveryday;
import cn.lzscxb.business.service.IFengProblemEverydayService;

/**
 * 每日一题Service业务层处理
 * 
 * @author Likfees
 * @date 2022-12-29
 */
@Service
public class FengProblemEverydayServiceImpl implements IFengProblemEverydayService 
{
    @Autowired
    private FengProblemEverydayMapper fengProblemEverydayMapper;
    @Autowired
    private FengProblemMapper fengProblemMapper;
    @Autowired
    private FengProblemQueueMapper fengProblemQueueMapper;

    @Override
    public List<FengProblemEveryday> selectMonthList(FengProblemEveryday fengProblemEveryday) {
        List<FengProblemEveryday> list = fengProblemEverydayMapper.selectMonthList(fengProblemEveryday);
        Long userId = SecurityUtils.getUserId();
        fengProblemEveryday.setCurrentUserId(userId);
        for (FengProblemEveryday everyday : list) {
            FengProblemQueue fengProblemQueue = new FengProblemQueue();
            fengProblemQueue.setUserId(userId);
            fengProblemQueue.setProblemId(everyday.getProblemId());
            fengProblemQueue.setEverydayId(everyday.getId());
            HashSet<Integer> statusList = fengProblemQueueMapper.selectProblemQuqueStatusList(fengProblemQueue);
            if (statusList.contains(2)) { // 2 执行完成
                everyday.setOwnness(1); // 解答过
            }else if (statusList.contains(5)) { // 5 待批阅
                everyday.setOwnness(3); // 待批阅
            }else if (statusList.contains(3) || statusList.contains(4)) {
                everyday.setOwnness(2); // 尝试过
            } else {
                everyday.setOwnness(0); // 未开始
            }
        }
        return list;
    }

    @Override
    public void everydayAddProblem() {
        String date = DateUtils.getDate();
        FengProblemEveryday everyday = this.selectFengProblemEverydayByDay(date);
        // 如果存在记录则不需要添加
        if (everyday != null) {
            return;
        }
        // 获取所有题目id 然后随机挑一个
        List<Long> problemIds = fengProblemMapper.selectFengProblemAllIds();
        Collections.shuffle(problemIds);
        int index = new Random().nextInt(problemIds.size());
        Long problemId = problemIds.get(index);
        FengProblemEveryday fengProblemEveryday = new FengProblemEveryday();
        fengProblemEveryday.setDateDay(DateUtils.getNowDate());
        fengProblemEveryday.setCreateTime(DateUtils.getNowDate());
        fengProblemEveryday.setProblemId(problemId);
        fengProblemEverydayMapper.insertFengProblemEveryday(fengProblemEveryday);
    }

    public FengProblemEveryday selectFengProblemEverydayByDay(String date) {
        return fengProblemEverydayMapper.selectFengProblemEverydayByDay(date);
    }

    /**
     * 查询每日一题
     * 
     * @param id 每日一题主键
     * @return 每日一题
     */
    @Override
    public FengProblemEveryday selectFengProblemEverydayById(Long id)
    {
        return fengProblemEverydayMapper.selectFengProblemEverydayById(id);
    }

    /**
     * 查询每日一题列表
     * 
     * @param fengProblemEveryday 每日一题
     * @return 每日一题
     */
    @Override
    public List<FengProblemEveryday> selectFengProblemEverydayList(FengProblemEveryday fengProblemEveryday)
    {
        return fengProblemEverydayMapper.selectFengProblemEverydayList(fengProblemEveryday);
    }

    /**
     * 新增每日一题
     * 
     * @param fengProblemEveryday 每日一题
     * @return 结果
     */
    @Override
    public int insertFengProblemEveryday(FengProblemEveryday fengProblemEveryday)
    {
        fengProblemEveryday.setCreateTime(DateUtils.getNowDate());
        return fengProblemEverydayMapper.insertFengProblemEveryday(fengProblemEveryday);
    }

    /**
     * 修改每日一题
     * 
     * @param fengProblemEveryday 每日一题
     * @return 结果
     */
    @Override
    public int updateFengProblemEveryday(FengProblemEveryday fengProblemEveryday)
    {
        fengProblemEveryday.setUpdateTime(DateUtils.getNowDate());
        return fengProblemEverydayMapper.updateFengProblemEveryday(fengProblemEveryday);
    }

    /**
     * 批量删除每日一题
     * 
     * @param ids 需要删除的每日一题主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemEverydayByIds(Long[] ids)
    {
        return fengProblemEverydayMapper.deleteFengProblemEverydayByIds(ids);
    }

    /**
     * 删除每日一题信息
     * 
     * @param id 每日一题主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemEverydayById(Long id)
    {
        return fengProblemEverydayMapper.deleteFengProblemEverydayById(id);
    }
}
