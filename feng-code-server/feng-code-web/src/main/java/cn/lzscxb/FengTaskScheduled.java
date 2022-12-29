package cn.lzscxb;

import cn.lzscxb.business.service.impl.FengProblemEverydayServiceImpl;
import cn.lzscxb.business.service.impl.FengProblemQueueServiceImpl;
import cn.lzscxb.common.core.redis.RedisCache;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.domain.constant.CacheConstants;
import cn.lzscxb.domain.entity.FengProblemQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: Likfees
 * @Date: 2022/12/21 19:22
 * @Description: 定时任务
 */
@Component
@Slf4j
public class FengTaskScheduled {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private FengProblemQueueServiceImpl fengProblemQueueService;

    @Autowired
    private FengProblemEverydayServiceImpl everydayService;

    //    @Scheduled(cron = "0 0 0 * * ?") // 每日凌晨执行，正式环境
    @Scheduled(cron = "0 0 */1 * * ?") // 每小时执行一次
//    @Scheduled(fixedRate = 10000)
    public void everydayAddProblem() {
        log.info("每日一题 - 定时任务执行啦！ 当前时间：{} {}", DateUtils.getDate(), DateUtils.getTime());
        everydayService.everydayAddProblem();

    }

    @Scheduled(fixedRate = 100)
    public void executeQuque() {
//        log.info("定时任务");
        Integer ququeId = redisCache.cacheListLPop(CacheConstants.QUQUE_EXECUTE);
        if (ququeId == null) {
            return;
        }
        try {
            log.info("执行代码 - 定时任务 - 正在执行任务 queueId: {}", ququeId);
            FengProblemQueue fengProblemQueue = fengProblemQueueService.excuteQuque(ququeId);
            if (fengProblemQueue != null && fengProblemQueue.getStatus() == 4 && fengProblemQueue.getRetryCount() < 3) { // 执行异常，重新执行
                redisCache.cacheListRpush(CacheConstants.QUQUE_EXECUTE, ququeId);
            }
            log.info("定时任务 - 执行任务已完成！ ququeId: {}", ququeId);
        } catch (Exception e) {
            FengProblemQueue fengProblemQueue = fengProblemQueueService.selectFengProblemQueueById(Long.valueOf(ququeId));
            if (fengProblemQueue.getRetryCount() < 3) {
                redisCache.cacheListRpush(CacheConstants.QUQUE_EXECUTE, ququeId);
            }
            log.error("ququqId: {}, 定时任务处理异常, msg: {}", ququeId, e.getMessage());
            e.printStackTrace();
        }
    }

}
